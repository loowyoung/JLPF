package com.anxin.common.utils;

import java.util.Random;

/**
 * 生成指定位数的不重复数字（如短信验证码）
 */
public class GenerRandNum {
    public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
    public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
