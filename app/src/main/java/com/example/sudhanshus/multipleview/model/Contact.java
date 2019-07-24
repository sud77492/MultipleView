package com.example.sudhanshus.multipleview.model;

public class Contact {

    int id, client_id, user_id;
    String name, email, phone, subject, detail, st_detail, active, created, modified;

    public Contact(int id, int client_id, int user_id, String name, String email, String phone, String subject, String detail, String st_detail, String active, String created, String modified) {
        this.id = id;
        this.client_id = client_id;
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.detail = detail;
        this.st_detail = st_detail;
        this.active = active;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSt_detail() {
        return st_detail;
    }

    public void setSt_detail(String st_detail) {
        this.st_detail = st_detail;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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
