package com.sahaj.client;

import com.sahaj.constants.TicketConstants;
import com.sahaj.entities.CurrentDay;
import com.sahaj.model.InputBean;
import com.sahaj.entities.PathList;
import com.sahaj.model.InputBean2;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * TODO: Interface:
 * weekday and weekend to implement
 */

/**
 * TODO: TDD addition
 */
public class Client {
    static int dailyCap = 0;
    static int weeklyCap = 0;
    static boolean isDailyCapReached = false;
    static PathList max = new PathList();
    static int maxCap =0;
    static HashMap<String,Integer> dailyCaps = new HashMap<>();
    static HashMap<String,Integer> weeklyCaps = new HashMap<>();
    static String firsday = "";
    static boolean isFirstday = true;
    static CurrentDay head;
    static{

        CurrentDay sun=new CurrentDay(TicketConstants.SUNDAY);
        CurrentDay sat=new CurrentDay(TicketConstants.SATURDAY,sun);
        CurrentDay fri=new CurrentDay(TicketConstants.FRIDAY,sat);
        CurrentDay thurs=new CurrentDay(TicketConstants.THURSDAY,fri);
        CurrentDay wed=new CurrentDay(TicketConstants.WEDNESDAY,thurs);
        CurrentDay tuesday=new CurrentDay(TicketConstants.TUESDAY,wed);
        CurrentDay monday=new CurrentDay(TicketConstants.MONDAY,tuesday);
        sun.next=monday;
         head = monday;
/*        PathList node_1_1 = new PathList(TicketConstants.ROUTE_1_1,TicketConstants.ZONE_1_1_DAILY_CAP);
        PathList node_1_2 = new PathList(TicketConstants.ROUTE_1_2,TicketConstants.INTERZONAL_DAILY_CAP);
        PathList node_2_2 = new PathList(TicketConstants.ROUTE_2_2,TicketConstants.ZONE_2_2_DAILY_CAP);
        PathList node_2_1 = new PathList(TicketConstants.ROUTE_2_1,TicketConstants.INTERZONAL_DAILY_CAP);

        head = node_1_1;
//        node_1_1.setNext(node_1_2);
//        node_1_2.setNext(node_2_2);
//        node_2_2.setNext(node_2_1);
//        node_2_1.setNext(node_1_1);

        node_1_1.setSameZone(node_1_2);
        node_1_2.setSameZone(node_1_1);
        node_1_2.setNext(node_2_1);

        node_2_1.setSameZone(node_2_2);
        node_2_2.setSameZone(node_2_1);
        node_2_1.setNext(node_1_2);*/

        dailyCaps.put(TicketConstants.ROUTE_1_1,100);
        dailyCaps.put(TicketConstants.ROUTE_1_2,120);
        dailyCaps.put(TicketConstants.ROUTE_2_1,120);
        dailyCaps.put(TicketConstants.ROUTE_2_2,80);

        weeklyCaps.put(TicketConstants.ROUTE_1_1,500);
        weeklyCaps.put(TicketConstants.ROUTE_1_2,600);
        weeklyCaps.put(TicketConstants.ROUTE_2_1,600);
        weeklyCaps.put(TicketConstants.ROUTE_2_2,400);
    }
    public static void main(String[] args) throws ParseException {
        System.out.println("Got the package of 50 LPA from Sahaj");

        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\travelTracker.txt");
            Scanner scanner = new Scanner(fis);

            String prevPath="";
            String prevDay="";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] arr = line.split(",");
                String day = arr[0];
                String time = arr[1];
                String source = arr[2];
                String destination = arr[3];
                System.out.println(line);
                if(firsday.equalsIgnoreCase(day)&&firsday!=""){
                    resetState(prevPath,true,day);
                    isFirstday=true;
                }
                if(isFirstday){
                    while (!head.day.equalsIgnoreCase(day)){
                        head=head.next;
                    }
                    isFirstday=false;
                }
                if(!prevDay.equalsIgnoreCase(TicketConstants.MONDAY)&&day.equalsIgnoreCase(TicketConstants.MONDAY)){
                    weeklyCap=0;
                    System.out.println("**********************************************************************");
                }
                InputBean2 inputBean = new InputBean2();
                inputBean.setDay(day);
                inputBean.setTime(time);
                inputBean.setSource(source);
                inputBean.setDestination(destination);
                inputBean.setPrevPath(prevPath);
                inputBean.setPrevDay(prevDay);

                int cost = calculateFare(inputBean);

                if(inputBean.getPrevDay().equalsIgnoreCase(TicketConstants.SUNDAY)&&day.equalsIgnoreCase(TicketConstants.MONDAY)){
//                    if(cost!=-1)weeklyCap=cost;
                    System.out.println("Weekly Cap:"+weeklyCap+" \nMaximum weekly cap applicable::"+max.weeklyCap);
                    resetState(prevPath,true,day);
                }else{
                    System.out.println("Weekly Cap:"+weeklyCap+" \nMaximum weekly cap applicable::"+max.weeklyCap);
                }
                if(cost!=-1)                System.out.println("----------------------------------");
                else {
                    System.out.println("Execution aborted...");
                    return;
                }
                prevPath=destination;
                prevDay=day;

            }
            scanner.close();
//            br.close();
//            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * TODO: TDD creation
     * Create the test case class:
     * Create failfast: write the fail test first
     * Combinations of input
     * XP : Extended programming
     * mock the methods: using constructor : parametrised and non-parametrised
     * @return
     */
