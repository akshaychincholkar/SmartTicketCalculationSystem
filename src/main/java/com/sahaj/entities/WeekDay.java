package com.sahaj.entities;

import com.sahaj.constants.TicketConstants;

public class WeekDay implements Day {
    @Override
    public int processFare(String source,String destination) {
        //incoming to zone 1
        int fare =0;
/*        if(!source.equalsIgnoreCase(TicketConstants.ZONE_1)
                && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
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
            if(!isPeakHour(TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_START_TIME,TicketConstants.ZONE_2_1_WEEKDAY_OFF_PEAK_HOUR_END_TIME,time)){
                *//**
                 * TODO: No sysouts
                 *//*
                System.out.println("Source zone: "+source+" Destination zone:"+destination+" Travel time:"+time+"Fare Category: Peak weekday");
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

            System.out.println("Daily cap:"+dailyCap);
            *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*
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
            if(isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_MORNING_END_TIME,time)||
                    isPeakHour(TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_START_TIME,TicketConstants.WEEKDAY_PEAK_HOUR_EVENING_END_TIME,time)){
                System.out.println("Fare Category: Peak weekday");
                if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                    fare = TicketConstants.PEAK_HOUR_FARE_1_1;
                    dailyCap+=fare;
                    if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>max.dailyCap){
                        fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                        System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                        dailyCap=max.dailyCap;
                        isDailyCapReached = true;
                    }
                    System.out.println("Daily cap:"+dailyCap);
                }else if(source.equalsIgnoreCase(TicketConstants.ZONE_2) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                    fare = TicketConstants.PEAK_HOUR_FARE_2_2;
                    dailyCap+=fare;
                    if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>max.dailyCap){
                        fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                        System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                        dailyCap=max.dailyCap;
                        isDailyCapReached = true;
                    }
                    System.out.println("Daily cap:"+dailyCap);
                }else if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
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
                if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_1)){
                    fare = TicketConstants.OFF_PEAK_HOUR_FARE_1_1;
                    dailyCap+=fare;
                    if(dailyCap>=TicketConstants.ZONE_1_1_DAILY_CAP&&dailyCap>max.dailyCap){
                        fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                        System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                        dailyCap=max.dailyCap;
                        isDailyCapReached = true;
                    }
                    System.out.println("Daily cap:"+dailyCap);
                }else if(source.equalsIgnoreCase(TicketConstants.ZONE_2) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
                    fare = TicketConstants.OFF_PEAK_HOUR_FARE_2_2;
                    dailyCap+=fare;
                    if(dailyCap>=TicketConstants.ZONE_2_2_DAILY_CAP&&dailyCap>max.dailyCap){
                        fare=max.dailyCap +fare-dailyCap;if(fare<0)fare=0;
                        System.out.println("Daily cap reached limit:"+max.dailyCap +" Fare reduced and applicable is Rs. "+fare);
                        dailyCap=max.dailyCap;
                        isDailyCapReached = true;
                    }
                    System.out.println("Daily cap:"+dailyCap);
                }else if(source.equalsIgnoreCase(TicketConstants.ZONE_1) && destination.equalsIgnoreCase(TicketConstants.ZONE_2)){
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

            *//*if(!isNextPresent)weeklyCap+=dailyCap;*//*weeklyCap+=fare;if(weeklyCap>max.weeklyCap){
                fare=max.weeklyCap+fare-weeklyCap;
                if(fare<0)fare=0;
                System.out.println("Weekly cap reached limit:"+max.weeklyCap +" Fare reduced and applicable is Rs. "+fare);
                weeklyCap=max.weeklyCap;
            }
            return fare;
        }*/
        return 0;
    }
}
