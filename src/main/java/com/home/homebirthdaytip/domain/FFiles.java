package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件表
 * @TableName f_files
 */
@TableName(value ="f_files")
@Data
public class FFiles implements Serializable {
    /**
     * 主键 应用端放开IdType.AUTO,备份端注释
     */
    @TableId(type = IdType.AUTO)
//    @TableId
    private Integer id;

    /**
     * Linux保存路径
     */
    private String linPath;

    /**
     * Windows保存路径
     */
    private String winPath;

    /**
     * 文件类型(1、目录 2、文件)
     */
    private Integer fileType;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 状态（1--正常或已同步  0、未同步或不正常）
     */
    private Integer status;

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
        FFiles other = (FFiles) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLinPath() == null ? other.getLinPath() == null : this.getLinPath().equals(other.getLinPath()))
            && (this.getWinPath() == null ? other.getWinPath() == null : this.getWinPath().equals(other.getWinPath()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLinPath() == null) ? 0 : getLinPath().hashCode());
        result = prime * result + ((getWinPath() == null) ? 0 : getWinPath().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", fileType=").append(fileType);
        sb.append(", fileSuffix=").append(fileSuffix);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}