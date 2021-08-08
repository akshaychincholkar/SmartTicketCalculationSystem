package com.sahaj.service;

import com.sahaj.constants.Card;
import com.sahaj.constants.TicketConstants;
import com.sahaj.entities.CurrentDay;
import com.sahaj.entities.PathList;
import com.sahaj.model.InputBean;
import com.sahaj.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Travel implements ITravel{
    public int dailyCap = 0;
    public int weeklyCap = 0;
    public boolean isDailyCapReached = false;
    public PathList max;
//    public CurrentDay head;
    public int maxCap =0;
    public String firsday = "";
    public String prevPath="";
    public boolean isFirstday = true;
    public String prevDay = "";
    public int fare;

    public Travel() {
        max  = new PathList();
    }

    @Override
    public int calculateFare(InputBean inputBean) {
        if(!prevDay.equalsIgnoreCase(inputBean.getDay()))
            prevPath=resetState(prevPath,false, inputBean.getDay());
        System.out.print("Day: "+ inputBean.getDay()+" Source: "+ inputBean.getSource()+" Destination:"+ inputBean.getDestination()+" Travel time:"+ inputBean.getTime()+" ");
        int currentDailyCap = getDailyCap(prevPath, inputBean.getSource(), inputBean.getDestination());
        int currentWeeklyCap = getWeeklyCap(inputBean.getSource(), inputBean.getDestination());
        if(max.weeklyCap<currentWeeklyCap)max.weeklyCap=currentWeeklyCap;
        if(currentDailyCap!=-1){
            if(max.dailyCap <currentDailyCap)max.dailyCap =currentDailyCap;
        }else{
            System.out.println("invalid path chosen#####");
            return -1;
        }

        //Testing code
        String dayType = "";
        if(!isWeekend(inputBean.getDay())){
            dayType = "WEEKDAY";
        }else{
            dayType="WEEKEND";
        }
//        if()
        String morning_start = dayType+"_MORNING_START_TIME";
        String morning_end = dayType+"_MORNING_END_TIME";
        String evening_start = dayType+"_EVENING_START_TIME";
        String evening_end = dayType+"_EVENING_END_TIME";
        String peak="";
        int fare2=0;
        String fareString = null;
        int prevDailyCap = dailyCap;
        int prevWeeklyCap= weeklyCap;
        if(!inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1)
                && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
            String zonal_condition = "ZONE_2_1_"+dayType+"_OFF_PEAK_HOUR_START_TIME";
            if(!isPeakHour(Card.valueOf(zonal_condition).getTime(),TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_END_TIME, inputBean.getTime())){
                /**
                 * TODO: No sysouts
                 * WEEKEND_1_2_NON_PEAK
                 */
                System.out.println("Source zone: "+ inputBean.getSource()+" Destination zone:"+ inputBean.getDestination()+" Travel time:"+ inputBean.getTime()+"Fare Category: Peak weekday");
                fare2=TicketConstants.PEAK_HOUR_FARE_1_2;
            }else{
                System.out.println("Fare Category: Non Peak weekday");
                fare2=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
            }
            System.out.println("INFO:"+zonal_condition+" Value:"+ fare2);
        }else{
            if(isPeakHour(Card.valueOf(morning_start).getTime(),Card.valueOf(morning_end).getTime(), inputBean.getTime())||
                    isPeakHour(Card.valueOf(evening_start).getTime(),Card.valueOf(evening_end).getTime(), inputBean.getTime())){
                peak = "PEAK";
            }else{
                peak="NON_PEAK";
            }

            fareString = dayType+"_"+inputBean.getSource()+"_"+inputBean.getDestination()+"_"+peak;
            fare2 = Card.valueOf(fareString).getFare();
        }
        dailyCap+=fare2;
        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
            fare2=max.dailyCap +fare2-dailyCap;if(fare2<0)fare2=0;
            System.out.println("INFO: Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare2);
            dailyCap=max.dailyCap;
            isDailyCapReached = true;
        }
        weeklyCap+=fare2;
        if(weeklyCap>max.weeklyCap){
            dailyCap-=fare2;
            fare2=max.weeklyCap+fare2-weeklyCap;
            dailyCap+=fare2;
            if(fare2<0)fare2=0;
            System.out.println("INFO: Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare2);
            weeklyCap=max.weeklyCap;
        }

        System.out.println("INFO:"+fareString+" Value:"+ fare2);
        System.out.println("INFO: DAILY_CAP: "+dailyCap);
        System.out.println("INFO: WEEKLY CAP: "+weeklyCap);
//        dailyCap=prevDailyCap;
//        weeklyCap=prevWeeklyCap;
        return fare2;
        //*************************

 /*       if(!isWeekend(inputBean.getDay())){
            //incoming to zone 1

            if(!inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1)
                    && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                //is Peak hour
                *//**
                 * TODO: Calculate it from POJO
                 *//*
                *//**
                 * TODO: Check solid principles
                 *//*
                *//**
                 * TODO: remove negation from if condition
                 *//*
                if(!isPeakHour(TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_START_TIME,TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_END_TIME, inputBean.getTime())){
                    *//**
                     * TODO: No sysouts
                     *//*
                    System.out.println("Source zone: "+ inputBean.getSource()+" Destination zone:"+ inputBean.getDestination()+" Travel time:"+ inputBean.getTime()+"Fare Category: Peak weekday");
                    fare=TicketConstants.PEAK_HOUR_FARE_1_2;
                }else{
                    System.out.println("Fare Category: Non Peak weekday");
                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                }
                *//**
                 * TODO: call from signature for daily cap and weekly cap
                 *
                 *//*
                dailyCap+=fare;
                if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                    fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                    System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                    dailyCap=max.dailyCap;
                    isDailyCapReached = true;
                }
//                weeklyCap+=fare;


                *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*
                weeklyCap+=fare;
                if(weeklyCap>max.weeklyCap){
                    dailyCap-=fare;
                    fare=max.weeklyCap+fare-weeklyCap;
                    dailyCap+=fare;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }System.out.println("Daily cap:"+dailyCap);
                return fare;
            }else{
                //Non incoming to zone 1
                if(isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_END_TIME, inputBean.getTime())||
                        isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_END_TIME, inputBean.getTime())){
                    System.out.println("Fare Category: Peak weekday");
                    if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) &&
                            inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_1;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_2) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_2_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else{
                        System.out.println("Invalid source / destination");
                        *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;return fare;
                    }
                }else {
                    System.out.println("Fare Category: Non Peak weekday");
//                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                    if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_1;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_2) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_2_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily Cap reached:"+dailyCap);

                    }else{
                        System.out.println("Invalid source / destination");
                        *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;return fare;
                    }
                }

                *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;
                if(weeklyCap>max.weeklyCap){
                    dailyCap-=fare;
                    fare=max.weeklyCap+fare-weeklyCap;
                    dailyCap+=fare;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }System.out.println("Daily cap:"+dailyCap);
                return fare;
            }
        }else{
            //incoming to zone 1
            if(!inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1)
                    && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                //is Peak hour
                if(!isPeakHour(TicketConstants.ZONE_2_1_WEEKEND_OFF_PEAK_HOUR_START_TIME,TicketConstants.ZONE_2_1_WEEKEND_OFF_PEAK_HOUR_END_TIME, inputBean.getTime())){
                    System.out.println("Source zone: "+ inputBean.getSource()+" Destination zone:"+ inputBean.getDestination()+" Travel time:"+ inputBean.getTime()+"Fare Category: Peak WEEKEND");
                    fare=TicketConstants.PEAK_HOUR_FARE_1_2;
                }else{
                    System.out.println("Fare Category: Non Peak WEEKEND");
                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                }

                dailyCap+=fare;
                if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                    fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                    System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                    dailyCap=max.dailyCap;
                    isDailyCapReached = true;
                }
//                weeklyCap+=fare;


                *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*
                weeklyCap+=fare;
                if(weeklyCap>max.weeklyCap){
                    dailyCap-=fare;
                    fare=max.weeklyCap+fare-weeklyCap;
                    dailyCap+=fare;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }System.out.println("Daily cap:"+dailyCap);
                return fare;
            }else{
                //Non incoming to zone 1
                if(isPeakHour(TicketConstants.WEEKEND_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKEND_PEAK_HOUR_MORNING_END_TIME, inputBean.getTime())||
                        isPeakHour(TicketConstants.WEEKEND_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKEND_PEAK_HOUR_EVENING_END_TIME, inputBean.getTime())){
                    System.out.println("Fare Category: Peak WEEKEND");
                    if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_1;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_2) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_2_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else{
                        System.out.println("Invalid source / destination");
                        *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;return fare;
                    }
                }else {
                    System.out.println("Fare Category: Non Peak WEEKEND");
//                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                    if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_1;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_2) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_2_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily cap:"+dailyCap);
                    }else if(inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1) && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                            fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=max.dailyCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("Daily Cap reached:"+dailyCap);

                    }else{
                        System.out.println("Invalid source / destination");
                        *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;return fare;
                    }
                }

                *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;
                if(weeklyCap>max.weeklyCap){
                    dailyCap-=fare;
                    fare=max.weeklyCap+fare-weeklyCap;
                    dailyCap+=fare;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }System.out.println("Daily cap:"+dailyCap);
                return fare;
            }
        }*/
    }

    @Override
    public int calculateZonalFare() {
        /** weekday:
         *  condition 1: other zone to zone 1
         *
         *  is Peak -> 1-2,2-2,1-1 method()
         *  calculatePeakFare()
         *  off peak -> 1-2,2-2,1-1 method()
         *  calculateOffPeakFare()
         *
         *  weekend:
         *  condition 1: other zone to zone 1
         *  is Peak -> 1-2,2-2,1-1
         *  off peak -> 1-2,2-2,1-1
         *
         *
         */
        return 0;
    }

    public String resetState(String prevPath,boolean isWeekCompleted,String day) {
        dailyCap=0;
        max.dailyCap =0;
        prevPath="";
        if(isWeekCompleted){
//            weeklyCap=0;
            max = new PathList();
        }
        else{
            while (!Util.head.day.equalsIgnoreCase(day)){
                Util.head=Util.head.next;
            }
        }
        return prevPath;
    }
    private int getWeeklyCap(String source,String destination){return Util.weeklyCappingLimits.get(source+"-"+destination);}
    private int getDailyCap(String prevDestination, String source, String destination) {
        if(!prevDestination.equalsIgnoreCase("")&&!prevDestination.equalsIgnoreCase(source))return -1;
        return Util.dailyCappingLimits.get(source+"-"+destination);
    }

    public boolean isWeekend(String day){
        boolean isWeekend=false;
        if(day.equalsIgnoreCase(TicketConstants.SATURDAY) || day.equalsIgnoreCase(TicketConstants.SUNDAY))isWeekend=true;
        return isWeekend;
    }
    public boolean isPeakHour(String timeString1,String timeString2,String userBuyTime ){
        boolean isValid = false;
        try {
            Date time1 = new SimpleDateFormat("HH:mm").parse(timeString1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);
            Date time2 = new SimpleDateFormat("HH:mm").parse(timeString2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);
            Date d = new SimpleDateFormat("HH:mm").parse(userBuyTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);
            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                isValid = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
