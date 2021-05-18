package learn.base.reflex;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        for (Method method : methods) {
            switch (method.getName()) {
                case "setName":
                    method.invoke(object,b.getName());
                    break;
                case "setId":
                    method.invoke(object,b.getId());
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

        @Override
        public String toString() {
            return "TestDtoA{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
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
