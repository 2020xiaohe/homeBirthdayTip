package com.home.homebirthdaytip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 定时任务表
 * @TableName j_jobs
 */
@TableName(value ="j_jobs")
@Data
public class JJobs implements Serializable {
    /**
     * 主键
     */
    @TableId(value ="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 定时任务执行时间   当未达到执行时间表示关闭，当达到执行时间表示开启
     */
    private String cron;

    /**
     * 定时任务类型，区分不同定时任务
     */
    private Integer cronType;

    /**
     * 定时任务类型描述
     */
    private String cronTypeDescribe;

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
        JJobs other = (JJobs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCron() == null ? other.getCron() == null : this.getCron().equals(other.getCron()))
            && (this.getCronType() == null ? other.getCronType() == null : this.getCronType().equals(other.getCronType()))
            && (this.getCronTypeDescribe() == null ? other.getCronTypeDescribe() == null : this.getCronTypeDescribe().equals(other.getCronTypeDescribe()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCron() == null) ? 0 : getCron().hashCode());
        result = prime * result + ((getCronType() == null) ? 0 : getCronType().hashCode());
        result = prime * result + ((getCronTypeDescribe() == null) ? 0 : getCronTypeDescribe().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cron=").append(cron);
        sb.append(", cronType=").append(cronType);
        sb.append(", cronTypeDescribe=").append(cronTypeDescribe);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}