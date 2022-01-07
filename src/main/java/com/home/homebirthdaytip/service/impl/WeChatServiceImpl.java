package com.home.homebirthdaytip.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.*;
import com.home.homebirthdaytip.domain.*;
import com.home.homebirthdaytip.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WeChatServiceImpl implements WeChatService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * AppId
     */
    @Value("${weixin.jscode2sessionUrl}")
    private String requestUrl;

    /**
     * AppId
     */
    @Value("${weixin.yun_appId}")
    private String appId;

    /**
     * 秘钥
     */
    @Value("${weixin.yun_appSecret}")
    private String appSecret;

    /**
     * Windows文件上传路径
     */
    @Value("${sysPath.windowsUploadPath}")
    private  String winPath;

    /**
     * linux文件上传路径
     */
    @Value("${sysPath.linuxUploadPath}")
    private  String linPath;

    @Autowired
    private WWechatYunUserService wWechatYunUserService;
    @Autowired
    private WWechatYunFilesService wWechatYunFilesService;
    @Autowired
    private WWechatYunMenuService  wWechatYunMenuService;
    @Autowired
    private WWechatYunAuthService wWechatYunAuthService;
    @Autowired
    private CCommonPushService cCommonPushService;

    @Override
    public Result wxLogin(String code) {
        String url = requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        try {
            String res = "";
            res = HttpUtils.sendGet(url);
            if (StringUtils.isNotBlank(res)) {
//                System.out.println(res);
                JSONObject jo = JSON.parseObject(res);
                String openid = jo.getString("openid");
                logger.info(commonUtils.log(openid,"登录了该平台"));
                WWechatYunUser w = wWechatYunUserService.getById(openid);
                if(w==null){
                    WWechatYunUser wWechatYunUser = new WWechatYunUser();
                    wWechatYunUser.setOpenId(openid);
                    wWechatYunUser.setOnlineStatus(Constants.TB_STATUS.normal.getIndex());
                    wWechatYunUserService.save(wWechatYunUser);

                    //创建目录
                    String filePathPrefix ="";
                    if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
                        filePathPrefix = winPath;
                    }else{
                        filePathPrefix = linPath;
                    }
                    String dirPath = filePathPrefix+openid;
                    File file = new File(dirPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    //写入文件信息表
                    WWechatYunFiles wechatYunFile = new WWechatYunFiles();
                    wechatYunFile.setLinPath(linPath);
                    wechatYunFile.setWinPath(winPath);
                    wechatYunFile.setFileSuffix(openid);
                    wechatYunFile.setFileType(Constants.FILE_TYPE.ml.getIndex());
                    wechatYunFile.setStatus(Constants.TB_STATUS.normal.getIndex());
                    wechatYunFile.setUploadUser("oq9pN4wS1AFE8NaIYf1RaQlyEmXE");//项目所拥有人
                    wechatYunFile.setUploadTime(new Date());
                    wWechatYunFilesService.save(wechatYunFile);

                    //初始化菜单
                    List<WWechatYunAuth> wWechatYunAuths = new ArrayList<>();
                    //默认在2021-09-10 00:00:00前的记录为基础菜单
                    List<WWechatYunMenu> wWechatYunMenus = wWechatYunMenuService.getBasicMenu("2021-09-10 00:00:00");
                    for (WWechatYunMenu wWechatYunMenu : wWechatYunMenus) {
                        WWechatYunAuth wWechatYunAuth = new WWechatYunAuth();
                        wWechatYunAuth.setOpenid(openid);
                        wWechatYunAuth.setMenuid(wWechatYunMenu.getId());
                        wWechatYunAuth.setCreateTime(new Date());
                        wWechatYunAuth.setStatus(Constants.TB_STATUS.normal.getIndex());
                        wWechatYunAuths.add(wWechatYunAuth);
                    }
                    wWechatYunAuthService.saveOrUpdateBatch(wWechatYunAuths);
                }
                return Result.ok().data(jo);
            } else {
                return Result.error().data("请求结果为空");
            }
        } catch (Exception e) {
            logger.error("微信请求登录失败,请求错误具体为:" + e.toString());
            return Result.error().data(e.toString());
        }
    }

    @Override
    public Result saveImage(String openId, MultipartFile img) {
        WWechatYunFiles wWechatYunFiles =new WWechatYunFiles();
        wWechatYunFiles.setWinPath(winPath);
        wWechatYunFiles.setLinPath(linPath);
        wWechatYunFiles.setFileType(Constants.FILE_TYPE.wj.getIndex());
        wWechatYunFiles.setUploadTime(new Date());
        wWechatYunFiles.setUploadUser(openId);
        wWechatYunFiles.setStatus(Constants.TB_STATUS.normal.getIndex());
        String filePathPrefix ="";
        if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
            filePathPrefix = winPath;
        }else{
            filePathPrefix = linPath;
        }
        String dirPath = filePathPrefix+openId;  //获取根目录
        String date = DateUtils.formatDate(new Date(),DateUtils.YYYYMMDD);
        dirPath+="/"+date;
        File dictionary = new File(dirPath);
        if (!dictionary.exists()) {
            dictionary.mkdirs();
            //插入目录日志
            WWechatYunFiles ml =new WWechatYunFiles();
            ml.setWinPath(winPath);
            ml.setLinPath(linPath);
            ml.setFileSuffix(date);
            ml.setFileType(Constants.FILE_TYPE.ml.getIndex());
            ml.setUploadTime(new Date());
            ml.setUploadUser(openId);
            ml.setStatus(Constants.TB_STATUS.normal.getIndex());
            wWechatYunFilesService.save(ml);
        }

        wWechatYunFiles.setFileSize(FileUtils.storageSizeConversion(img.getSize(),Constants.FILE_SIZE_TYPE_ENUM.MB.getIndex()));
        String imgName = img.getOriginalFilename();
        String imgSuffixName = imgName.substring(imgName.lastIndexOf("."));//文件后缀名
        String imgNewName = UUID.randomUUID().toString()+imgSuffixName;
        wWechatYunFiles.setFileSuffix(openId+"/"+date+"/"+imgNewName);
        String imgPath = dirPath+"/"+imgNewName;
        File file  =  new File(imgPath);
        try {
            img.transferTo(file);//保存文件
            wWechatYunFilesService.save(wWechatYunFiles);//文件表
            return Result.ok().data("在"+DateUtils.formatDate(new Date(),DateUtils.PATTERN_24TIME)+"上传成功");
        } catch (Exception e) {
             logger.error("在"+DateUtils.formatDate(new Date(),DateUtils.PATTERN_24TIME)+"上传失败,具体错误为:" + e.toString());
            return Result.error().data(e.toString());
        }
    }

    @Override
    public Result downloadImagesList(String openId) {
        List<WWechatYunFiles> downloadImagesList = wWechatYunFilesService.getDownloadImagesList(openId);
        return Result.ok().count(downloadImagesList.size()).data(downloadImagesList);
    }

    @Override
    public Result getCommPushCondition() {
        List<CCommonPush> cCommonPushes = cCommonPushService.getCommPushList();
        return Result.ok().data(cCommonPushes);
    }

    @Override
    public Result editUserOrLoginStatus(WWechatYunUser user) {
        WWechatYunUser yunUser = wWechatYunUserService.getById(user.getOpenId());
        if (yunUser != null) {
            user.setOnlineHeadIcronId(yunUser.getOnlineHeadIcronId());
            user.setOutlineHeadIcronId(yunUser.getOutlineHeadIcronId());
        }

        //初次登录制作用户在线离线头像
        if((user.getOnlineHeadIcronId()==null || user.getOutlineHeadIcronId()==null) && user.getAvatarUrl()!=null){

            if(user.getOnlineHeadIcronId()==null){
                WWechatYunFiles wWechatYunFiles =new WWechatYunFiles();
                wWechatYunFiles.setWinPath(winPath);
                wWechatYunFiles.setLinPath(linPath);
                wWechatYunFiles.setFileType(Constants.FILE_TYPE.wj.getIndex());
                wWechatYunFiles.setUploadTime(new Date());
                wWechatYunFiles.setUploadUser(user.getOpenId());
                //头像不在列展示
                wWechatYunFiles.setStatus(Constants.TB_STATUS.delete.getIndex());
                String filePathPrefix ="";
                if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
                    filePathPrefix = winPath;
                }else{
                    filePathPrefix = linPath;
                }
                String dirPath = filePathPrefix+user.getOpenId();  //获取根目录
                String date = DateUtils.formatDate(new Date(),DateUtils.YYYYMMDD);
                dirPath+="/"+date;
                File dictionary = new File(dirPath);
                if (!dictionary.exists()) {
                    dictionary.mkdirs();
                    //插入目录日志
                    WWechatYunFiles ml =new WWechatYunFiles();
                    ml.setWinPath(winPath);
                    ml.setLinPath(linPath);
                    ml.setFileSuffix(date);
                    ml.setFileType(Constants.FILE_TYPE.ml.getIndex());
                    ml.setUploadTime(new Date());
                    ml.setUploadUser(user.getOpenId());
                    ml.setStatus(Constants.TB_STATUS.normal.getIndex());
                    wWechatYunFilesService.save(ml);
                }

                String imgName = "headIcron.png";
                String imgSuffixName = imgName.substring(imgName.lastIndexOf("."));//文件后缀名
                String imgNewName = UUID.randomUUID().toString()+imgSuffixName;
                wWechatYunFiles.setFileSuffix(user.getOpenId()+"/"+date+"/"+imgNewName);
                String imgPath = dirPath+"/"+imgNewName;
                PictureUtils.cutHeadImages(user.getAvatarUrl(),imgPath);
                wWechatYunFilesService.save(wWechatYunFiles);
                user.setOnlineHeadIcronId(wWechatYunFiles.getId());
            }
            if(user.getOutlineHeadIcronId()==null && user.getOnlineHeadIcronId()!=null){
                //获取登录态头像
                WWechatYunFiles onLineCron = wWechatYunFilesService.getById(user.getOnlineHeadIcronId());
                String filePathPrefix ="";
                if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
                    filePathPrefix = onLineCron.getWinPath();
                }else{
                    filePathPrefix = onLineCron.getLinPath();
                }
                String onLineIcronPath = filePathPrefix+onLineCron.getFileSuffix();

                //将头像置灰并保存
                WWechatYunFiles wWechatYunFiles =new WWechatYunFiles();
                wWechatYunFiles.setWinPath(winPath);
                wWechatYunFiles.setLinPath(linPath);
                wWechatYunFiles.setUploadTime(new Date());
                wWechatYunFiles.setUploadUser(user.getOpenId());
                //头像不在列展示
                wWechatYunFiles.setStatus(Constants.TB_STATUS.delete.getIndex());
                wWechatYunFiles.setFileType(Constants.FILE_TYPE.wj.getIndex());
                String dirPath = filePathPrefix+user.getOpenId();  //获取根目录
                String date = DateUtils.formatDate(new Date(),DateUtils.YYYYMMDD);
                dirPath+="/"+date;
                File dictionary = new File(dirPath);
                if (!dictionary.exists()) {
                    dictionary.mkdirs();
                    //插入目录日志
                    WWechatYunFiles ml =new WWechatYunFiles();
                    ml.setWinPath(winPath);
                    ml.setLinPath(linPath);
                    ml.setFileSuffix(date);
                    ml.setFileType(Constants.FILE_TYPE.ml.getIndex());
                    ml.setUploadTime(new Date());
                    ml.setUploadUser(user.getOpenId());
                    ml.setStatus(Constants.TB_STATUS.normal.getIndex());
                    wWechatYunFilesService.save(ml);
                }

                String imgName = "headIcron.png";
                String imgSuffixName = imgName.substring(imgName.lastIndexOf("."));//文件后缀名
                String imgNewName = UUID.randomUUID().toString()+imgSuffixName;
                wWechatYunFiles.setFileSuffix(user.getOpenId()+"/"+date+"/"+imgNewName);
                String imgPath = dirPath+"/"+imgNewName;
                //头像置灰
                PictureUtils.RGBPictureToDark(onLineIcronPath,imgPath);
                wWechatYunFilesService.save(wWechatYunFiles);
                user.setOutlineHeadIcronId(wWechatYunFiles.getId());
            }
        }
        //变更用户信息与登录状态
        if(wWechatYunUserService.saveOrUpdate(user)){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

//    public static void main(String[] args) {
//        for (int i = 0; i <5; i++) {
//            System.out.println("本次生成的UUID:"+ UUID.randomUUID().toString());
//        }
//    }
}