//    public static int calculateFare(String day, 
// String time, String source, 
// String destination,String prevPath,String inputBean.getPrevDay()){ ///TODO: Create pojo
    public static int calculateFare(InputBean2 inputBean){
        if(!inputBean.getPrevDay().equalsIgnoreCase(inputBean.getDay()))
            inputBean.setPrevPath(resetState(inputBean.getPrevPath(),false, inputBean.getDay()));
        int fare = 0;
        System.out.print("Day: "+ inputBean.getDay()+" Source: "+ inputBean.getSource()+" Destination:"+ inputBean.getDestination()+" Travel time:"+ inputBean.getTime()+" ");
        int currentDailyCap = getDailyCap(inputBean.getPrevPath(), inputBean.getSource(), inputBean.getDestination());
        int currentWeeklyCap = getWeeklyCap(inputBean.getSource(), inputBean.getDestination());
        if(max.weeklyCap<currentWeeklyCap)max.weeklyCap=currentWeeklyCap;
        if(currentDailyCap!=-1){
            if(max.dailyCap <currentDailyCap)max.dailyCap =currentDailyCap;

        }else{
            System.out.println("invalid path chosen#####");
            return -1;
        }
        if(!isWeekend(inputBean.getDay())){
            //incoming to zone 1
            if(!inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1)
                    && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
                //is Peak hour
                /**
                 * TODO: Calculate it from POJO
                 */
                /**
                 * TODO: Check solid principles
                 */
                /**
                 * TODO: remove negation from if condition
                 */
                if(!isPeakHour(TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_START_TIME,TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_END_TIME, inputBean.getTime())){
                    /**
                     * TODO: No sysouts
                     */
                    System.out.println("Source zone: "+ inputBean.getSource()+" Destination zone:"+ inputBean.getDestination()+" Travel time:"+ inputBean.getTime()+"Fare Category: Peak weekday");
                    fare=TicketConstants.PEAK_HOUR_FARE_1_2;
                }else{
                    System.out.println("Fare Category: Non Peak weekday");
                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                }
                /**
                 * TODO: call from signature for daily cap and weekly cap
                 *
                 */
                dailyCap+=fare;
                if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>max.dailyCap){
                    fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                    System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                    dailyCap=max.dailyCap;
                    isDailyCapReached = true;
                }
//                weeklyCap+=fare;

                System.out.println("Daily cap:"+dailyCap);
                /*if(!isNextPresent)weeklyCap+=dailyCap;*/
                weeklyCap+=fare;
                if(weeklyCap>max.weeklyCap){
                    fare=max.weeklyCap+fare-weeklyCap;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }
                return fare;
            }else{
                //Non incoming to zone 1
                if(isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_END_TIME, inputBean.getTime())||
                        isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_END_TIME, inputBean.getTime())){
                    System.out.println("Fare Category: Peak weekday");
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
                        /*if(!isNextPresent)weeklyCap+=dailyCap;*/weeklyCap+=fare;return fare;
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
                        /*if(!isNextPresent)weeklyCap+=dailyCap;*/weeklyCap+=fare;return fare;
                    }
                }

                /*if(!isNextPresent)weeklyCap+=dailyCap;*/weeklyCap+=fare;if(weeklyCap>max.weeklyCap){
                    fare=max.weeklyCap+fare-weeklyCap;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }
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

                System.out.println("Daily cap:"+dailyCap);
                /*if(!isNextPresent)weeklyCap+=dailyCap;*/
                weeklyCap+=fare;
                if(weeklyCap>max.weeklyCap){
                    fare=max.weeklyCap+fare-weeklyCap;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }
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
                        /*if(!isNextPresent)weeklyCap+=dailyCap;*/weeklyCap+=fare;return fare;
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
                        /*if(!isNextPresent)weeklyCap+=dailyCap;*/weeklyCap+=fare;return fare;
                    }
                }

                /*if(!isNextPresent)weeklyCap+=dailyCap;*/weeklyCap+=fare;if(weeklyCap>max.weeklyCap){
                    fare=max.weeklyCap+fare-weeklyCap;
                    if(fare<0)fare=0;
                    System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                    weeklyCap=max.weeklyCap;
                }
                return fare;
            }
        }
    }

    private static String resetState(String prevPath,boolean isWeekCompleted,String day) {
        /*while(!head.getRoute().equalsIgnoreCase(path)){
            head=head.getNext();
        }
        head.getDailyCap();*/
//        weeklyCap+=dailyCap;
        dailyCap=0;
        max.dailyCap =0;
        prevPath="";

        if(isWeekCompleted){
//            weeklyCap=0;
            max = new PathList();
        }
        else{
            while (!head.day.equalsIgnoreCase(day)){
                head=head.next;
            }
        }
        return prevPath;
    }
    private static int getWeeklyCap(String source,String destination){return weeklyCaps.get(source+"-"+destination);}
    private static int getDailyCap(String prevDestination, String source, String destination) {
        /*if(head.getRoute().equalsIgnoreCase(path))return head.getDailyCap();
        head = head.getNext();
        if(head.getRoute().equalsIgnoreCase(path))return head.getDailyCap();
        else return -1;*/
        if(!prevDestination.equalsIgnoreCase("")&&!prevDestination.equalsIgnoreCase(source))return -1;
        return dailyCaps.get(source+"-"+destination);

    }

    public static boolean isWeekend(String day){
        boolean isWeekend=false;
        if(day.equalsIgnoreCase(TicketConstants.SATURDAY) || day.equalsIgnoreCase(TicketConstants.SUNDAY))isWeekend=true;
        return isWeekend;
    }
    public static boolean isPeakHour(String timeString1,String timeString2,String userBuyTime ){
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
//            System.out.println("time 1: "+calendar1.getTime()+ " \ntime 2: "+calendar2.getTime()+"\nUser time: "+calendar3.getTime());
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                isValid = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
