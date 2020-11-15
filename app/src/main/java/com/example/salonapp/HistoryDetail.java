package com.example.salonapp;

public class HistoryDetail {
    private String historyID;
    private String historyPrice;

    public HistoryDetail(){
        this("","");
    }

    public HistoryDetail(String historyID, String historyPrice) {
        this.historyID = historyID;
        this.historyPrice = historyPrice;
    }

    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getHistoryPrice() {
        return historyPrice;
    }

    public void setHistoryPrice(String historyPrice) {
        this.historyPrice = historyPrice;
    }

}
