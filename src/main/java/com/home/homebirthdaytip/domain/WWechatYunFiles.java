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
 * 文件表微信云与家用文件表
 * @TableName w_wechat_yun_files
 */
@TableName(value ="w_wechat_yun_files")
@Data
public class WWechatYunFiles implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * Linux保存路径
     */
    private String linPath;

    /**
     * Windows保存路径
     */
    private String winPath;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件类型(1、目录 2、文件)
     */
    private Integer fileType;

    /**
     * 上传人
     */
    private String uploadUser;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date uploadTime;

    /**
     * 删除状态
     */
    private Integer status;

    /**
     * 状态（1--正常或已同步  0、未同步或不正常）
     */
    private Integer synchronizeStatus;

    /**
     * 文件大小(以M为单位)
     */
    private Double fileSize;

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
        WWechatYunFiles other = (WWechatYunFiles) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLinPath() == null ? other.getLinPath() == null : this.getLinPath().equals(other.getLinPath()))
            && (this.getWinPath() == null ? other.getWinPath() == null : this.getWinPath().equals(other.getWinPath()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getUploadUser() == null ? other.getUploadUser() == null : this.getUploadUser().equals(other.getUploadUser()))
            && (this.getUploadTime() == null ? other.getUploadTime() == null : this.getUploadTime().equals(other.getUploadTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSynchronizeStatus() == null ? other.getSynchronizeStatus() == null : this.getSynchronizeStatus().equals(other.getSynchronizeStatus()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLinPath() == null) ? 0 : getLinPath().hashCode());
        result = prime * result + ((getWinPath() == null) ? 0 : getWinPath().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getUploadUser() == null) ? 0 : getUploadUser().hashCode());
        result = prime * result + ((getUploadTime() == null) ? 0 : getUploadTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSynchronizeStatus() == null) ? 0 : getSynchronizeStatus().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", linPath=").append(linPath);
        sb.append(", winPath=").append(winPath);
        sb.append(", fileSuffix=").append(fileSuffix);
        sb.append(", fileType=").append(fileType);
        sb.append(", uploadUser=").append(uploadUser);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append(", status=").append(status);
        sb.append(", synchronizeStatus=").append(synchronizeStatus);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}