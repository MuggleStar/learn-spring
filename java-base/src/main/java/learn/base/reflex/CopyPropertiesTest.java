package learn.base.reflex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射处理属性复制
 *
 * @author lujianrong
 * @date 2021/5/18
 */
public class CopyPropertiesTest {


    /**
     * 处理所有类型
     * 1、Long createBy ==》String createByToName
     * 2、Long updateBy ==》String updateByToName
     *
     * @param object
     */
    public void handleObject(Object object) {
        List<Object> objectList = new ArrayList<>();
        objectList.add(object);
        this.handleObjectList(objectList);
    }

    /**
     * 处理所有类型
     * 1、Long createBy ==》String createByToName
     * 2、Long updateBy ==》String updateByToName
     *
     * @param objectList
     */
    public void handleObjectList(List<?> objectList) {

        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }

        // 类方法封装处理
        Class<?> clazz = objectList.get(0).getClass();
        Method[] methods = clazz.getMethods();

        Map<String,Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            methodMap.put(method.getName(),method);
        }

        // 找出匹配的属性方法
        Map<String,SysIdToNameEnum> idMethodMap = new HashMap<>();
        Map<String,SysIdToNameEnum> nameMethodMap = new HashMap<>();
        for (Method idMethod : methods) {
            SysIdToNameEnum sysIdToNameEnum = SysIdToNameEnum.getByIdMethod(idMethod.getName());
            if (sysIdToNameEnum == null) {
                continue;
            }
            // id类型匹配
            Class<?> idReturnType = idMethod.getReturnType();
            if (!sysIdToNameEnum.getIdType().equals(idReturnType)){
                continue;
            }

            // 名称类型匹配
            Method nameMethod = methodMap.get(sysIdToNameEnum.getNameSetMethod());
            Class<?>[] nameParameterTypes = nameMethod.getParameterTypes();
            if (nameParameterTypes.length != 1 || !sysIdToNameEnum.getNameType().equals(nameParameterTypes[0])){
                continue;
            }
            idMethodMap.put(idMethod.getName(),sysIdToNameEnum);
            nameMethodMap.put(nameMethod.getName(),sysIdToNameEnum);
        }
        if (idMethodMap.size()<=0) {
            return;
        }

        // 获取id参数
        List<SysIdToNameDTO> sysIdToNameDTOList = new ArrayList<>();
        for (Object object : objectList) {
            SysIdToNameDTO sysIdToNameDTO = new SysIdToNameDTO();
            Iterator<String> iterator = idMethodMap.keySet().iterator();
            while (iterator.hasNext()) {
                SysIdToNameEnum sysIdToNameEnum = idMethodMap.get(iterator.next());
                this.handleIdParam(object,sysIdToNameDTO,sysIdToNameEnum);
                sysIdToNameDTOList.add(sysIdToNameDTO);
            }
        }

        // 查询取值
        this.handleSysIdToNameDTOList(sysIdToNameDTOList);

        // 设置名称数据
        for (int i = 0; i < objectList.size(); i++) {
            Object object = objectList.get(i);
            SysIdToNameDTO sysIdToNameDTO = sysIdToNameDTOList.get(i);
            Iterator<String> iterator = nameMethodMap.keySet().iterator();
            while (iterator.hasNext()) {
                SysIdToNameEnum sysIdToNameEnum = nameMethodMap.get(iterator.next());
                this.handleNameParam(object,sysIdToNameDTO,sysIdToNameEnum);
            }
        }

    }


    /**
     * 查询并赋值
     *
     * @param sysIdToNameDTOList
     */
    private void handleSysIdToNameDTOList(List<SysIdToNameDTO> sysIdToNameDTOList) {

        // 查询数据

    }


    /**
     * 赋值 SysIdToNameDTO 名称 Object
     * @param object
     * @param sysIdToNameDTO
     * @param idToNameEnum
     */
    private void handleNameParam (Object object, SysIdToNameDTO sysIdToNameDTO, SysIdToNameEnum idToNameEnum) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();

        Map<String,Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            methodMap.put(method.getName(),method);
        }
        Method nameSetMethod = methodMap.get(idToNameEnum.getNameSetMethod());
        try {
            switch (idToNameEnum) {
                case createBy_createByToName:
                    if (sysIdToNameDTO.getCreateByToName() != null) {
                        nameSetMethod.invoke(object,sysIdToNameDTO.getCreateByToName());
                    }
                    break;
                case updateBy_updateByToName:
                    if (sysIdToNameDTO.getUpdateByToName() != null) {
                        nameSetMethod.invoke(object,sysIdToNameDTO.getUpdateByToName());
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }
    }


    /**
     * 赋值 id参数到 SysIdToNameDTO
     * @param object
     * @param sysIdToNameDTO
     * @param idToNameEnum
     */
    private void handleIdParam (Object object, SysIdToNameDTO sysIdToNameDTO, SysIdToNameEnum idToNameEnum) {

        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();

        Map<String,Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            methodMap.put(method.getName(),method);
        }
        Method idGetMethod = methodMap.get(idToNameEnum.getIdGetMethod());
        try {
            Object idData = idGetMethod.invoke(object);
            switch (idToNameEnum) {
                case createBy_createByToName:
                    sysIdToNameDTO.setCreateBy((Long) idData);
                    break;
                case updateBy_updateByToName:
                    sysIdToNameDTO.setUpdateBy((Long) idData);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }
    }



    @Data
    public static class SysIdToNameDTO {

        /** 创建人id ==> 名称*/
        private Long createBy;
        private String createByToName;

        /** 更新人id ==> 名称*/
        private Long updateBy;
        private String updateByToName;

    }



    @Getter
    @AllArgsConstructor
    public enum SysIdToNameEnum {

        // 兼容的id转名称
        createBy_createByToName("getCreateBy","setCreateByToName",Long.class,String.class,"系统用户id=>名称"),
        updateBy_updateByToName("getUpdateBy","setUpdateByToName",Long.class,String.class,"系统用户id=>名称"),
        ;

        /**
         * id 方法名
         */
        private String idGetMethod;
        /**
         * 名称 方法名
         */
        private String nameSetMethod;
        /**
         * id 类型
         */
        private Class<?> idType;
        /**
         * 名称类型
         */
        private Class<?> nameType;
        /**
         * 描述
         */
        private String description;


        public static Map<String,SysIdToNameEnum> lookUp = new HashMap<>();

        static {
            for (SysIdToNameEnum current : SysIdToNameEnum.values()) {
                lookUp.put(current.getIdGetMethod(),current);
            }
        }

        public static SysIdToNameEnum getByIdMethod(String idMethod) {
            return lookUp.get(idMethod);
        }
    }
}
