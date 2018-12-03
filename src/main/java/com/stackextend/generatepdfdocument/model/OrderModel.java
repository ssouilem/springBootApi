package com.stackextend.generatepdfdocument.model;

import java.util.List;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Customer;

public class OrderModel {

    private String code;
    private Customer customer;
    private List<BordereauDetail> entries;

    public OrderModel(String code, Customer address, List<BordereauDetail> entries) {
        this.code = code;
        this.customer = address;
        this.entries = entries;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer address) {
        this.customer = address;
    }

    public List<BordereauDetail> getEntries() {
        return entries;
    }

    public void setEntries(List<BordereauDetail> entries) {
        this.entries = entries;
    }
}
