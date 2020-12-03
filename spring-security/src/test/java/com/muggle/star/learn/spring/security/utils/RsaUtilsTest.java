package com.muggle.star.learn.spring.security.utils;

import org.junit.Test;

public class RsaUtilsTest {

    @Test
    public void test(){

        RsaUtils.getKey("123",10);

        RsaUtils.getKey("abc",10);
    }

}