package com.sahaj.entities;

import com.sahaj.constants.TicketConstants;

import java.util.LinkedList;

public class PathCircularLinkedList {
    private String route;
    public int dailyCap;
    public int weeklyCap;
    private PathCircularLinkedList next;
    private PathCircularLinkedList sameZone;
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
    public PathCircularLinkedList(String route, int dailyCap, int String){
        this.route=route;
        this.dailyCap = dailyCap;
    }

    public PathCircularLinkedList() {
        dailyCap =0;
        weeklyCap=0;
    }

    public String getRoute() {
        return route;
    }
    public PathCircularLinkedList getNext() {
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

    public PathCircularLinkedList getSameZone() {
        return sameZone;
    }

    public void setSameZone(PathCircularLinkedList sameZone) {
        this.sameZone = sameZone;
    }

    public void setNext(PathCircularLinkedList next) {
        this.next = next;
    }
}
