package com.sahaj.service;

import com.sahaj.constants.Card;
import com.sahaj.constants.TicketConstants;
import com.sahaj.entities.PathList;
import com.sahaj.model.InputBean;
import com.sahaj.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Travel implements ITravel{
    public int dailyCap = 0;
    public int weeklyCap = 0;
    public boolean isDailyCapReached = false;
    public PathList max;
    public String firsday = "";
    public String prevPath="";
    public boolean isFirstday = true;
    public String prevDay = "";
    public int fare;
    String dayType = "";

    public Travel() {
        max  = new PathList();
    }

    @Override
    public int calculateFare(InputBean inputBean) {
        //Resetting the daily cap and required attributes for new day
        if(!prevDay.equalsIgnoreCase(inputBean.getDay()))
            prevPath=resetState(prevPath,false, inputBean.getDay());
        if(!setMaximumApplicableCapss(prevDay,inputBean))return -1;
        if(!isWeekend(inputBean.getDay()))dayType = TicketConstants.WEEKDAY;
        else dayType=TicketConstants.WEEKEND;
        if(!inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1)
                && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
            fare = calculateInterZonalFare(inputBean);
        }else{
            fare = calculateGenericFare(inputBean);
        }
        dailyCap+=fare;
        if(dailyCap>max.dailyCap){
            processDailyCap();
        }
        weeklyCap+=fare;
        if(weeklyCap>max.weeklyCap){
            processWeeklyCap();
        }
//        System.out.println("INFO: FARE:"+ fare);
//        System.out.println("INFO: DAILY_CAP: "+dailyCap);
//        System.out.println("INFO: WEEKLY CAP: "+weeklyCap);
        return fare;
    }

    private int calculateInterZonalFare(InputBean inputBean) {
        String zonal_start_time = dayType+TicketConstants.OFF_PEAK_HOUR_START_TIME;
        String zonal_end_time = dayType+TicketConstants.OFF_PEAK_HOUR_END_TIME;
        if(!isPeakHour(Card.valueOf(zonal_start_time).getTime(),Card.valueOf(zonal_end_time).getTime(), inputBean.getTime())){
            fare=Card.valueOf(dayType+"_"+inputBean.getSource()+"_"+TicketConstants.ZONE_1+"_"+TicketConstants.PEAK).getFare();
        }else{
            fare=Card.valueOf(dayType+"_"+inputBean.getSource()+"_"+TicketConstants.ZONE_1+"_"+TicketConstants.NON_PEAK).getFare();
        }
        return fare;
    }

    private void processWeeklyCap() {
        dailyCap-=fare;
        fare=max.weeklyCap+fare-weeklyCap;
        dailyCap+=fare;
        if(fare<0)fare=0;
        System.out.println("INFO: Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
        weeklyCap=max.weeklyCap;
    }

    private void processDailyCap() {
        fare=max.dailyCap +fare-dailyCap;
        if(fare<0)fare=0;
        System.out.println("INFO: Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
        dailyCap=max.dailyCap;
        isDailyCapReached = true;
    }

    private int calculateGenericFare(InputBean inputBean) {
        String morning_start = dayType+TicketConstants.MORNING_START_TIME;
        String morning_end = dayType+TicketConstants.MORNING_END_TIME;
        String evening_start = dayType+TicketConstants.EVENING_START_TIME;
        String evening_end = dayType+TicketConstants.EVENING_END_TIME;
        String peak="";
        String fareString = null;

        if(isPeakHour(Card.valueOf(morning_start).getTime(),Card.valueOf(morning_end).getTime(), inputBean.getTime())||
                isPeakHour(Card.valueOf(evening_start).getTime(),Card.valueOf(evening_end).getTime(), inputBean.getTime())){
            peak = TicketConstants.PEAK;
        }else{
            peak=TicketConstants.NON_PEAK;
        }

        fareString = dayType+"_"+inputBean.getSource()+"_"+inputBean.getDestination()+"_"+peak;
        return  Card.valueOf(fareString).getFare();
    }

    private boolean setMaximumApplicableCapss(String prevDay, InputBean inputBean) {
        int currentDailyCap = getDailyCap(prevPath, inputBean.getSource(), inputBean.getDestination());
        int currentWeeklyCap = getWeeklyCap(inputBean.getSource(), inputBean.getDestination());
        if(max.weeklyCap<currentWeeklyCap)max.weeklyCap=currentWeeklyCap;
        if(currentDailyCap!=-1){
            if(max.dailyCap <currentDailyCap)max.dailyCap =currentDailyCap;
        }
        return true;
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

    public void printFare(InputBean inputBean) {
        System.out.println("Day: "+inputBean.getDay()+"  Time: "+
                inputBean.getTime()+" Path: "+
                inputBean.getSource()+"-"+
                inputBean.getDestination()+" Fare: "+fare);
    }
}
