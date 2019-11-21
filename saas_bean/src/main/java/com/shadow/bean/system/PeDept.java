package com.shadow.bean.system;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author shadow
 */
public class PeDept implements Serializable {
    private String deptId;
    private String deptName;
    private java.math.BigDecimal state;
    private String companyId;
    private String companyName;
    private PeDept parent;

    public PeDept() {
    }

    public PeDept getParent() {
        return parent;
    }

    public void setParent(PeDept parent) {
        this.parent = parent;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return this.deptName;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", PeDept.class.getSimpleName() + "[", "]")
                .add("deptId='" + deptId + "'")
                .add("deptName='" + deptName + "'")
                .add("state=" + state)
                .add("companyId='" + companyId + "'")
                .add("companyName='" + companyName + "'")
                .add("parent=" + parent)
                .toString();
    }
}