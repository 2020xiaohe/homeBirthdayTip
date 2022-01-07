package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.homebirthdaytip.common.Constants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送表(包含邮件、微信及短信)
 * @TableName c_common_push
 */
@TableName(value ="c_common_push")
@Data
public class CCommonPush implements Serializable {
    /**
     * 主键
     */
    @TableId(value ="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 推送类别
     */
    private Integer pushType;

    /**
     * 微信云--组合名称
     */
    @TableField(exist = false)
    private String wxYunTitle;

    /**
     * 微信云--推送类别名称
     */
    @TableField(exist = false)
    private String pushTypeName;

    /**
     * 推送主题
     */
    private String pushTheme;

    /**
     * 推送模板id
     */
    private String pushTemplateId;

    /**
     * 模板中参数
     */
    private String pushTemplateParams;


    /**
     *推送内容
     */
    private String pushArticle;

    /**
     * 接收账号
     */
    private String pushAccount;

    /**
     * 接收用户名称
     */
    private String pushAccountName;

    /**
     * 产生推送需求时间
     */
    private Date pushCreateTime;

    /**
     * 推送状态(0-等待发送 1-发送成功 2-发送异常)
     */
    private Integer pushStatus;

    /**
     * 开始推送时间
     */
    private Date pushStartTime;

    /**
     * 结束推送时间
     */
    private Date pushEndTime;

    public String getWxYunTitle() {
        return pushTheme+"->"+pushAccountName;
    }

    public String getPushTypeName() {
        return Constants.SERVICE_TYPE.getNameByIndex(pushType);
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CCommonPush other = (CCommonPush) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPushType() == null ? other.getPushType() == null : this.getPushType().equals(other.getPushType()))
            && (this.getPushTemplateId() == null ? other.getPushTemplateId() == null : this.getPushTemplateId().equals(other.getPushTemplateId()))
            && (this.getPushTemplateParams() == null ? other.getPushTemplateParams() == null : this.getPushTemplateParams().equals(other.getPushTemplateParams()))
            && (this.getPushAccount() == null ? other.getPushAccount() == null : this.getPushAccount().equals(other.getPushAccount()))
            && (this.getPushAccountName() == null ? other.getPushAccountName() == null : this.getPushAccountName().equals(other.getPushAccountName()))
            && (this.getPushCreateTime() == null ? other.getPushCreateTime() == null : this.getPushCreateTime().equals(other.getPushCreateTime()))
            && (this.getPushStatus() == null ? other.getPushStatus() == null : this.getPushStatus().equals(other.getPushStatus()))
            && (this.getPushStartTime() == null ? other.getPushStartTime() == null : this.getPushStartTime().equals(other.getPushStartTime()))
            && (this.getPushEndTime() == null ? other.getPushEndTime() == null : this.getPushEndTime().equals(other.getPushEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPushType() == null) ? 0 : getPushType().hashCode());
        result = prime * result + ((getPushTemplateId() == null) ? 0 : getPushTemplateId().hashCode());
        result = prime * result + ((getPushTemplateParams() == null) ? 0 : getPushTemplateParams().hashCode());
        result = prime * result + ((getPushAccount() == null) ? 0 : getPushAccount().hashCode());
        result = prime * result + ((getPushAccountName() == null) ? 0 : getPushAccountName().hashCode());
        result = prime * result + ((getPushCreateTime() == null) ? 0 : getPushCreateTime().hashCode());
        result = prime * result + ((getPushStatus() == null) ? 0 : getPushStatus().hashCode());
        result = prime * result + ((getPushStartTime() == null) ? 0 : getPushStartTime().hashCode());
        result = prime * result + ((getPushEndTime() == null) ? 0 : getPushEndTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pushType=").append(pushType);
        sb.append(", pushTemplateId=").append(pushTemplateId);
        sb.append(", pushTemplateParams=").append(pushTemplateParams);
        sb.append(", pushAccount=").append(pushAccount);
        sb.append(", pushAccountName=").append(pushAccountName);
        sb.append(", pushCreateTime=").append(pushCreateTime);
        sb.append(", pushStatus=").append(pushStatus);
        sb.append(", pushStartTime=").append(pushStartTime);
        sb.append(", pushEndTime=").append(pushEndTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}