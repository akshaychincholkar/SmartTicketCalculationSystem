package com.sahaj.model;

import com.sahaj.entities.PathCircularLinkedList;

public class AttributeCache {
    private int dailyCap = 0;
    private int weeklyCap = 0;
    private boolean isDailyCapReached = false;
    private PathCircularLinkedList max;
    private String firsday = "";
    private String prevPath="";
    private boolean isFirstday = true;
    private String prevDay = "";
    private int fare;
    private String dayType = "";

    public int getDailyCap() {
        return dailyCap;
    }

    public void setDailyCap(int dailyCap) {
        this.dailyCap = dailyCap;
    }

    public int getWeeklyCap() {
        return weeklyCap;
    }

    public void setWeeklyCap(int weeklyCap) {
        this.weeklyCap = weeklyCap;
    }

    public boolean isDailyCapReached() {
        return isDailyCapReached;
    }

    public void setDailyCapReached(boolean dailyCapReached) {
        isDailyCapReached = dailyCapReached;
    }

    public PathCircularLinkedList getMax() {
        return max;
    }

    public void setMax(PathCircularLinkedList max) {
        this.max = max;
    }

    public String getFirsday() {
        return firsday;
    }

    public void setFirsday(String firsday) {
        this.firsday = firsday;
    }

    public String getPrevPath() {
        return prevPath;
    }

    public void setPrevPath(String prevPath) {
        this.prevPath = prevPath;
    }

    public boolean isFirstday() {
        return isFirstday;
    }

    public void setFirstday(boolean firstday) {
        isFirstday = firstday;
    }

    public String getPrevDay() {
        return prevDay;
    }

    public void setPrevDay(String prevDay) {
        this.prevDay = prevDay;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }
}
