package com.home.homebirthdaytip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 注意:如果只是使用@RestController注解Controller，
 * 则Controller中的方法无法返回jsp页面，或者html，
 * 配置的视图解析器 InternalResourceViewResolver不起作用，
 * 返回的内容就是Return 里的内容。
 * @author: hemb
 * @date: 2020年10月24日 15:19
 */

@Controller
public class PageController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/regist")
    public String register() {
        return "registPage";
    }

    @GetMapping("/wxPushImport")
    public String wxPushImport() {
        return "wxPushImport";
    }

    @GetMapping("/emailPushImport")
    public String emailPushImport() {
        return "emailPushImport";
    }

    @GetMapping("/sendMessage")
    public String sendMessage() {
        return "sendMessage";
    }

    @GetMapping("/pushStatistics")
    public String pushStatistics() {
        return "pushStatistics";
    }

}
