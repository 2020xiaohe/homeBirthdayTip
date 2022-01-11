package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName c_common_push_account_retative_id
 */
@TableName(value ="c_common_push_account_retative_id")
@Data
public class CCommonPushAccountRetativeId implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 账号表主键--邮件
     */
    private String emailaccount;

    /**
     * 账号表主键--短信
     */
    private String messageaccount;

    @TableField(exist = false)
    private int emailSubscribeStatus;

    @TableField(exist = false)
    private int messageSubscribeStatus;

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
        CCommonPushAccountRetativeId other = (CCommonPushAccountRetativeId) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmailaccount() == null ? other.getEmailaccount() == null : this.getEmailaccount().equals(other.getEmailaccount()))
            && (this.getMessageaccount() == null ? other.getMessageaccount() == null : this.getMessageaccount().equals(other.getMessageaccount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEmailaccount() == null) ? 0 : getEmailaccount().hashCode());
        result = prime * result + ((getMessageaccount() == null) ? 0 : getMessageaccount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", emailSubscribeStatus=").append(emailSubscribeStatus);
        sb.append(", messageSubscribeStatus=").append(messageSubscribeStatus);
        sb.append(", emailaccount=").append(emailaccount);
        sb.append(", messageaccount=").append(messageaccount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}