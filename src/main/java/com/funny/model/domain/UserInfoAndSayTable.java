package com.funny.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Package: com.funny.model.domain
 * @Description: ${todo}
 * @author: liuxin
 * @date: 2017/9/17 下午8:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoAndSayTable {
    private Map<String,Object> userInfoSay;
}
