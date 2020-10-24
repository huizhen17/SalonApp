package com.example.salonapp;

public class ServicesDetail {
    private String serviceName;
    private String serviceTime;
    private String servicePrice;

    public ServicesDetail(String serviceName, String serviceTime, String servicePrice) {
        this.serviceName = serviceName;
        this.serviceTime = serviceTime;
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }
}
