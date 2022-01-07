package com.home.homebirthdaytip.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.HHomeMember;
import com.home.homebirthdaytip.service.HHomeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: hemb
 * @date: 2020年12月21日 18:23
 */
@RestController
public class HomeMemberController {

    @Autowired
    private HHomeMemberService hHomeMemberService;

    @PostMapping("/getAlllBirthDay")
    @ResponseBody
    public String getAllNormalBirthDay(){
        List<HHomeMember> members=hHomeMemberService.getAllMembers();
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(members));
        JSONObject json=new JSONObject();
        json.put("data", array);
        return json.toString();
    }

    @PostMapping("/saveBatchHomeBirthDay")
    public void saveHomeBirthDay(String[] ids, String[] name, String[] oldBirthDay
            , String[] newBirthDay, String[] phoneNumber, String[] seqs,String[] wxOpenId, HttpServletResponse response) throws Exception {
        List<HHomeMember> homeBirthdayTimes = new ArrayList<>();
        if (name != null) {
            for (int i = 0; i < name.length; i++) {
                HHomeMember h = new HHomeMember();
                if (ids.length != 0) {
                    if (ids[i] == null || ("").equals(ids[i])) {
                        h.setMeaasgeIsSended(Constants.TB_YEAR_SEND_STATUS.no.getIndex());
                    } else {
                        h.setId(Integer.parseInt(ids[i]));
                    }
                } else {
                    h.setMeaasgeIsSended(Constants.TB_YEAR_SEND_STATUS.no.getIndex());
                }


                h.setName(name[i]);
                h.setBirthday(newBirthDay[i]);
                h.setOldBirthday(oldBirthDay[i]);
                h.setPhoneNumber(phoneNumber[i]);
                h.setStatus(String.valueOf(Constants.TB_STATUS.normal.getIndex()));
                h.setSeq(Integer.parseInt(seqs[i]));
                h.setWxOpenId(wxOpenId[i]);
                homeBirthdayTimes.add(h);
            }
        }
        hHomeMemberService.saveOrUpdateBatch(homeBirthdayTimes);
        response.sendRedirect("/index");
    }

//    public void sendMessage() throws Exception {
//        ZhenziSmsClient client = new ZhenziSmsClient("", "", "");
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("number", "15811111111");
//        params.put("templateId", "0");
//        String[] templateParams = new String[2];
//        templateParams[0] = "3421";
//        templateParams[1] = "5分钟";
//        params.put("templateParams", templateParams);
//        String result = client.send(params);
//    }

    @GetMapping("/delete")
    public void delete(Integer id) {
        HHomeMember h = hHomeMemberService.getById(id);
        h.setStatus(String.valueOf(Constants.TB_STATUS.delete.getIndex()));
        hHomeMemberService.saveOrUpdate(h);
    }
}
