package com.shadow.bean.system;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author shadow 
 */
public class SsModule implements Serializable{
    private String moduleId;
    private String parentId;
    private String parentName;
    private String name;
    private java.math.BigDecimal layerNum;
    private java.math.BigDecimal isLeaf;
    private String ico;
    private String cpermission;
    private String curl;
    private java.math.BigDecimal ctype;
    private java.math.BigDecimal state;
    private String belong;
    private String cwhich;
    private java.math.BigDecimal quoteNum;
    private String remark;
    private java.math.BigDecimal orderNo;
	public SsModule(){}
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLayerNum(java.math.BigDecimal layerNum) {
        this.layerNum = layerNum;
    }

    public java.math.BigDecimal getLayerNum() {
        return this.layerNum;
    }

    public void setIsLeaf(java.math.BigDecimal isLeaf) {
        this.isLeaf = isLeaf;
    }

    public java.math.BigDecimal getIsLeaf() {
        return this.isLeaf;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getIco() {
        return this.ico;
    }

    public void setCpermission(String cpermission) {
        this.cpermission = cpermission;
    }

    public String getCpermission() {
        return this.cpermission;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public String getCurl() {
        return this.curl;
    }

    public void setCtype(java.math.BigDecimal ctype) {
        this.ctype = ctype;
    }

    public java.math.BigDecimal getCtype() {
        return this.ctype;
    }

    public void setState(java.math.BigDecimal state) {
        this.state = state;
    }

    public java.math.BigDecimal getState() {
        return this.state;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getBelong() {
        return this.belong;
    }

    public void setCwhich(String cwhich) {
        this.cwhich = cwhich;
    }

    public String getCwhich() {
        return this.cwhich;
    }

    public void setQuoteNum(java.math.BigDecimal quoteNum) {
        this.quoteNum = quoteNum;
    }

    public java.math.BigDecimal getQuoteNum() {
        return this.quoteNum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SsModule)) return false;
        SsModule module = (SsModule) o;
        return Objects.equals(moduleId, module.moduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleId);
    }

}