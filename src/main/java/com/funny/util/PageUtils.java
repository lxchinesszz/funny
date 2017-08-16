package com.funny.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @Package: com.funny.util
 * @Description: ${todo}
 * @author: liuxin
 * @date: 2017/8/6 下午9:35
 */
public class PageUtils {
    /**
     * * 创建分页请求.
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize) {
        Sort sort =
                new Sort(Sort.Direction.DESC, "timestamp");
        //参数1表示当前第几页,参数2表示每页的大小,参数3表示排序
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }
}
