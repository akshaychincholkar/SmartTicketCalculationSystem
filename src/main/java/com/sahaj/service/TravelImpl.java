package com.sahaj.service;

import com.sahaj.constants.Card;
import com.sahaj.constants.TicketConstants;
import com.sahaj.entities.PathCircularLinkedList;
import com.sahaj.model.AttributeCache;
import com.sahaj.model.InputBean;
import com.sahaj.utils.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TravelImpl implements Travel {
    AttributeCache cache = new AttributeCache();

    public AttributeCache getCache() {
        return cache;
    }

    public TravelImpl() {
        cache.setMax(new PathCircularLinkedList());
    }

    @Override
    public int calculateFare(InputBean inputBean) {
        //Resetting the daily cap and required attributes for new day
        if(!cache.getPrevDay().equalsIgnoreCase(inputBean.getDay()))
            cache.setPrevPath(resetState(cache.getPrevPath(),false, inputBean.getDay()));
        if(!setMaximumApplicableCaps(cache.getPrevDay(),inputBean))return -1;
        if(!isWeekend(inputBean.getDay()))cache.setDayType( TicketConstants.WEEKDAY);
        else cache.setDayType(TicketConstants.WEEKEND);
        if(!inputBean.getSource().equalsIgnoreCase(TicketConstants.ZONE_1)
                && inputBean.getDestination().equalsIgnoreCase(TicketConstants.ZONE_1)){
            cache.setFare(calculateInterZonalFare(inputBean));
        }else{
            cache.setFare(calculateGenericFare(inputBean));
        }
        cache.setDailyCap(cache.getDailyCap()+cache.getFare());
        if(cache.getDailyCap()>cache.getMax().dailyCap){
            processDailyCap();
        }
        cache.setWeeklyCap(cache.getWeeklyCap()+cache.getFare());
        if(cache.getWeeklyCap()>cache.getMax().weeklyCap){
            processWeeklyCap();
        }
        return cache.getFare();
    }

    private int calculateInterZonalFare(InputBean inputBean) {
        String zonal_start_time = cache.getDayType()+TicketConstants.OFF_PEAK_HOUR_START_TIME;
        String zonal_end_time = cache.getDayType()+TicketConstants.OFF_PEAK_HOUR_END_TIME;
        if(!isPeakHour(Card.valueOf(zonal_start_time).getTime(),Card.valueOf(zonal_end_time).getTime(), inputBean.getTime())){
            cache.setFare(Card.valueOf(cache.getDayType()+TicketConstants.UNDERSCORE+inputBean.getSource()+TicketConstants.UNDERSCORE+TicketConstants.ZONE_1+TicketConstants.UNDERSCORE+TicketConstants.PEAK).getFare());
        }else{
            cache.setFare(Card.valueOf(cache.getDayType()+TicketConstants.UNDERSCORE+inputBean.getSource()+TicketConstants.UNDERSCORE+TicketConstants.ZONE_1+TicketConstants.UNDERSCORE+TicketConstants.NON_PEAK).getFare());
        }
        return cache.getFare();
    }

    private void processWeeklyCap() {
        cache.setDailyCap(cache.getDailyCap()-cache.getFare());
        cache.setFare(cache.getMax().weeklyCap+cache.getFare()-cache.getWeeklyCap());
        cache.setDailyCap(cache.getDailyCap()+cache.getFare());
        if(cache.getFare()<0)cache.setFare(0);
        System.out.println("|\tWeekly cap reached to "+cache.getMax().weeklyCap +"\n|\tFare applicable is Rs. "+cache.getFare());
        System.out.println("|---------------------------------");
        cache.setWeeklyCap(cache.getMax().weeklyCap);
    }

    private void processDailyCap() {
        cache.setFare(cache.getMax().dailyCap +cache.getFare()-cache.getDailyCap());
        if(cache.getFare()<0)cache.setFare(0);
        System.out.println("|\tDaily cap reached to "+cache.getMax().dailyCap +"\n|\tFare applicable is Rs. "+cache.getFare());
        System.out.println("|---------------------------------");
        cache.setDailyCap(cache.getMax().dailyCap);
        cache.setDailyCapReached(true);
    }

    private int calculateGenericFare(InputBean inputBean) {
        String morning_start = cache.getDayType()+TicketConstants.MORNING_START_TIME;
        String morning_end = cache.getDayType()+TicketConstants.MORNING_END_TIME;
        String evening_start = cache.getDayType()+TicketConstants.EVENING_START_TIME;
        String evening_end = cache.getDayType()+TicketConstants.EVENING_END_TIME;
        String peak="";
        String fareString = null;

        if(isPeakHour(Card.valueOf(morning_start).getTime(),Card.valueOf(morning_end).getTime(), inputBean.getTime())||
                isPeakHour(Card.valueOf(evening_start).getTime(),Card.valueOf(evening_end).getTime(), inputBean.getTime())){
            peak = TicketConstants.PEAK;
        }else{
            peak=TicketConstants.NON_PEAK;
        }

        fareString = cache.getDayType()+TicketConstants.UNDERSCORE+inputBean.getSource()+TicketConstants.UNDERSCORE+inputBean.getDestination()+TicketConstants.UNDERSCORE+peak;
        return  Card.valueOf(fareString).getFare();
    }

    private boolean setMaximumApplicableCaps(String prevDay, InputBean inputBean) {
        int currentDailyCap = getDailyCap(cache.getPrevPath(), inputBean.getSource(), inputBean.getDestination());
        int currentWeeklyCap = getWeeklyCap(inputBean.getSource(), inputBean.getDestination());
        if(cache.getMax().weeklyCap<currentWeeklyCap)cache.getMax().weeklyCap=currentWeeklyCap;
        if(currentDailyCap!=-1){
            if(cache.getMax().dailyCap <currentDailyCap)cache.getMax().dailyCap =currentDailyCap;
        }
        return true;
    }

    @Override
    public String resetState(String prevPath,boolean isWeekCompleted,String day) {
        cache.setDailyCap(0);
        cache.getMax().dailyCap =0;
        if(isWeekCompleted){
            cache.setMax(new PathCircularLinkedList());
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
        return Util.dailyCappingLimits.get(source+"-"+destination);
    }

    @Override
    public boolean isWeekend(String day){
        boolean isWeekend=false;
        if(day.equalsIgnoreCase(TicketConstants.SATURDAY) || day.equalsIgnoreCase(TicketConstants.SUNDAY))isWeekend=true;
        return isWeekend;
    }

    @Override
    public boolean isPeakHour(String startTime,String endTime,String userBuyTime ){
        boolean isValid = false;
        try {
            Date time1 = new SimpleDateFormat("HH:mm").parse(startTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);
            Date time2 = new SimpleDateFormat("HH:mm").parse(endTime);
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

    @Override
    public void printFare(InputBean inputBean, AttributeCache cache) {
        System.out.println("|\tDay: "+inputBean.getDay()+
                "\n|\tTime: "+inputBean.getTime()+
                "\n|\tPath: "+inputBean.getSource()+"-"+inputBean.getDestination()+
                "\n|\tFare: "+cache.getFare());
    }
}
