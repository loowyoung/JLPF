package com.anxin.common.domain;

import com.anxin.common.utils.ShiroUtils;
import com.anxin.common.utils.StringUtils;
import com.anxin.system.domain.UserDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author sun
 * @data 2018/10/15
 * @Description 实体公共父类
 */

public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id（uuid）
     */
    public String id;
    /**
     * 备注
     */
//    @NotBlank(message = "备注信息不能为空")
    public String remarks;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createDate;
    /**
     * 创建人
     */
//    @NotBlank(message = "创建人不能为空")
    public String createBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateDate;
    /**
     * 更新人
     */
//    @NotBlank(message = "更新者不能为空")
    public String updateBy;

    public BaseEntity() {

    }

    public UserDO getUser() {
        return ShiroUtils.getUser();
    }

    /**
     * insert前执行，需要手动调用
     */
    public void preInsert(){
        //设置id为uuid
        if (StringUtils.isBlank(this.id)){
            setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }

        if (getUser()!=null) {
            if (StringUtils.isNotBlank(getUser().getUserId().toString())){
                this.createBy = getUser().getUsername();
                this.updateBy = getUser().getUsername();
            }
        }
        this.updateDate = new Date();
        this.createDate = this.updateDate;
    }

    /**
     * update前执行，需要手动调用
     */
    public void preUpdate(){
        if (getUser()!=null) {
            if (StringUtils.isNotBlank(getUser().getUserId().toString())) {
                this.updateBy = getUser().getUsername();
            }
        }
        this.updateDate = new Date();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
            this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
