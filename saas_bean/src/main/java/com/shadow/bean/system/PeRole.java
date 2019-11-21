package com.shadow.bean.system;
import  java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author shadow 
 */
public class PeRole implements Serializable{
    private String roleId;
    private String name;
    private String remark;
    private java.math.BigDecimal orderNo;
    private String createBy;
    private String createDept;
    private Timestamp createTime;
    private String updateBy;
    private Timestamp updateTime;
    private String companyId;
    private String companyName;
	public PeRole(){}
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setOrderNo(java.math.BigDecimal orderNo) {
        this.orderNo = orderNo;
    }

    public java.math.BigDecimal getOrderNo() {
        return this.orderNo;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public String getCreateDept() {
        return this.createDept;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

}