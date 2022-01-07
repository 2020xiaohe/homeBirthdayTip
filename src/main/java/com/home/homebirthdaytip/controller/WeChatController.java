package com.home.homebirthdaytip.controller;

import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.FileUtils;
import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.*;
import com.home.homebirthdaytip.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/weChat/yun")
public class WeChatController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private WWechatYunUserService wWechatYunUserService;
    @Autowired
    private WWechatYunNoticeService wWechatYunNoticeService;
    @Autowired
    private WWechatYunAuthService wWechatYunAuthService;
    @Autowired
    private WWechatYunMenuService wWechatYunMenuService;
    @Autowired
    private WWechatYunFilesService wWechatYunFilesService;
    @Autowired
    private WWechatYunFileLogsService wWechatYunFileLogsService;
    @Autowired
    private JJobsService jJobsService;
    @Autowired
    private CCommonPushService cCommonPushService;


    /**
     * 云服务模块--用户登录
     * @param code
     * @return
     */
    @PostMapping("/wxLogin")
    public Result wxLogin(String code){
       return  weChatService.wxLogin(code);
    }


    /**
     * 云服务模块--编辑用户基本信息及更新用户在线、离线状态
     * @param user
     * @return
     */
    @PostMapping("/editUserOrLoginStatus")
    public Result editUserOrLoginStatus(WWechatYunUser user){
//        System.out.println(user.toString());
        return weChatService.editUserOrLoginStatus(user);
    }

    /**
     * 云服务模块--地图打点
     * @return
     */
    @PostMapping("/getUserMarkers")
    public Result getUserMarkers(){
        try {
            List<WWechatYunUser> users = wWechatYunUserService.getUserForMarkers();
            return Result.ok().count(users.size()).data(users);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error();
        }
    }

    /**
     * 云服务模块--公告
     * @param nowTime 小程序前端时间
     * @return
     */
    @PostMapping("/getNotices")
    public Result getNotices(String nowTime) {
        try {
            List<WWechatYunNotice> noticeList = wWechatYunNoticeService.getAllNotices(nowTime);
            return Result.ok().count(noticeList.size()).data(noticeList);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error();
        }
    }

    /**
     * 云服务模块--新增或修改菜单
     * @param wWechatYunMenu
     * @return
     */
    @PostMapping("/editMenu")
    public Result editMenu(WWechatYunMenu wWechatYunMenu){
        try {
            if(wWechatYunMenuService.saveOrUpdate(wWechatYunMenu)){
                return Result.ok();
            }else {
                return Result.error();
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error();
        }
    }

    /**
     * 添加权限
     * @param openId
     * @param menuid
     * @return
     */
//    @PostMapping("/editAuth")
//    public Result editAuth(String openId,String menuid){
//        WWechatYunAuth auth = new WWechatYunAuth();
//        auth.setOpenid(openId);//"oq9pN4wS1AFE8NaIYf1RaQlyEmXE"
//        auth.setMenuid(menuid);//"7a7ad27e7bf86825873696f6c96982c7"
//        auth.setCreateTime(new Date());
//        auth.setStatus(Constants.TB_STATUS.normal.getIndex());
//        wWechatYunAuthService.save(auth);
//        return Result.ok();
//    }

    /**
     * 云服务模块--菜单展示
     * @return
     */
    @PostMapping("/menuList")
    public Result menuList(String openId){
        try {
            List<WWechatYunMenu> menuList = new ArrayList<>();
            List<WWechatYunAuth> auths = wWechatYunAuthService.getAuthors(openId);
            if(!auths.isEmpty()){
                List<String> menuIds = new ArrayList<>();
                for (WWechatYunAuth auth : auths) {
                    menuIds.add(auth.getMenuid());
                }
                menuList=wWechatYunMenuService.getMenuListByMenuIds(menuIds);
            }
            return Result.ok().data(menuList);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error();
        }
    }

    /**
     * 云服务模块--保存上传的图片/文件
     * @param openId 操作者
     * @param img 图片
     * @return
     */
    @PostMapping("/uploadImage")
    public Result uploadImage(String openId,@RequestParam("file")MultipartFile img){
        return weChatService.saveImage(openId,img);
    }

    /**
     * 云服务模块--图片列表
     * @param openId 操作者
     * @return
     */
    @PostMapping("/downloadImagesList")
    public Result downloadImagesList(String openId){
        return weChatService.downloadImagesList(openId);
    }

    /**
     * 云服务模块--图片列表--详情
     * @param openId 操作者
     * @param listTime 日期
     * @return
     */
    @PostMapping("/getImagesByUploadTime")
    public Result getImagesByUploadTime(String openId, String listTime){
        return wWechatYunFilesService.getImagesByUploadTime(openId,listTime);
    }


    /**
     * 根据路径获取图片用于预览
     * @param id 图片记录主键
     * @param response
     */
    @GetMapping("/getImages")
    public void getImageByPath(String id, HttpServletResponse response) {
        WWechatYunFiles wWechatYunFiles = wWechatYunFilesService.getById(id);
        String filePathPrefix="";
        if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
            filePathPrefix = wWechatYunFiles.getWinPath();
        }else{
            filePathPrefix = wWechatYunFiles.getLinPath();
        }
        String imgPath = filePathPrefix+wWechatYunFiles.getFileSuffix();
        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        InputStream inputStreamTmp = null;
        try {
            File picFile = new File(imgPath);
            response.setContentType("image/jpeg; charset=UTF-8");
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(picFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStreamTmp) {
                try {
                    inputStreamTmp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 云服务模块--图片列表--详情--删除图片
     * @param id 图片记录主键
     * @return
     */
    @PostMapping("/deleteImage")
    public Result deleteImage(String id){
        try {
            WWechatYunFiles w = wWechatYunFilesService.getById(id);
            w.setStatus(Constants.TB_STATUS.delete.getIndex());
            wWechatYunFilesService.saveOrUpdate(w);
            return Result.ok();
        } catch (Exception e) {
           logger.error(e.toString());
           return Result.error().data("删除失败");
        }
    }

    /***
     * 文件上传日志
     * @param openId  操作人
     * @param uploadResult 上传结果集
     * @return
     */
    @PostMapping("/setFileLogList")
    public Result setFileLogList(String openId,String uploadResult){
        return wWechatYunFileLogsService.saveLogs(openId,uploadResult);
    }


    /**
     * 云服务模块--用户文件日志
     * @param openId 当前用户唯一标识
     * @return
     */
    @PostMapping("/fileLogList")
    public Result fileLogList(String openId){
        try {
            List<WWechatYunFileLogs> fileLogs = wWechatYunFileLogsService.getLogs(openId);
            return Result.ok().count(fileLogs.size()).data(fileLogs);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error();
        }
    }

    /**
     * 获取所有定时器表达式
     * @return
     */
    @GetMapping("/getIcrons")
    public Result getIcrons(){
        return Result.ok().data(jJobsService.list());
    }

    /**
     * 保存或修改定时器表达式
     * @param emailIcron
     * @param messageIcron
     * @param wechatIcron
     * @param lunerIcron
     * @return
     */
    @PostMapping("/saveOrUpdateCrons")
    public Result saveOrUpdateCrons(String emailIcron,String messageIcron,
                                    String wechatIcron,String lunerIcron,String newDayIcron){
        List<JJobs> jobs = jJobsService.list();
        for (JJobs job : jobs) {
            if(Constants.CRON_TYPE.nldsq.getIndex().equals(job.getCronType())){
                job.setCron(lunerIcron);
            }else if(Constants.CRON_TYPE.yjdsq.getIndex().equals(job.getCronType())){
                job.setCron(emailIcron);
            }else if(Constants.CRON_TYPE.dxdsq.getIndex().equals(job.getCronType())){
                job.setCron(messageIcron);
            }else if(Constants.CRON_TYPE.wxdsq.getIndex().equals(job.getCronType())){
                job.setCron(wechatIcron);
            }else if(Constants.CRON_TYPE.xldsq.getIndex().equals(job.getCronType())){
                job.setCron(newDayIcron);
            }
        }
        if(jJobsService.saveOrUpdateBatch(jobs)){
            return Result.ok().data("保存成功");
        }else{
            return Result.error().data("保存失败");
        }
    }

    @PostMapping("/bachSavePushEmail")
    public Result bachSavePushEmail(String openId,@RequestParam(value="myfile") MultipartFile myfile){
        if (myfile!=null && !myfile.isEmpty()) {
            List<CCommonPush> list = cCommonPushService.parsentExcelToEmails(myfile);
            if(!list.isEmpty()){
                cCommonPushService.saveOrUpdateBatch(list);
//                for (CCommonPush cCommonPush : list) {
//                    System.out.println(cCommonPush);
//                }
            }
            logger.info(openId+"本次共导入"+list.size()+"条记录");
            return Result.ok().data("本次共导入"+list.size()+"条记录");
        }else {
            return Result.ok().data("文件为空！");
        }
    }

    @GetMapping("/getCommPushCondition")
    public Result getCommPushCondition(){
        return weChatService.getCommPushCondition();
    }

}
