package com.example.parkjunghun.moneyplanner.Activity.Model;

public class ScheduleViewItem {

    int image;
    String name;
    String money;
    int change_image;

    public ScheduleViewItem(int image,String name,String money, int change_image){
        this.image = image;
        this.name = name;
        this.money = money;
        this.change_image = change_image;
    }

    public void setChange_image(int change_image) {
        this.change_image = change_image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setName(String name) { this.name = name; }

    public int getChange_image() {
        return change_image;
    }

    public int getImage() {
        return image;
    }

    public String getMoney() {
        return money;
    }
    public String getName() {
        return name;
    }
}
