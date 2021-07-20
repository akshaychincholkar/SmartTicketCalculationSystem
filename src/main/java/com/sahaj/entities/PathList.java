package com.sahaj.entities;

public class PathList {
    private String route;
    private int cap;
    private PathList next;
    private PathList sameZone;
    public PathList(String route,int cap){
        this.route=route;
        this.cap = cap;
    }
    public String getRoute() {
        return route;
    }
    public PathList getNext() {
        return next;
    }

    public int getCap() {
        return cap;
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
