package com.sahaj.entities;

public class CurrentDay {
    public String day;
    public CurrentDay next;

    public CurrentDay(String day) {
        this.day = day;
    }

    public CurrentDay(String day, CurrentDay next) {
        this.day = day;
        this.next = next;
    }
}
