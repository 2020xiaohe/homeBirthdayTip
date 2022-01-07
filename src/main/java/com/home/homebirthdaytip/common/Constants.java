package com.home.homebirthdaytip.common;

/**
 * @Description: 常量
 * @author: hemb
 * @date: 2021年02月27日 18:00
 */
public class Constants {
    public static final String HEFENG_URL = "https://way.jd.com/he/freeweather";
    public static final String HEFENG_CITY  = "zhangzhou";
    public static final String HEFENG_SECRET_KEY = "a8743414f6f497e244bad423dca658d2";

    //微信推送后点击的详情跳转地址
    public static final String TZ_REDICTURL ="";
    public static final String SRTX_REDICTURL ="";
    public static final String JTCYSRTX_REDICTURL ="";
    public static final String JRTX_REDICTURL ="";

    //短信推送配置
    public static final String SDK_APPID = "1400597606";
    public static final String SIGN_NAME = "小何之家个人网";
    public static final String COUNTRY_CODE ="+86";
    public static final String PHONE_NUM_CHECK = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    public static final String MAIN_URL="http://192.168.242.132:2900/";


    /**
     * 状态
     */
    public enum TB_STATUS {
        delete(0,"删除"), normal(1, "正常");

        private Integer index;
        private String name;

        // 构造方法
        TB_STATUS(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }
    }

    /**
     * 年度生日是否已经推送
     */
    public enum TB_YEAR_SEND_STATUS {
        no(0,"否"), yes(1, "是");

        private Integer index;
        private String name;

        // 构造方法
        TB_YEAR_SEND_STATUS(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }
    }


    public enum PUSH_STATUS {
        ddfs(0,"等待发送"), yjfs(1, "已经发送"), fsyc(2, "发送异常");

        private Integer index;
        private String name;

        // 构造方法
        PUSH_STATUS(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }
    }

    public enum SERVICE_TYPE {
        yj(0,"email"), wx(1, "weChat"), dx(2, "message");

        private Integer index;
        private String name;

        // 构造方法
        SERVICE_TYPE(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }

        public static String getNameByIndex(Integer index) {
            for (SERVICE_TYPE type : SERVICE_TYPE.values()) {
                if (type.getIndex().equals(index)) {
                    return type.getName();
                }
            }
            return null;
        }

    }

    public enum CRON_TYPE {
        bzrw(0,"布置工作定时器"),
        nldsq(1,"农历日期检查定时器"),
        yjdsq(2, "邮件推送定时器"),
        dxdsq(3, "短信推送定时器"),
        wxdsq(4, "微信推送定时器"),
        xldsq(5, "新历日期检查定时器"),
        hbdx(8, "汇报推送情况短信推送时间点");



        private Integer index;
        private String name;

        // 构造方法
        CRON_TYPE(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }
    }

    public enum DX_TEMPLATE_ENUM{
        tz("0","1207750"),
        srtx("1","1207782"),
        jtcysrtx("2","1210669"),
        xczf("3","1225427"),
        tshb("4","1268527");

        private String index;
        private String value;

        // 构造方法
        DX_TEMPLATE_ENUM(String index, String value) {
            this.value = value;
            this.index = index;
        }

        // get set 方法

        public String getIndex() {
            return index;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByIndex(String index) {
            for (DX_TEMPLATE_ENUM type : DX_TEMPLATE_ENUM.values()) {
                if (type.getIndex().equals(index)) {
                    return type.getValue();
                }
            }
            return null;
        }

    }




    public enum TEMPLATE_ENUM {
        tz("0","DadEf7Fk9QLbY8dqIvt_pTa2PA-eRj21V5nwOXljhkM"), srtx("1", "PaR5zgw7Ow_r1OlxICMvjJsxd-CfEgP7AlAlX3DFsTQ"),jtcysrtx("2", "2iPJ1bOB_U9eoqk1zWmQHOlBm-3bmCpmG56hk0SBtGc"),jrtx("3","cAOy75VnoBhc16dRv55eGYWGi3NtLrjZywUUOSiv2rs");

        private String index;
        private String value;

        // 构造方法
        TEMPLATE_ENUM(String index, String value) {
            this.value = value;
            this.index = index;
        }

        // get set 方法

        public String getIndex() {
            return index;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 文件类型(1、目录 2、文件)
     */
    public enum FILE_TYPE {
        ml(1,"目录"), wj(2, "文件");

        private Integer index;
        private String name;

        // 构造方法
        FILE_TYPE(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }
    }

    /**
     * 状态（1、正常或已同步  0、未同步或不正常）
     */
    public enum FILE_STATUS {
        wtb(0,"未同步"), ytb(1, "已同步");

        private Integer index;
        private String name;

        // 构造方法
        FILE_STATUS(Integer index, String name) {
            this.name = name;
            this.index = index;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public Integer getIndex() {
            return index;
        }
    }

    public enum FILE_SIZE_TYPE_ENUM {
        B("B","字节"),
        KB("KB", "千字节"),
        MB("MB", "兆字节"),
        GB("GB","千兆字节");

        private String index;
        private String value;

        // 构造方法
        FILE_SIZE_TYPE_ENUM(String index, String value) {
            this.value = value;
            this.index = index;
        }

        // get set 方法

        public String getIndex() {
            return index;
        }

        public String getValue() {
            return value;
        }
    }
}