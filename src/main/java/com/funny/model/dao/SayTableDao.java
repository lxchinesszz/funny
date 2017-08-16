package com.funny.model.dao;

import com.funny.model.domain.SayTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @Package: com.funny.model.dao
 * @Description: 根据日期获取分页
 * @author: liuxin
 * @date: 2017/8/6 下午9:31
 */
@Repository
public interface SayTableDao extends PagingAndSortingRepository<SayTable, String> {
    public Page<SayTable> findBytimestamp(String gender, Pageable pageable);
}
