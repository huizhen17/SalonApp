package com.example.salonapp;

public class UserAddress {
    private String houseNo;
    private String houseBlock;
    private String houseLevel;
    private String houseBuilding;
    private String houseStreet;
    private String houseCity;
    private String housePostcode;
    private String houseState;

    public UserAddress(String houseNo, String houseBlock, String houseLevel, String houseBuilding, String houseStreet, String houseCity, String housePostcode, String houseState) {
        this.houseNo = houseNo;
        this.houseBlock = houseBlock;
        this.houseLevel = houseLevel;
        this.houseBuilding = houseBuilding;
        this.houseStreet = houseStreet;
        this.houseCity = houseCity;
        this.housePostcode = housePostcode;
        this.houseState = houseState;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getHouseBlock() {
        return houseBlock;
    }

    public void setHouseBlock(String houseBlock) {
        this.houseBlock = houseBlock;
    }

    public String getHouseLevel() {
        return houseLevel;
    }

    public void setHouseLevel(String houseLevel) {
        this.houseLevel = houseLevel;
    }

    public String getHouseBuilding() {
        return houseBuilding;
    }

    public void setHouseBuilding(String houseBuilding) {
        this.houseBuilding = houseBuilding;
    }

    public String getHouseStreet() {
        return houseStreet;
    }

    public void setHouseStreet(String houseStreet) {
        this.houseStreet = houseStreet;
    }

    public String getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(String houseCity) {
        this.houseCity = houseCity;
    }

    public String getHousePostcode() {
        return housePostcode;
    }

    public void setHousePostcode(String housePostcode) {
        this.housePostcode = housePostcode;
    }

    public String getHouseState() {
        return houseState;
    }

    public void setHouseState(String houseState) {
        this.houseState = houseState;
    }

    @Override
    public String toString() {
        return  houseNo + ", " + houseBlock + "-" + houseLevel + ", " + houseBuilding +
                ", " + houseStreet + ", " + houseCity + '\'' + ", " + housePostcode  +
                ", " + houseState ;
    }
}
