package com.sahaj.entities;

import com.sahaj.constants.TicketConstants;

import java.util.LinkedList;

public class PathList {
    private String route;
    public int dailyCap;
    public int weeklyCap;
    private PathList next;
    private PathList sameZone;
    public  LinkedList<String> currentDay = new LinkedList<>();
    {
        currentDay.add(TicketConstants.MONDAY);
        currentDay.add(TicketConstants.TUESDAY);
        currentDay.add(TicketConstants.WEDNESDAY);
        currentDay.add(TicketConstants.THURSDAY);
        currentDay.add(TicketConstants.FRIDAY);
        currentDay.add(TicketConstants.SATURDAY);
        currentDay.add(TicketConstants.SUNDAY);

    }
    public PathList(String route,int dailyCap,int String){
        this.route=route;
        this.dailyCap = dailyCap;
    }

    public PathList() {
        dailyCap =0;
        weeklyCap=0;
    }

    public String getRoute() {
        return route;
    }
    public PathList getNext() {
        return next;
    }

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

    public PathList getSameZone() {
        return sameZone;
    }

    public void setSameZone(PathList sameZone) {
        this.sameZone = sameZone;
    }

    public void setNext(PathList next) {
        this.next = next;
    }
}
