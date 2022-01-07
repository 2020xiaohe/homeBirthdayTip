package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信云文件记录
 * @TableName w_wechat_yun_file_logs
 */
@TableName(value ="w_wechat_yun_file_logs")
@Data
public class WWechatYunFileLogs implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 上传人
     */
    private String operaUser;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operaTime;

    /**
     * 内容(1.xxx在xxxx-xx-xx xx:xx:xx上传xx个文件,其中成功x个,失败x个2.xxx在xxxx-xx-xx xx:xx:xx删除了xx文件)
     */
    private String content;

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
        WWechatYunFileLogs other = (WWechatYunFileLogs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOperaUser() == null ? other.getOperaUser() == null : this.getOperaUser().equals(other.getOperaUser()))
            && (this.getOperaTime() == null ? other.getOperaTime() == null : this.getOperaTime().equals(other.getOperaTime()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperaUser() == null) ? 0 : getOperaUser().hashCode());
        result = prime * result + ((getOperaTime() == null) ? 0 : getOperaTime().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operaUser=").append(operaUser);
        sb.append(", operaTime=").append(operaTime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}