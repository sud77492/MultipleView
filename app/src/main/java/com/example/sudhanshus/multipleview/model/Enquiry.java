package com.example.sudhanshus.multipleview.model;

public class Enquiry {

    int enquiry_id;

    String Subject;

    public Enquiry(int enquiry_id, String subject) {
        this.enquiry_id = enquiry_id;
        Subject = subject;
    }

    public int getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(int enquiry_id) {
        this.enquiry_id = enquiry_id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
