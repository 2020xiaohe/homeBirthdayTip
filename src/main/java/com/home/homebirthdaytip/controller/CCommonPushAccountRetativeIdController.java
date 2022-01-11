package com.home.homebirthdaytip.controller;

import com.home.homebirthdaytip.domain.CCommonPushAccountRetativeId;
import com.home.homebirthdaytip.service.CCommonPushAccountRetativeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/subscribe")
public class CCommonPushAccountRetativeIdController {

    @Autowired
    private CCommonPushAccountRetativeIdService cCommonPushAccountRetativeIdService;
    /**
     * 进入订阅页面
     * @param model
     * @param appSecret
     * @return
     */
    @GetMapping("/orderOrcancel")
    public ModelAndView  subscribePage(Model model, String appSecret){
        model.addAttribute("id", appSecret);
        ModelAndView mv = new ModelAndView("cancelSubscribe");
        mv.addObject(model);
        return mv;
    }

    @PostMapping("/getSubscribeInitStatus")
    public CCommonPushAccountRetativeId getSubscribeInitStatus(String accountRetativeId){
        return cCommonPushAccountRetativeIdService.getSubscribeInitStatus(accountRetativeId);
    }

    @PostMapping("/updateSubscribeStatus")
    public Boolean updateSubscribeStatus(CCommonPushAccountRetativeId c){
        return cCommonPushAccountRetativeIdService.updateSubscribeStatus(c);
    }

}
