package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭成员表
 * @TableName h_home_member
 */
@TableName(value ="h_home_member")
@Data
public class HHomeMember implements Serializable {
    /**
     * 主键
     */
    @TableId(value ="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 新历生日
     */
    private String birthday;

    /**
     * 农历生日   一月必须为正月  区分二月和闰二月
     */
    private String oldBirthday;

    /**
     * 微信账号
     */
    private String wxOpenId;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 本年度消息是否已推送  0--未推送  1--已推送       新增时默认未推送
     */
    private Integer meaasgeIsSended;

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
        HHomeMember other = (HHomeMember) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getOldBirthday() == null ? other.getOldBirthday() == null : this.getOldBirthday().equals(other.getOldBirthday()))
            && (this.getWxOpenId() == null ? other.getWxOpenId() == null : this.getWxOpenId().equals(other.getWxOpenId()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSeq() == null ? other.getSeq() == null : this.getSeq().equals(other.getSeq()))
            && (this.getMeaasgeIsSended() == null ? other.getMeaasgeIsSended() == null : this.getMeaasgeIsSended().equals(other.getMeaasgeIsSended()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getOldBirthday() == null) ? 0 : getOldBirthday().hashCode());
        result = prime * result + ((getWxOpenId() == null) ? 0 : getWxOpenId().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSeq() == null) ? 0 : getSeq().hashCode());
        result = prime * result + ((getMeaasgeIsSended() == null) ? 0 : getMeaasgeIsSended().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", birthday=").append(birthday);
        sb.append(", oldBirthday=").append(oldBirthday);
        sb.append(", wxOpenId=").append(wxOpenId);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", status=").append(status);
        sb.append(", seq=").append(seq);
        sb.append(", meaasgeIsSended=").append(meaasgeIsSended);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}