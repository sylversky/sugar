package com.example.model;

import com.orm.annotation.Table;

@Table
public class Address {
    private Long id;
    private String street;
    private String province;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append(getId()).append(",")
                .append(getStreet()).append(",")
                .append(getProvince()).append("}");

        return stringBuffer.toString();
    }
}
