package com.home.homebirthdaytip.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.homebirthdaytip.domain.TUser;
import com.home.homebirthdaytip.service.TUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: hemb
 * @date: 2020年10月19日 17:01
 */
@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TUserService userService;

    @PostMapping("/doRegister")
    public boolean addUser(TUser user){
        boolean flag = false;
        flag = userService.save(user);
        return flag;
    }
    @GetMapping("/updateUser")
    @ResponseBody
    public String updateUser(TUser user){
        String result="";
        if (user.getId()!=null && user.getId()!=0){
            userService.updateUser(user);
            result="修改成功";
        }else {
            result="修改失败，id不能为空";
        }
        return result;
    }

    @PostMapping("/getAllUserSelect")
    @ResponseBody
    public String getAllUserSelect(){
        List<Map<String,Object>> lists = new ArrayList<>();
        JSONObject json=new JSONObject();
        List<TUser> users = userService.list();
        for (TUser t:users) {
            Map<String,Object> map = new HashMap<>();
            map.put("index",t.getId());
            map.put("value",t.getName());
            lists.add(map);
        }
        json.put("allUsers", lists);
        return json.toString();
    }

    @GetMapping("/getUserOpenId")
    @ResponseBody
    public String getUserOpenId(Integer id){
        TUser t = userService.getById(id);
        return t.getWxPushUserId();
    }

    @GetMapping("/checkUserName")
    @ResponseBody
    public boolean checkUserName(String name){
        boolean flag = false;
        TUser t = userService.findUserByName(name);
        if (t == null){
            flag=true;
        }
        return flag;
    }

//    public static void main(String[] args){
//        String a=",";
//        String b="ffff，saaa，ssss";
//         System.out.println(b);
//          b=b.replaceAll("/,",a);
//          System.out.println(b);
//    }
}
