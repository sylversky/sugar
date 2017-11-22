package com.example.model;

import com.orm.annotation.IgnoreUpdate;
import com.orm.annotation.Table;
import com.orm.annotation.Unique;

import java.util.Date;
import java.util.List;

@Table
public class Person {
    private Long id;
    @Unique
    private String regId;
    private String name;
    private Date dob;
    private List<Address> address;
    @IgnoreUpdate
    private int readStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append(getId()).append(",")
                .append(getRegId()).append(",")
                .append(getName()).append(",")
                .append(getDob().toString()).append(",\n");
        stringBuffer.append("[");

        Address item;
        for(int i=0; i<address.size();i++){
            item = address.get(i);
            if(i==0){
                stringBuffer.append(item.toString());
            }else{
                stringBuffer.append(",").append(item.toString());
            }
        }
        stringBuffer.append("]\n,");

        stringBuffer.append(getReadStatus());
        stringBuffer.append("}");

        return stringBuffer.toString();
    }
}
