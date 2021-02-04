package com.example.nkinvoicing;

import java.io.Serializable;

public class Contacts implements Serializable {

    String userCompany ;
    String userAddress;
    String userPostcode;
    String userTel;
    String userCompanyID;
    String userEmail;
    //--------------------------------------------------------------------------
    String receiverCompany;
    String receiverAddress;
    String receiverPostcode;
    String receiverTel;
    String receiverCompanyID;
    String receiverEmail;

    public Contacts(String userCompany,
                    String userAddress,
                    String userPostcode,
                    String userTel,
                    String userCompanyID,
                    String userEmail,
                    String receiverCompany,
                    String receiverAddress,
                    String receiverPostcode,
                    String receiverTel,
                    String receiverCompanyID,
                    String receiverEmail) {
        this.userCompany = userCompany;
        this.userAddress = userAddress;
        this.userPostcode = userPostcode;
        this.userTel = userTel;
        this.userCompanyID = userCompanyID;
        this.userEmail = userEmail;
        this.receiverCompany = receiverCompany;
        this.receiverAddress = receiverAddress;
        this.receiverPostcode = receiverPostcode;
        this.receiverTel = receiverTel;
        this.receiverCompanyID = receiverCompanyID;
        this.receiverEmail = receiverEmail;
    }
    public Contacts(){}

    @Override
    public String toString() {
        return "Contacts{" +
                "userCompany='" + userCompany + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userPostcode='" + userPostcode + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userCompanyID='" + userCompanyID + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", receiverCompany='" + receiverCompany + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverPostcode='" + receiverPostcode + '\'' +
                ", receiverTel='" + receiverTel + '\'' +
                ", receiverCompanyID='" + receiverCompanyID + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                '}';
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPostcode() {
        return userPostcode;
    }

    public void setUserPostcode(String userPostcode) {
        this.userPostcode = userPostcode;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserCompanyID() {
        return userCompanyID;
    }

    public void setUserCompanyID(String userCompanyID) {
        this.userCompanyID = userCompanyID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPostcode() {
        return receiverPostcode;
    }

    public void setReceiverPostcode(String receiverPostcode) {
        this.receiverPostcode = receiverPostcode;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverCompanyID() {
        return receiverCompanyID;
    }

    public void setReceiverCompanyID(String receiverCompanyID) {
        this.receiverCompanyID = receiverCompanyID;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
