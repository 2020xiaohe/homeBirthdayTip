package com.home.homebirthdaytip.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.service.CCommonPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @author: hemb
 * @date: 2020年12月19日 14:21
 */
@RestController
public class CommPushController {

    @Autowired
    private CCommonPushService cCommonPushService;

    @GetMapping("getSendedCount")
    public String getSendedCount(){
        JSONObject json=new JSONObject();
        int count=cCommonPushService.countByPushTypeAndPushStatus(Constants.SERVICE_TYPE.yj.getIndex(),Constants.PUSH_STATUS.yjfs.getIndex());
        json.put("sendedCount", count);
        return  json.toString();
    }

    @GetMapping("getEmailImportTemplate")
    public  void getFile(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        new Document().downLoad(fileName,"邮件通知统一导入模板.xlsx",request,response);

        cCommonPushService.downloadExcelTemplate(fileName,"通知统一导入模板.xlsx",request,response);

    }

    @PostMapping("sEmailTaskExcelImport")
    public void excelImport(@RequestParam(value="myfile") MultipartFile myfile) throws Exception {
        if (myfile!=null && !myfile.isEmpty()) {
            List<CCommonPush> list = cCommonPushService.parsentExcelToEmails(myfile);
            if(!list.isEmpty()){
                cCommonPushService.saveOrUpdateBatch(list);
            }
        }
    }

    @RequestMapping("saveOrUpdateEmailTask")
    public void saveOrUpdateEmailTask(CCommonPush s){
        if (s.getId()==null || s.getId()<=0){
            // 0-等待发送 1-发送成功 2-发送异常
            s.setPushType(Constants.SERVICE_TYPE.yj.getIndex());
            s.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
            s.setPushCreateTime(new Date());
        }
        cCommonPushService.saveOrUpdate(s);
    }

    @RequestMapping("getAllByPage")
    public void getAllEmailTaskPageByPushStatus(HttpServletRequest request,HttpServletResponse response,String page,String rows,Integer type) throws Exception {
        int pageSize = 3;
        int pageNum = 0;
        if(rows!=null && !("").equals(rows)){
            pageSize = Integer.parseInt(rows);
        }
        if(page!=null && !("").equals(page)){
            pageNum = Integer.parseInt(page);
        }
        int pushStatus = type;

        IPage<CCommonPush> iPage = cCommonPushService.getByPage(pageNum,pageSize,Constants.SERVICE_TYPE.yj.getIndex(), pushStatus);
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(iPage.getRecords()));
        JSONObject json=new JSONObject();
        json.put("total", iPage.getTotal());
        json.put("rows", array);
        PrintWriter writer=response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }

    @PostMapping("getAllWxPushTaskPageByPushStatus")
    public void getAllWxPushTaskPageByPushStatus(HttpServletResponse response, String page, String rows,Integer type) throws Exception {
        int pageNum = 1;
        int pageSize = 3;
        if(rows!=null && !("").equals(rows)){
            pageSize = Integer.parseInt(rows);
        }
        if(page!=null && !("").equals(page)){
            pageNum = Integer.parseInt(page);
        }
        int pushStatus = type;
        IPage<CCommonPush> iPage = cCommonPushService.getByPage(pageNum,pageSize,Constants.SERVICE_TYPE.wx.getIndex(), pushStatus);
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(iPage.getRecords()));
        JSONObject json=new JSONObject();
        json.put("rows", array);
        json.put("total", iPage.getTotal());
        PrintWriter writer=response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }

    @RequestMapping("saveOrUpdateWxpushTask")
    public void saveOrUpdateWxpushTask(CCommonPush s){
        if (s.getId()==null || s.getId()<=0){
            // 0-等待发送 1-发送成功 2-发送异常
            s.setPushType(Constants.SERVICE_TYPE.wx.getIndex());
            s.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
            s.setPushCreateTime(new Date());
        }
        cCommonPushService.saveOrUpdate(s);
    }
}
