package com.sahaj.client;

import com.sahaj.constants.TicketConstants;
import com.sahaj.entities.PathList;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Client {
    static int dailyCap = 0;
    static int weeklyCap = 0;
    static boolean isDailyCapReached = false;
    static PathList head;
    static int maxCap =0;
    static HashMap<String,Integer> map = new HashMap<>();
    static{
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
        map.put(TicketConstants.ROUTE_1_1,100);
        map.put(TicketConstants.ROUTE_1_2,120);
        map.put(TicketConstants.ROUTE_2_1,120);
        map.put(TicketConstants.ROUTE_2_2,80);
    }
    public static void main(String[] args) throws ParseException {
        System.out.println("Got the package of 50 LPA from Sahaj");
/*        if(isPeakHour("07:00","10:30","10:15")){
            System.out.println("The user is travelling at peak hour");
        }else{
            System.out.println("The user is not travelling at peak hour");
        }
        if(isWeekend("SUNDAY")) System.out.println("Weekend");
        else System.out.println("No weekend");*/
        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\travelTracker.txt");
            Scanner scanner = new Scanner(fis);
//            File file=new File("src\\main\\resources\\travelTracker.txt");
//            FileReader fr=new FileReader(file);   //reads the file
//            BufferedReader br=new BufferedReader(fr);
//            String line;
            String prevPath="";
            while(scanner.hasNextLine()){
//            if((line= br.readLine())!=null){
                String line = scanner.nextLine();
                String[] arr = line.split(",");
                String day = arr[0];
                String time = arr[1];
                String source = arr[2];
                String destination = arr[3];
                System.out.println(line);
                System.out.println(calculateFare(prevPath,day,time,source,destination,scanner.hasNextLine()));
                prevPath=source+"-"+destination;
            }
            scanner.close();
//            br.close();
//            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static int calculateFare(String prevPath, String day, String time, String source, String destination, boolean isNextPresent){
//        if(!prevDay.equalsIgnoreCase(day))resetPathList(source+"-"+destination);
        int fare = 0;
        System.out.print("Day: "+day+" Source: "+source+" Destination:"+destination+" Travel time:"+time+" ");
        int cap = updatePathList(prevPath,source+"-"+destination);
        if(cap!=-1){
            if(maxCap<cap)maxCap=cap;
        }else{
            System.out.println("invalid path chosen#####");
            return -1;
        }
        if(!isWeekend(day)){
            //incoming to zone 1
            if(!source.equalsIgnoreCase(TicketConstants.ZONE_1)
                    && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                //is Peak hour
                if(!isPeakHour(TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_START_TIME,TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_END_TIME,time)){
                    System.out.println("Source zone: "+source+" Destination zone:"+destination+" Travel time:"+time+"Fare Category: Peak weekday");
                    fare=TicketConstants.PEAK_HOUR_FARE_1_2;
                }else{
                    System.out.println("Fare Category: Non Peak weekday");
                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                }

                dailyCap+=fare;
                if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>maxCap){
                    fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                    System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                    dailyCap=maxCap;
                    isDailyCapReached = true;
                }
                System.out.println("D Cap:"+dailyCap);
                return fare;
            }else{
                //Non incoming to zone 1
                if(isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_END_TIME,time)||
                        isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_END_TIME,time)){
                    System.out.println("Fare Category: Peak weekday");
                    if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_1;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>maxCap){
                            fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=maxCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("D Cap:"+dailyCap);
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_2) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_2_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>maxCap){
                            fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=maxCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("D Cap:"+dailyCap);
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>maxCap){
                            fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=maxCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("D Cap:"+dailyCap);
                    }else{
                        System.out.println("Invalid source / destination");
                        return fare;
                    }
                }else {
                    System.out.println("Fare Category: Non Peak weekday");
//                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                    if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_1;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>maxCap){
                            fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=maxCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("D Cap:"+dailyCap);
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_2) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_2_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>maxCap){
                            fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=maxCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("D Cap:"+dailyCap);
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                        dailyCap+=fare;
                        if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>maxCap){
                            fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                            System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                            dailyCap=maxCap;
                            isDailyCapReached = true;
                        }
                        System.out.println("D Cap:"+dailyCap);
                    }else{
                        System.out.println("Invalid source / destination");
                        return fare;
                    }
                }

                return fare;
            }
        }else{
            if(!source.equalsIgnoreCase(TicketConstants.ZONE_1)
                    && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                if(!isPeakHour(TicketConstants.ZONE_2_1_WEEKDEND_OFF_PEAK_HOUR_START_TIME,TicketConstants.ZONE_2_1_WEEKEND_OFF_PEAK_HOUR_END_TIME,time)){
                    System.out.println("Fare Category: Peak weekend");
                    fare=TicketConstants.PEAK_HOUR_FARE_1_2;
                }else{
                    System.out.println("Fare Category: Non Peak weekend");
                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                }
                dailyCap+=fare;
                if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>maxCap){
                    fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                    System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                    dailyCap=maxCap;
                    isDailyCapReached = true;
                }
                System.out.println("D Cap:"+dailyCap);
                return fare;
            }else{
/*                if((isPeakHour(TicketConstants.WEEKEND_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKEND_PEAK_HOUR_MORNING_END_TIME,time)||
                        isPeakHour(TicketConstants.WEEKEND_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKEND_PEAK_HOUR_EVENING_END_TIME,time))){
                    System.out.println("Fare Category: Peak weekend");
                }else{
                    System.out.println("Fare Category: Non Peak weekend");
                }*/
                if(isPeakHour(TicketConstants.WEEKEND_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKEND_PEAK_HOUR_MORNING_END_TIME,time)||
                        isPeakHour(TicketConstants.WEEKEND_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKEND_PEAK_HOUR_EVENING_END_TIME,time)){
                    System.out.println("Fare Category: Peak Weekend");
                    if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_1;
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_2) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_2_2;
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.PEAK_HOUR_FARE_1_2;
                    }else{
                        System.out.println("Invalid source / destination");
                        return fare;
                    }
                }else {
                    System.out.println("Fare Category: Non Peak Weekend");
//                    fare=TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                    if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_1;
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_2) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_2_2;
                    }else if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                        fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_2;
                    }else{
                        System.out.println("Invalid source / destination");
                        return fare;
                    }
                }
                dailyCap+=fare;
                if(dailyCap>=TicketConstants.INTERZONAL_DAILY_CAP&&dailyCap>maxCap){
                    fare=maxCap+fare-dailyCap;if(fare<0)fare=0;
                    System.out.println("Daily cap reached limit:"+maxCap+" Fare reduced and applicable is Rs. "+fare);
                    dailyCap=maxCap;
                    isDailyCapReached = true;
                }
                System.out.println("D Cap:"+dailyCap);
                return fare;
            }
        }
    }

/*    private static void resetPathList(String path) {
        while(!head.getRoute().equalsIgnoreCase(path)){
            head=head.getNext();
        }
        head.getCap();
    }*/

    private static int updatePathList(String prevPath, String path) {
        /*if(head.getRoute().equalsIgnoreCase(path))return head.getCap();
        head = head.getNext();
        if(head.getRoute().equalsIgnoreCase(path))return head.getCap();
        else return -1;*/
        if(prevPath.equalsIgnoreCase(TicketConstants.ROUTE_1_1)&&path.equalsIgnoreCase(TicketConstants.ROUTE_2_2))return -1;
        return map.get(path);

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
