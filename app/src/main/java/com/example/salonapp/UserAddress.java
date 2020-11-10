package com.example.salonapp;

import java.io.Serializable;

public class UserAddress implements Serializable {
    private String houseNo;
    private String houseBlock;
    private String houseLevel;
    private String houseBuilding;
    private String houseGarden;
    private String houseStreet;
    private String houseCity;
    private String housePostcode;
    private String houseState;

    public UserAddress() {
        this("","","","","","","","","");
    }

    public UserAddress(String houseNo, String houseBlock, String houseLevel, String houseBuilding, String houseGarden, String houseStreet, String houseCity, String housePostcode, String houseState) {
        this.houseNo = houseNo;
        this.houseBlock = houseBlock;
        this.houseLevel = houseLevel;
        this.houseBuilding = houseBuilding;
        this.houseGarden = houseGarden;
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

    public String getHouseGarden() {
        return houseGarden;
    }

    public void setHouseGarden(String houseGarden) {
        this.houseGarden = houseGarden;
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

    public String generateAddress() {
        if(getHouseBlock().equals("")){
            return  getHouseNo() + ", " + getHouseStreet() + ", " + getHouseGarden() + ", "+ getHousePostcode() + ", " + getHouseCity()  +
                    ", " + getHouseState() + ", Malaysia";
        }
        else {
            return  getHouseNo() + ", " + getHouseBlock() + "-" + getHouseLevel() + ", " + getHouseBuilding() +
                    ", " + getHouseStreet() + ", " + getHouseGarden() + ", "+ getHousePostcode() + ", " + getHouseCity()  +
                    ", " + getHouseState() + ", Malaysia123";
        }
    }
}
