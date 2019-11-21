package com.shadow.bean.company;
import  java.sql.Timestamp;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author shadow 
 */
public class SsCompany implements Serializable{
    private String id;
    private String name;
    private Timestamp expirationDate;
    private String address;
    private String licenseId;
    private String representative;
    private String phone;
    private String companySize;
    private String industry;
    private String remarks;
    private Integer state;
    private Double balance;
    private String city;
	public SsCompany(){}
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Timestamp getExpirationDate() {
        return this.expirationDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseId() {
        return this.licenseId;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getRepresentative() {
        return this.representative;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanySize() {
        return this.companySize;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SsCompany.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("expirationDate=" + expirationDate)
                .add("address='" + address + "'")
                .add("licenseId='" + licenseId + "'")
                .add("representative='" + representative + "'")
                .add("phone='" + phone + "'")
                .add("companySize='" + companySize + "'")
                .add("industry='" + industry + "'")
                .add("remarks='" + remarks + "'")
                .add("state=" + state)
                .add("balance=" + balance)
                .add("city='" + city + "'")
                .toString();
    }
}