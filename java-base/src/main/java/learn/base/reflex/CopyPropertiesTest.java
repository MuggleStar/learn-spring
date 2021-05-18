package learn.base.reflex;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射处理属性复制
 *
 * @author lujianrong
 * @date 2021/5/18
 */
public class CopyPropertiesTest {


    @Test
    public void testCopy() throws InvocationTargetException, IllegalAccessException {
        TestDtoA a = new TestDtoA();
        a.setId(1L);
        a.setMobile("15522223333");
        this.copy(a);
        System.out.println(a.toString());
    }

    public void copy(Object object) throws InvocationTargetException, IllegalAccessException {

        TestDtoB b = new TestDtoB();
        b.setName("BBBBBBBBBBB");

        Class<?> aClazz = object.getClass();
        Method[] methods = aClazz.getMethods();

        // 方法出入参数校验
        for (Method method : methods) {
            switch (method.getName()) {
                case "getId":
                    if (!Long.class.equals(method.getReturnType())){
                        System.out.println("参数类型不对getId");
                        return;
                    }
                    break;
                case "getName":
                    if (!String.class.equals(method.getReturnType())){
                        System.out.println("参数类型不对getName");
                        return;
                    }
                    break;
                case "getMobile":
                    if (!String.class.equals(method.getReturnType())){
                        System.out.println("参数类型不对getMobile");
                        return;
                    }
                    break;
                case "setMobile":
                    if (!String.class.equals(method.getParameterTypes()[0])){
                        System.out.println("参数类型不对setMobile");
                        return;
                    }
                    break;
                default:break;
            }
        }

        // 取值
        for (Method method : methods) {
            switch (method.getName()) {
                case "getId":
                    Object id = method.invoke(object);
                    if (id instanceof Long) {
                        b.setId((Long) id);
                    }
                    break;
                case "getMobile":
                    Object phone = method.invoke(object);
                    if (phone instanceof String) {
                        b.setPhone((String) phone);
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println(b.toString());

        // 赋值
        for (Method method : methods) {
            switch (method.getName()) {
                case "setName":
                    method.invoke(object,b.getName());
                    break;
                case "setId":
                    method.invoke(object,b.getId());
                    break;
                case "setTags":
                    List list = new ArrayList();
                    list.add(123);
                    list.add("123aaa");
                    method.invoke(object,list);
                    break;
                default:
                    break;
            }
        }
    }


    public static class TestDtoA {

        private Long id;
        private String name;
        private String mobile;
        private List<String> tags;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public String toString() {
            return "TestDtoA{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", tags=" + tags +
                    '}';
        }
    }

    public static class TestDtoB {

        private Long id;
        private String name;
        private String phone;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "TestDtoB{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }

    }

}
