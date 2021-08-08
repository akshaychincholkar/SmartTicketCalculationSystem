package com.sahaj.constants;

public enum Card {
    WEEKDAY_1_1_PEAK(30),
    WEEKDAY_1_2_PEAK(35),
    WEEKDAY_2_1_PEAK(35),
    WEEKDAY_2_2_PEAK(25),
    WEEKDAY_1_1_NON_PEAK(25),
    WEEKDAY_1_2_NON_PEAK(30),
    WEEKDAY_2_1_NON_PEAK(30),
    WEEKDAY_2_2_NON_PEAK(20),
    WEEKEND_1_1_PEAK(30),
    WEEKEND_1_2_PEAK(35),
    WEEKEND_2_1_PEAK(35),
    WEEKEND_2_2_PEAK(25),
    WEEKEND_1_1_NON_PEAK(25),
    WEEKEND_1_2_NON_PEAK(30),
    WEEKEND_2_1_NON_PEAK(30),
    WEEKEND_2_2_NON_PEAK(20),
    WEEKDAY_MORNING_START_TIME("07:00"),
    WEEKDAY_MORNING_END_TIME("10:30"),
    WEEKDAY_EVENING_START_TIME("17:00"),
    WEEKDAY_EVENING_END_TIME("20:00"),
    WEEKEND_MORNING_START_TIME("09:00"),
    WEEKEND_MORNING_END_TIME("11:00"),
    WEEKEND_EVENING_START_TIME("18:00"),
    WEEKEND_EVENING_END_TIME("22:00"),
    WEEKDAY_OFF_PEAK_HOUR_START_TIME("17:00"),
    WEEKDAY_OFF_PEAK_HOUR_END_TIME("20:00"),
    WEEKEND_OFF_PEAK_HOUR_START_TIME("18:00"),
    WEEKEND_OFF_PEAK_HOUR_END_TIME("22:00");
    
    private int fare;
    private String time;

    private Card(int fare) {
        this.fare = fare;
    }
    private Card(String time) {
        this.time = time;
    }
    public int getFare() {
        return this.fare;
    }
    public String getTime() {
        return this.time;
    }
}
