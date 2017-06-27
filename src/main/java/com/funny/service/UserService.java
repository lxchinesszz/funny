package com.funny.service;

import com.funny.model.domain.User;
import org.springframework.stereotype.Service;



/**
 * Created by liuxin on 2017/6/27.
 */
@Service
public interface UserService {
    User login(User user);
}
