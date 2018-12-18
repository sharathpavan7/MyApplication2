package com.example.user.myapplication;

public class Contact {

    private String contactName, contactMobile, contactImage;


    public Contact(String name, String mobile, String image) {
        contactName = name;
        contactMobile = mobile;
        contactImage = image;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactImage() {
        return contactImage;
    }
}
