package com.sahaj.model;
//TODO: datatype change DATe if not complex

public class InputBean {
    private String day;
    private String time;
    private String source;
    private String destination;
    private boolean isNextPresent;
    //TODO: previous day null initialization
    private String prevPath = "";
    private String prevDay = "";

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isNextPresent() {
        return isNextPresent;
    }

    public void setNextPresent(boolean nextPresent) {
        isNextPresent = nextPresent;
    }

    public String getPrevPath() {
        return prevPath;
    }

    public void setPrevPath(String prevPath) {
        this.prevPath = prevPath;
    }

    public String getPrevDay() {
        return prevDay;
    }

    public void setPrevDay(String prevDay) {
        this.prevDay = prevDay;
    }

}
