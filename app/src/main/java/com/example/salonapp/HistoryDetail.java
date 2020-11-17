package com.example.salonapp;

public class HistoryDetail {
    private String historyID;
    private String historyWorkerID;
    private String historyPrice;
    private String historyService;

    public HistoryDetail(){
        this("","","","");
    }

    public HistoryDetail(String historyID, String historyWorkerID, String historyPrice, String historyService) {
        this.historyID = historyID;
        this.historyWorkerID = historyWorkerID;
        this.historyPrice = historyPrice;
        this.historyService = historyService;
    }

    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getHistoryWorkerID() {
        return historyWorkerID;
    }

    public void setHistoryWorkerID(String historyWorkerID) {
        this.historyWorkerID = historyWorkerID;
    }

    public String getHistoryPrice() {
        return historyPrice;
    }

    public void setHistoryPrice(String historyPrice) {
        this.historyPrice = historyPrice;
    }

    public String getHistoryService() {
        return historyService;
    }

    public void setHistoryService(String historyService) {
        this.historyService = historyService;
    }
}
