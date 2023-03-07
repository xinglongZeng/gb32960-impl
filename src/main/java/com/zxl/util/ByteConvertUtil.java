package com.zxl.util;

import com.zxl.common.CommonConstants;
import com.zxl.gb.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ByteConvertUtil {

    /**
     * 输入源支持的数据类型集合
     */
    private static final Set<String> inPutTypes = new HashSet();

    /**
     * 输出源支持的数据类型集合
     */
    private static final Set<String> outPutTypes = new HashSet<>();

    /**
     * 支持的执行顺序
     */
    private static final Set<String> sequenceOp = new HashSet<>();

    /**
     * 支持的偏移量操作符
     */
    private static final Set<String> offSetOp = new HashSet<>();

    /**
     * 支持的缩放操作符
     */
    private static final Set<String> scaleOp = new HashSet<>();


    private static Field[] baseDataUnitFileds = BaseDataUnit.class.getDeclaredFields();


    private static final String NOT_HAVE_CONVERT = "not have @ByteConvert";


    private static final Map<Class,List<CovertConfig>> ALL_COVERT_CONFIG = new HashMap<>();


    /**
     * 初始化
     */
    static {
        initAllCovertConfig();

        // --------------------  输入类型 --------------------
        inPutTypes.add(CommonConstants.INPUT_TYPE_SHORT);
        inPutTypes.add(CommonConstants.INPUT_TYPE_ENUM);
        inPutTypes.add(CommonConstants.INPUT_TYPE_INTEGER);
        inPutTypes.add(CommonConstants.INPUT_TYPE_FLOAT);
        inPutTypes.add(CommonConstants.INPUT_TYPE_DOUBLE);


        // -------------------- 输出类型 --------------------

        outPutTypes.add(CommonConstants.OUTPUT_TYPE_BYTES);


        // --------------------  执行顺序 --------------------

        sequenceOp.add(CommonConstants.SEQUENCE_OP_O);
        sequenceOp.add(CommonConstants.SEQUENCE_OP_S);
        sequenceOp.add(CommonConstants.SEQUENCE_OP_SO);
        sequenceOp.add(CommonConstants.SEQUENCE_OP_OS);


        // --------------------  偏移 --------------------

        offSetOp.add(CommonConstants.OFFSET_OP_ADD);
        offSetOp.add(CommonConstants.OFFSET_OP_SUB);


        // -------------------- 缩放 --------------------

        scaleOp.add(CommonConstants.SCALE_OP_PLUS);
        scaleOp.add(CommonConstants.SCALE_OP_DIV);


    }


    private static void initAllCovertConfig(){
        // 初始化 ALL_COVERT_CONFIG
        for(CovertConfig config : CovertConfig.values()){

            List<CovertConfig> list = ALL_COVERT_CONFIG.get(config.getClazz());

            if(list == null){
                list = new ArrayList<>();
                ALL_COVERT_CONFIG.put(config.getClazz(),list);
            }

            list.add(config);
            list.sort(Comparator.comparing(CovertConfig::getFieldOrder));
        }
        log.info(" ------  [ ALL_COVERT_CONFIG初始化完成 ]  ------");
    }


    public static byte[] byteList2Array(List<byte[]> list) {
        Integer total = list.stream()
                .map(a -> a.length)
                .reduce(0, Integer::sum);

        byte[] result = new byte[total];

        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            byte[] bytes = list.get(i);
            System.arraycopy(bytes, 0, result, index, bytes.length);
            index += bytes.length;

        }

        return result;

    }

    public static byte[] enumConvert2Bytes(BaseEnum obj) throws Exception {
        return obj.enum2Bytes();
    }



    private static BigDecimal okValueParseToNumber(Object value, int precision) throws Exception {

        // 根据数据类型进行处理
        if (value instanceof Short) {

            return new BigDecimal((Short) value);

        } else if (value instanceof Integer) {

            return new BigDecimal((Integer) value);

        } else if (value instanceof Double) {

            return BigDecimal.valueOf((Double) value).setScale(precision, RoundingMode.HALF_UP);

        } else if (value instanceof Long) {

            return new BigDecimal((Long) value);

        } else {

            throw new Exception("暂不支持的数字类型! " + value.getClass().getSimpleName());
        }


    }

    private static byte[] bigDecimal2Bytes(BigDecimal bValue, GbFieldTypeEnum fieldType) throws Exception {
        byte[] bytes = null;
        switch (fieldType) {
            case BYTE:
                bytes = new byte[1];
                bytes[0] = bValue.byteValue();
                return bytes;
            case WORD:
                return ByteUtil.shortToBytes(bValue.shortValue());
            case DWORD:
                return ByteUtil.intToBytes(bValue.intValue());
            case BYTE_LIST:
                // todo:
            case STRING:
                // todo:
            default:
                throw new Exception("不支持的国标字段类型:" + fieldType);

        }

    }

    private static byte[] fillByteArray(byte[] bytes, int length) throws Exception {
        // 长度超限就报异常
        if (bytes.length > length) {
            throw new Exception("字节数组长度实际值：" + bytes.length + "大于 限定值:" + length);
        } else if (bytes.length < length) {
            // 长度小于限定值，则进行填充
            return Arrays.copyOf(bytes, length);
        } else {
            return bytes;
        }
    }



    private static BigDecimal processSequence(BigDecimal v, String sequence, String offset, String scale) throws Exception {
        if(!StringUtils.isEmpty(sequence)){
            for (char c : sequence.toCharArray()) {
                v = processOffSetAndScale(v, c, offset, scale);
            }
        }

        return v;
    }


    /**
     * 处理偏移和缩放
     *
     * @param v
     * @param c
     * @return
     * @throws Exception
     */
    private static BigDecimal processOffSetAndScale(BigDecimal v, char c, String offset, String scale) throws Exception {


        if (CommonConstants.SEQUENCE_OP_O.charAt(0) == c) {

            //  进行偏移
            return v.add(new BigDecimal(offset));

        } else if (CommonConstants.SEQUENCE_OP_S.charAt(0) == c) {

            //   进行缩放
            return v.multiply(new BigDecimal(scale));

        } else {
            throw new Exception("不支持的操作符:"+c);
        }

    }


    /**
     * 反向处理偏移和缩放
     *
     * @param v
     * @param c
     * @param precision
     * @return
     * @throws Exception
     */
    private static BigDecimal reverseProcessOffSetAndScale(BigDecimal v, char c, String offset, String scale, Integer precision) throws Exception {
        if(precision!=null){
            v = v.setScale(precision,RoundingMode.HALF_UP);
        }


        if (CommonConstants.SEQUENCE_OP_O.charAt(0) == c) {

            //  进行偏移
            return v.subtract(new BigDecimal(offset));

        } else if (CommonConstants.SEQUENCE_OP_S.charAt(0) == c) {

            //   进行缩放
            return v.divide(new BigDecimal(scale));

        } else {
            throw new Exception("不支持的操作符:"+c);
        }

    }



    /**
     * 检查输出源的类型
     *
     * @param outPutType
     */
    private static void checkOutPutType(String outPutType) throws Exception {
        if (!outPutTypes.contains(outPutType)) {
            throw new Exception("不支持的输出源数据类型:" + outPutType);
        }
    }

    /**
     * 检查执行顺序
     */
    private static void checkSequence(String sequence, String offset, String scale) throws Exception {
        if(StringUtils.isEmpty(sequence)){
            return;
        }

        if (!sequenceOp.contains(sequence)) {
            throw new Exception("不支持的执行顺序操作符:" + sequence);

        }

        //  ----  检查和执行顺序相关的参数是否传入 ---------

        // 偏移
        if (sequence.contains("o") && offset == null) {
            throw new Exception("偏移量参数为空,不符合执行规则! sequence:" + sequence);
        }

        // 缩放
        if (sequence.contains("s") && scale == null) {
            throw new Exception("缩放参数为空,不符合执行规则! sequence:" + sequence);
        }

    }


    public static Map<String, Field> getFieldMap(Class clazz) {

        Map<String, Field> fieldMap = new HashMap<>();

        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> fieldMap.put(field.getName(), field));


        if (BaseDataUnit.class.isAssignableFrom(clazz)) {

            Arrays.stream(baseDataUnitFileds)
                    .forEach(field -> fieldMap.put(field.getName(), field));

        }


        return fieldMap;
    }



    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


    /**
     * 判断 bytes的字节数量是否 等于 length， 如果满足则直接返回；
     * 如果小于，则使用ascii编码的空字符进行填充;
     * 如果大于，则抛出异常
     *
     * @param length
     * @param bytes
     * @return
     */
    public static byte[] fillStringBytes(int length, byte[] bytes) throws Exception {

        if (bytes.length < length) {
            List<byte[]> list = new ArrayList<>();
            list.add(bytes);

            // 进行填充
            for (int i = length - bytes.length; i > 0; i--) {
                list.add(ByteUtil.FILL_STRING_BYTE);
            }

            return ByteConvertUtil.byteList2Array(list);

        } else if (bytes.length == length) {

            return bytes;

        } else {

            throw new Exception("fillStringBytes-字节数组长度超出限定值:" + length);
        }

    }


    public static CovertConfig getCovertConfig(Class clazz , String fieldName) throws Exception {
        List<CovertConfig> configList = ALL_COVERT_CONFIG.get(clazz);
        return configList.stream()
                .filter(e->e.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow(()-> new Exception("未找到指定的字段的CovertConfig ! clazz:"+clazz.getName()+", fieldName:"+fieldName));
    }



    /**
     * 将某个class的对象的所有字段解析为字节数组
     * @param obj
     * @return
     */
    public static byte[] covertClassForUpload(Object obj) throws Exception {

        List<CovertConfig> configList = ALL_COVERT_CONFIG.get(obj.getClass());

        Map<String, Field> fieldMap = ByteConvertUtil.getFieldMap(obj.getClass());

        if(configList.size() != fieldMap.size()){
            throw new Exception("CovertConfig配置的字段数量与实际字段数量不一致!");
        }

        List<byte[]> byteList = new ArrayList<>();

        for (CovertConfig config : configList){

            byte[] bytes = null;

            if(config.getUploadMethodName() !=null ){

                bytes = (byte[])executeSpecialMethodForUpload(obj,config.getUploadMethodName());

            }else {

                Field field = fieldMap.get(config.getFieldName());
                field.setAccessible(true);
                Object fieldValue = field.get(obj);

                if(fieldValue ==null){
                    throw new Exception("field获取失败! class :"+obj.getClass().getName()+", fieldName : "+config.getFieldName());
                }

                bytes = covertFieldForUpload(config,fieldValue);
            }

            if(bytes==null || bytes.length==0){
                throw new Exception("covertClassForUpload 执行config失败! config : "+config);
            }

            byteList.add(bytes);

        }


        return byteList2Array(byteList);

    }


    public static Field getFieldFromClass(Class clazz,String field){
        Map<String, Field> fieldMap = ByteConvertUtil.getFieldMap(clazz);
        return fieldMap.get(field);
    }


    public static Object executeSpecialMethodForDownload(byte[] bytes,Object obj,String methodName) throws Exception {

        Method method = getMethodByName(methodName);

        return method.invoke(obj, bytes,obj);
    }



    public static Object executeSpecialMethodForUpload(Object obj,String methodName,Object... param) throws Exception {

        Method method = getMethodByName(methodName);

        return method.invoke(obj, param);
    }

    private static Method getMethodByName(String methodName) throws Exception {
        String[] split = methodName.split("#");
        if( split.length!=2){
            throw new Exception("methodName不符合规范! methodName:"+methodName);
        }

        // 通过类加载器，找到指定的类class
        Class<?> clazz = ByteConvertUtil.class.getClassLoader().loadClass(split[0]);

        if(clazz==null){
            throw new Exception("class查询失败! class:"+split[0]);
        }

        List<Method> methodList = Arrays.stream(clazz.getDeclaredMethods())
                .filter(e -> e.getName().equals(split[1]))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(methodList)){
            throw new Exception("method查询失败! class:"+split[0]+", method:"+split[1]);
        }

        if(methodList.size() != 1){
            throw new Exception("存在同名的多个method! class:"+split[0]+", method:"+split[1]);
        }

        Method method = methodList.get(0);
        method.setAccessible(true);
        return method;
    }


    /**
     * 进行上传数据的解析（实体对象转换成字节数组）
     *
     * @param covertConfig 解析配置
     * @param field            对应要进行解析的字段的值
     * @return
     */
    public static byte[] covertFieldForUpload(CovertConfig covertConfig, Object field) throws Exception {

        // 判断gbValue的类型
        if (field instanceof GbValue) {

            // 参数校验
            doCheckForGbValue((GbValue<?>) field, covertConfig, DataFlowEnum.UPLOAD);

            // 解析成字节
            return covertToBytesByCovertConfig(covertConfig, (GbValue<?>) field);

            // 是枚举类型
        } else if (field instanceof BaseEnum) {

            return ((BaseEnum) field).enum2Bytes();


            // 集合
        } else if(field instanceof List) {

            return covertListToBytes(field,covertConfig.getByteLength());
            // 其他类型

        } else{

            throw new Exception("暂不支持的实体数据类型:" + field.getClass().getName());
        }
    }

    public static byte[] covertListToBytes(Object field, Integer byteLength) throws Exception {
        List<byte[]> byteList = new ArrayList<>();

        List<?> list = (List<?>)field;

       for(Object obj : list){
           byteList.add(covertClassForUpload(obj));
       }

       return ByteConvertUtil.byteList2Array(byteList);

    }

    public static byte[] covertToBytesByCovertConfig(CovertConfig covertConfig, GbValue<?> gbValue) throws Exception {

        switch (gbValue.getFlg()) {
            case EXCEPTION:
                return getNotOkValueFromGbValueCheck(covertConfig.getExceptionBytes(), covertConfig.getByteLength());
            case INVALID:
                return getNotOkValueFromGbValueCheck(covertConfig.getInvalidBytes(), covertConfig.getByteLength());
            default:
                return getOkValueFromGbValueByCovertConfig(gbValue.getValue(), covertConfig);
        }
    }


    public static void doCheckForGbValue(GbValue<?> gbValue, CovertConfig covertConfig, DataFlowEnum dataFlowEnum) throws Exception {
        if (covertConfig == null) {
            throw new Exception("covertConfig为null!");
        }

        // 非空校验
        if (covertConfig.getCheckNotNull().equals(dataFlowEnum) && gbValue == null) {
            throw new Exception(covertConfig.getNullErrMsg());
        }

        // 检查执行顺序的设置
        checkSequence(covertConfig.getSequence(), covertConfig.getOffset(), covertConfig.getScale());


        if (GbValueFlgEnum.INVALID.equals(gbValue.getFlg())) {
            if (covertConfig.getInvalidBytes() == null) {
                throw new Exception("字段的无效值(16进制)未设置！");
            }
        } else if (GbValueFlgEnum.EXCEPTION.equals(gbValue.getFlg())) {
            if (covertConfig.getExceptionBytes() == null) {
                throw new Exception("字段的异常值(16进制)未设置！");
            }
        } else {
            // 获取 GbValue<?> 实际指定的类型
            String typeName = gbValue.getValue().getClass().getSimpleName();
            BigDecimal v = null;

            // short
            if ("Short".equals(typeName)) {
                v = new BigDecimal((Short) gbValue.getValue());

                // Integer
            } else if ("Integer".equals(typeName)) {
                v = new BigDecimal((Integer) gbValue.getValue());

                // Float
            } else if ("Float".equals(typeName)) {
                v = BigDecimal.valueOf((Float) gbValue.getValue());

                // double
            } else if ("Double".equals(typeName)) {
                v = BigDecimal.valueOf((Double) gbValue.getValue());

                //  other type
            }else {
                return;
            }

            BigDecimal min = covertConfig.getMinValue();
            BigDecimal max = covertConfig.getMaxValue();

            assert v != null;
            if (min != null && v.compareTo(min) < 0) {
                throw new Exception("参数校验失败，最小值为:" + min + ",实际值为:" + v);
            }

            if (max != null && v.compareTo(max) > 0) {
                throw new Exception("参数校验失败，最大值为:" + max + ",实际值为:" + v);
            }
        }

    }

    private static byte[] getNotOkValueFromGbValueCheck(Long notOkValue, Integer byteLength) throws Exception {

        if (notOkValue == null) {
            throw new Exception("非正常值为设置，无法进行映射!");
        }

        BigDecimal b = new BigDecimal(notOkValue);

        if (byteLength == null) {
            throw new Exception("byteLength未设置，无法进行映射!");
        }


        // 非正常数据直接按照需要的字节数组长度进行转换
        switch (byteLength) {
            case 1:
                byte[] bytes = new byte[1];
                bytes[0] = b.byteValue();
                return bytes;
            case 2:
                return ByteUtil.shortToBytes(b.shortValue());
            case 4:
                return ByteUtil.intToBytes(b.intValue());
            default:
                throw new Exception("暂不支持的字节长度:" + byteLength);
        }
    }


    private static byte[] getOkValueFromGbValueByCovertConfig(Object value, CovertConfig covertConfig) throws Exception {

        byte[] result = null;

        // 是数字型数据
        if (value instanceof Number) {

            result = processNumberType(value, covertConfig);

            // Date类型
        } else if (value instanceof Date) {

            result = ByteUtil.processDateType((Date) value);

            // string类型
        } else if (value instanceof String) {

            result = processStringType((String) value , covertConfig.getByteLength());

        }

        assert result!=null;

        if(covertConfig.getByteLength()!=null && result.length != covertConfig.getByteLength()){
            throw new Exception("解析出的字节数组长度与设置值不同! 设置值:"+covertConfig.getByteLength()+" , 实际值:"+result.length);
        }


        return result;
    }


    public static byte[] processStringType(String str,Integer byteLength) throws Exception {

        byte[] bytes = ByteUtil.processsStringType(str);

        if(byteLength != null){
           return  fillStringBytes(byteLength ,bytes);
        }
        return bytes;
    }


    private static byte[] processNumberType(Object value, CovertConfig covertConfig) throws Exception {

        BigDecimal bValue = null;

        bValue = okValueParseToNumber(value, covertConfig.getPrecision());

        // 处理偏移和缩放
        bValue = processSequence(bValue, covertConfig.getSequence(), covertConfig.getOffset(), covertConfig.getScale());

        // 转换为字节数组
        return bigDecimal2Bytes(bValue, covertConfig.getByteLength());
    }


    private static byte[] bigDecimal2Bytes(BigDecimal bValue, int byteLength) throws Exception {
        byte[] bytes = null;
        switch (byteLength) {
            case 1:
                bytes = new byte[1];
                bytes[0] = bValue.byteValue();
                break;
            case 2:
                bytes = ByteUtil.shortToBytes(bValue.shortValue());
                break;
            case 4:
                bytes = ByteUtil.intToBytes(bValue.intValue());
                break;
            default:
                throw new Exception("不支持的字节长度类型:" + byteLength);

        }

        return bytes;

    }


    /**
     * 根据CovertConfig 将字节数组解析为对应的实体对象
     * @param bytes
     * @return
     */
    public static Object convertBytesForDownload(byte[] bytes , List<CovertConfig> configList ) throws Exception {

        checkParamForParseBytesToObj(bytes,configList);

        Object instance = configList.get(0).getClazz().newInstance();

        int length = 0;

        for(CovertConfig config : configList){

            length += setFieldValueByCovertConfig(instance,bytes,config,length);

        }

        if(length != bytes.length ){
            throw new Exception("还有剩余字节未解析!");
        }

        return instance;
    }

    /**
     * 根据CovertConfig 将字节数组解析为对应的实体对象,并且获得解析成实体对象所需字节数量
     * @param bytes
     * @return
     */
    public static Object convertBytesForDownloadAndGetSize(byte[] bytes , List<CovertConfig> configList , AtomicInteger size) throws Exception {

        checkParamForParseBytesToObj(bytes,configList);

        Object instance = configList.get(0).getClazz().newInstance();

        int index = 0;

        for(CovertConfig config : configList){

            index += setFieldValueByCovertConfig(instance,bytes,config,index);

        }

        size.set(index);

        return instance;
    }




    private static void checkParamForParseBytesToObj(byte[] bytes , List<CovertConfig> configList) throws Exception {
        if(bytes==null || bytes.length==0){
            throw new Exception("字节数据不存在!");
        }

        if(CollectionUtils.isEmpty(configList)){
            throw new Exception("configList为空!");
        }

        Class clazz = configList.get(0).getClazz();

        Optional<CovertConfig> optional = configList.stream()
                .filter(e -> !e.getClazz().equals(clazz))
                .findFirst();

        if(optional.isPresent()){
            throw new Exception("class不一致! 标准class : "+clazz.getName()+", 非标准class : "+optional.get().getClazz().getName());
        }

    }



    private static int setFieldValueByCovertConfig(Object instance, byte[] bytes, CovertConfig config, int start) throws Exception {

        Field field =  getFieldFromClass(instance.getClass(),config.getFieldName());

        field.setAccessible(true);

        int length = getByteLengthFromCovertConfig(config,instance);

        byte[] subBytes = ByteUtil.subBytes(bytes, start, start + length - 1);

        field.setAccessible(true);

        Object fieldValue = null;

        if(config.getDownloadMethodName() != null){

            //  将字节解析为实体数据的支持
            fieldValue =  executeSpecialMethodForDownload(subBytes,instance, config.getDownloadMethodName());

        }else {

            // 是GbValue包装的类型
            if( config.isPackFlg()){

                fieldValue = parseBytesToGbValueByCovertConfig( subBytes,  config);

            }else {

                // 不是GbValue类型，则可能是List,枚举, Object
                switch (config.getInputValueType()){
                    case Enum:
                        BaseEnum[] values = (BaseEnum[]) field.getType().getMethod("values").invoke(new Object(), new Object[]{});
                        fieldValue = values[0].parseToEnum(subBytes);
                        break;
                    case Object:
                        fieldValue = parseByteArrayToObj(subBytes,field.getDeclaringClass());
                        break;
                    case List:
                        // 集合中元素个数
                        int size = getListSize(config, instance);
                        subBytes = ByteUtil.subBytes(bytes, start, start + length*size-1);
                        Class<?> wrapClass = getWrapTypeFromListField(field);
                        fieldValue =  parseBytesToList(subBytes,wrapClass,size,length);
                        length = length*size;
                        break;
                    default:
                        throw new Exception("暂不支持的类型: "+config.getInputValueType());
                }

            }
        }

        field.set(instance,fieldValue);

        return length;

    }


    /**
     * 从类型中List的field中获取List包装的泛型对象的class
     * @return
     */
    public static Class<?> getWrapTypeFromListField(Field field) throws ClassNotFoundException {
        ParameterizedType type= (ParameterizedType)field.getGenericType();
        Type typeArgument = type.getActualTypeArguments()[0];
        return ByteConvertUtil.class.getClassLoader().loadClass(typeArgument.getTypeName());
    }



    private static <T> List<T> parseBytesToList(byte[] bytes, Class<T> clazz , Object instance) throws Exception {
        List<T> list = new ArrayList<>();

        List<CovertConfig> configList = ALL_COVERT_CONFIG.get(clazz);

        int length= getFieldByteLength(configList.get(0),instance);

        if(bytes.length % length !=0 ){
            throw new Exception("解析字节数组为List失败！bytes的长度不是List包装对象所需字节数的整数倍! class :"+clazz.getName() +", 包装类型 : "+clazz.getDeclaringClass());
        }

        int size = bytes.length / length;

        for(int i=0;i<size ;i++){
            int start = i*length;
            int end = start+length-1;
            byte[] sub = ByteUtil.subBytes(bytes,start*length,end);
            list.add(parseByteArrayToObj(sub,clazz, configList,0,sub.length));
        }

        return list;
    }


    private static <T> List<T> parseBytesToList(byte[] bytes, Class<T> clazz, int eleSize , int eleByteLength) throws Exception {
        List<T> list = new ArrayList<>();

        if(eleSize * eleByteLength != bytes.length  ){
            throw new Exception("解析字节数组为List失败！bytes的长度不是List包装对象所需字节数的整数倍! class :"+clazz.getName() +", 包装类型 : "+clazz.getDeclaringClass());
        }

        for(int i=0;i<eleSize;i++){
            int start = i*eleByteLength;
            int end = start+eleByteLength-1;
            byte[] sub = ByteUtil.subBytes(bytes,start,end);

            if(String.class.equals(clazz)){
                list.add((T)ByteUtil.parseBytesToString(sub));
            }else {
                list.add(parseByteArrayToObj(sub, clazz));
            }
        }

        return list;
    }

    /**
     * @param bytes
     * @param config
     * @return
     */
    private static GbValue parseBytesToGbValueByCovertConfig(byte[] bytes, CovertConfig config) throws Exception {
        GbValue gbValue = new GbValue();
        Object v = null;
        switch (config.getInputValueType()){
            case Short:
                v = new BigDecimal(ByteUtil.convertByteArrayToShort(bytes));
                v = processSequenceForDownload( (BigDecimal) v , config).shortValue();
                break;
            case Integer:
                v = new BigDecimal(ByteUtil.convertByteArrayToInt(bytes));
                v = processSequenceForDownload( (BigDecimal) v ,config).intValue();
                break;
            case Double:
                v = BigDecimal.valueOf(parseBytesToDouble(bytes));
                v = processSequenceForDownload( (BigDecimal) v , config).doubleValue();
                break;
            case String:
                v = ByteUtil.parseBytesToString(bytes);
                break;
            case Date:
                v = ByteUtil.convertByteArrayToDate(bytes);
                break;
            default:
                throw new Exception("暂不支持的类型: "+config.getInputValueType());
        }

        gbValue.setValue(v);
        return gbValue;

    }

    private static BigDecimal processSequenceForDownload(BigDecimal v, CovertConfig config) throws Exception {
        if(config.getSequence()==null){
            return v;
        }

        char[] chars = config.getSequence().toCharArray();
        List<Character> characterList =  ByteUtil.charArrayToList(chars);
        // 将执行顺序逆向
        Collections.reverse(characterList);

        for (char c:characterList) {
            v = reverseProcessOffSetAndScale(v, c, config.getOffset(), config.getScale(),config.getPrecision());
        }

        return v;
    }

    private static double parseBytesToDouble(byte[] bytes) throws Exception {
        long l = ByteUtil.convertByteArrayToLong(bytes);
        return new BigDecimal(l).doubleValue();
    }


    public static List<CovertConfig> getCovertConfigList(Class clazz) {
        return ALL_COVERT_CONFIG.get(clazz);
    }




    public static int getFieldByteLength(CovertConfig config, Object obj) throws Exception {

        int length = getByteLengthFromCovertConfig(config,obj);

        if(InputValueTypeEnum.List.equals(config.getInputValueType())){

            int size = getListSize(config, obj);

            length =  size * length ;
        }

        return length;
    }

    public static int getListSize(CovertConfig config, Object obj) throws Exception {

        // 集合类型Field的整体所需字节数 = 集合中元素个数 * 单个元素所需字节数
        if(config.getListSizeFieldName() == null){
            throw new Exception("listSizeFidldName为空,无法获取指定字段所需字节数! fieldName:"+config.getFieldName());
        }

        // 指定集合中元素个数的字段必定是 GbValue<Short>或者GbValue<Integer>
        return getIntValueByFieldName(obj,config.getListSizeFieldName());

    }


    /**
     *
     * @param obj
     * @param fieldName 必须是GbValue类型
     * @return
     */
    public static int getIntValueByFieldName( Object obj , String fieldName) throws IllegalAccessException {
        Field field = getFieldFromClass(obj.getClass(),fieldName);
        field.setAccessible(true);
        Object v = ((GbValue)field.get(obj)).getValue();
        if(v instanceof Short){
            return (short) v;
        }else {
            return (int) v;
        }
    }




    public static int getByteLengthFromCovertConfig(CovertConfig config , Object obj) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        if( config.getByteLength() != null ){
            return config.getByteLength();
        }


        if(config.getEleLengthFieldName() != null){
            return  getIntValueByFieldName(obj,config.getEleLengthFieldName());
        }


        Field field = config.getClazz().getDeclaredField(config.getFieldName());

        Class<?> type = null;

        // 不能确定字节长度的，只有对应字段的类型为List和Object，
        if(InputValueTypeEnum.List.equals(config.getInputValueType())){

             type = getWrapTypeFromListField(field);

        }else if( InputValueTypeEnum.Object.equals(config.getInputValueType()) ){

            type = field.getDeclaringClass();

        }


        List<CovertConfig> filedCovertConfigList = getCovertConfigList(type);

        int byteLength=0;

        for (CovertConfig c :  filedCovertConfigList ){
            if( c.getByteLength()!=null ){
                byteLength += c.getByteLength();
            }else {
                byteLength += getByteLengthFromCovertConfig(c,obj);
            }
        }

        return byteLength;
    }

    private static void checkCovertConfigList(List<CovertConfig> covertConfigList,Class<?> clazz) throws Exception {
        Optional<CovertConfig> optional = covertConfigList.stream()
                .filter(e -> !e.getClazz().equals(clazz))
                .findFirst();

        if(optional.isPresent()){
            throw new Exception("class不一致! 标准class : "+clazz.getName()+", 非标准class : "+optional.get().getClazz().getName());
        }
    }


    public static <T> T parseByteArrayToObj(byte[] allBytes , Class<T> clazz ,List<CovertConfig> covertConfigList ,int index ,int remainByteLength) throws Exception {

        if (CollectionUtils.isEmpty(covertConfigList)) {
            throw new Exception("未找到对应的CovertConfig配置! class:" + clazz.getName());
        }

        if (remainByteLength > allBytes.length) {
            throw new Exception("剩余可用字节数：" + remainByteLength + " 大于 字节数组大小:" + allBytes.length);
        }

        if (allBytes == null || allBytes.length==0 || remainByteLength == 0) {
            return null;
        }


        checkCovertConfigList(covertConfigList, clazz);

        T instance = clazz.newInstance();

        int fieldIndex = 0;

        while (index < allBytes.length ) {

            CovertConfig config = covertConfigList.get(fieldIndex);

            Field field =  getFieldFromClass(clazz,config.getFieldName());

            field.setAccessible(true);

            index += setFieldValueByCovertConfig(instance,allBytes,config,index);

            fieldIndex++;

        }

        return instance;
    }



    public static <T> T parseByteArrayToObj(byte[] allBytes , Class<T> clazz ) throws Exception {

        List<CovertConfig> covertConfigList = getCovertConfigList(clazz);

        return parseByteArrayToObj(allBytes,clazz,covertConfigList,0,allBytes.length);
    }


    public static int setFieldOnInstance(byte[] bytes, Object instance , List<CovertConfig> configList ) throws Exception {
        int start = 0;
        for( CovertConfig config : configList  ){
            int length = setFieldValueByCovertConfig(instance, bytes, config, start);
            start+=length;

        }

        return start;
    }
}
