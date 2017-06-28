package com.funny.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxin on 2017/6/28.
 * 视图
 */
@Controller
public class TestController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(String name, Model model){
        model.addAttribute("name",name);
        return "customer";
    }
}
