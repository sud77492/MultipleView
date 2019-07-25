package com.example.sudhanshus.multipleview.model;

public class Enquiry {

    int id, client_id, product_id, user_id;

    String product_title, order_no, name, email, phone, address, city, detail, active, ext_data, chk_mail, created, modified;

    public Enquiry(int id, int client_id, int product_id, int user_id, String product_title, String order_no, String name, String email, String phone, String address, String city, String detail, String active, String ext_data, String chk_mail, String created, String modified) {
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.product_title = product_title;
        this.order_no = order_no;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.detail = detail;
        this.active = active;
        this.ext_data = ext_data;
        this.chk_mail = chk_mail;
        this.created = created;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getExt_data() {
        return ext_data;
    }

    public void setExt_data(String ext_data) {
        this.ext_data = ext_data;
    }

    public String getChk_mail() {
        return chk_mail;
    }

    public void setChk_mail(String chk_mail) {
        this.chk_mail = chk_mail;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
