package com.funny.util;

import java.util.UUID;

/**
 * Created by liuxin on 2017/7/1.
 */
public class UUIDUtils {
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
