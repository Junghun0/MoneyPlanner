package com.example.parkjunghun.moneyplanner.Activity.Model;

public class DetailMoneyInfo {

    private String key;
    private String type;
    private String selectDate;
    private int usingMoney;
    private String imgUrl;

    public DetailMoneyInfo(){}

    public DetailMoneyInfo(String key, String type, String selectDate, int usingMoney) {
        this.key = key;
        this.type = type;
        this.selectDate = selectDate;
        this.usingMoney = usingMoney;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    public int getUsingMoney() {
        return usingMoney;
    }

    public void setUsingMoney(int usingMoney) {
        this.usingMoney = usingMoney;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
