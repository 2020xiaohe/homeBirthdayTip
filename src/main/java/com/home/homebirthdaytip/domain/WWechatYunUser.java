package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信云用户表
 * @TableName w_wechat_yun_user
 */
@TableName(value ="w_wechat_yun_user")
@Data
public class WWechatYunUser implements Serializable {
    /**
     * 用户唯一标识
     */
    @TableId
    private String openId;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 城市
     */
    private String city;

    /**
     * 省会
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 在线状态（0、离线 1、在线）
     */
    private Integer onlineStatus;

    /**
     * 在线头像
     */
    private String onlineHeadIcronId;

    /**
     * 离线头像
     */
    private String outlineHeadIcronId;

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
        WWechatYunUser other = (WWechatYunUser) that;
        return (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getAvatarUrl() == null ? other.getAvatarUrl() == null : this.getAvatarUrl().equals(other.getAvatarUrl()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCountry() == null ? other.getCountry() == null : this.getCountry().equals(other.getCountry()))
            && (this.getOnlineStatus() == null ? other.getOnlineStatus() == null : this.getOnlineStatus().equals(other.getOnlineStatus()))
            && (this.getOnlineHeadIcronId() == null ? other.getOnlineHeadIcronId() == null : this.getOnlineHeadIcronId().equals(other.getOnlineHeadIcronId()))
            && (this.getOutlineHeadIcronId() == null ? other.getOutlineHeadIcronId() == null : this.getOutlineHeadIcronId().equals(other.getOutlineHeadIcronId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getAvatarUrl() == null) ? 0 : getAvatarUrl().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCountry() == null) ? 0 : getCountry().hashCode());
        result = prime * result + ((getOnlineStatus() == null) ? 0 : getOnlineStatus().hashCode());
        result = prime * result + ((getOnlineHeadIcronId() == null) ? 0 : getOnlineHeadIcronId().hashCode());
        result = prime * result + ((getOutlineHeadIcronId() == null) ? 0 : getOutlineHeadIcronId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", openId=").append(openId);
        sb.append(", avatarUrl=").append(avatarUrl);
        sb.append(", nickName=").append(nickName);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", city=").append(city);
        sb.append(", province=").append(province);
        sb.append(", country=").append(country);
        sb.append(", onlineStatus=").append(onlineStatus);
        sb.append(", onlineHeadIcronId=").append(onlineHeadIcronId);
        sb.append(", outlineHeadIcronId=").append(outlineHeadIcronId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}