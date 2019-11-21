package com.shadow.bean.system;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author shadow
 */
public class PeUser implements Serializable {
    private String userId;
    private String deptId;
    private String email;
    private String userName;
    private String station;
    private String password;
    private java.math.BigDecimal state;
    private String companyId;
    private String companyName;
    private String deptName;
    private String managerId;
    private String gender;
    private String telephone;
    private String birthday;
    /**
     * 0作为内部控制，租户企业不能使用
     * 0-saas管理员
     * 1-企业管理员
     * 2-管理所有下属部门和人员
     * 3-管理本部门
     * 4-普通员工
     */
    private Integer degree;
    private java.math.BigDecimal salary;
    private String joinDate;
    private Integer orderNo;
    private String createBy;
    private String createDempt;
    private Timestamp createTime;
    private String updateBy;
    private Timestamp updateTime;
    private String remark;

    public PeUser() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStation() {
        return this.station;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setState(java.math.BigDecimal state) {
        this.state = state;
    }

    public java.math.BigDecimal getState() {
        return this.state;
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

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerId() {
        return this.managerId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getDegree() {
        return this.degree;
    }

    public void setSalary(java.math.BigDecimal salary) {
        this.salary = salary;
    }

    public java.math.BigDecimal getSalary() {
        return this.salary;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getJoinDate() {
        return this.joinDate;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateDempt(String createDempt) {
        this.createDempt = createDempt;
    }

    public String getCreateDempt() {
        return this.createDempt;
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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    @Override
    public String toString() {
        return "PeUser{" +
                "userId='" + userId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", station='" + station + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", managerId='" + managerId + '\'' +
                ", gender='" + gender + '\'' +
                ", telephone='" + telephone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", degree=" + degree +
                ", salary=" + salary +
                ", joinDate='" + joinDate + '\'' +
                ", orderNo=" + orderNo +
                ", createBy='" + createBy + '\'' +
                ", createDempt='" + createDempt + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}