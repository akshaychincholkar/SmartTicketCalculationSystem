package service;

import com.sahaj.model.AttributeCache;
import com.sahaj.model.InputBean;
import com.sahaj.service.TravelImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TravelImplTest {
    InputBean inputBean1;
    InputBean inputBean2;
    InputBean inputBean3;
    InputBean inputBean4;
    InputBean inputBean5;
    InputBean inputBean6;
    InputBean inputBean7;
    InputBean inputBean8;
    InputBean inputBean9;
    InputBean inputBean10;
    InputBean inputBean11;
    InputBean inputBean12;
    InputBean inputBean13;
    InputBean inputBean14;
    InputBean inputBean15;
    InputBean inputBean16;
    InputBean inputBean17;
    InputBean inputBean18;
    InputBean inputBean19;
    InputBean inputBean20;
    InputBean inputBean21;
    InputBean inputBean22;
    InputBean inputBean23;
    InputBean inputBean24;
    InputBean inputBean25;
    InputBean inputBean26;
    InputBean inputBean27;
    InputBean inputBean28;
    InputBean inputBean29;
    InputBean inputBean30;
    InputBean inputBean31;
    InputBean inputBean32;

    InputBean interzonal1;
    InputBean interzonal2;
    InputBean interzonal3;
    InputBean interzonal4;

    InputBean stateReset1;
    InputBean stateReset2;

    InputBean printBean;

    TravelImpl travelImpl;
    private AttributeCache cache;

    @Before
    public void setupTestData() {
    inputBean1 = new InputBean("MONDAY","10:15","1","2");
    inputBean2 = new InputBean("MONDAY","10:05","1","2");
    inputBean3 = new InputBean("MONDAY","18:15","1","2");
    inputBean4 = new InputBean("MONDAY","18:35","1","2");
    inputBean5 = new InputBean("MONDAY","19:15","2","2");
    inputBean6 = new InputBean("TUESDAY","10:15","1","2");
    inputBean7 = new InputBean("TUESDAY","10:05","1","2");
    inputBean8 = new InputBean("TUESDAY","18:15","1","2");
    inputBean9 = new InputBean("TUESDAY","18:35","1","2");
    inputBean10 = new InputBean("TUESDAY","19:15","2","2");
    inputBean11 = new InputBean("WEDNESDAY","10:15","1","2");
    inputBean12 = new InputBean("WEDNESDAY","10:05","1","2");
    inputBean13 = new InputBean("WEDNESDAY","18:15","1","2");
    inputBean14 = new InputBean("WEDNESDAY","18:35","1","2");
    inputBean15 = new InputBean("WEDNESDAY","19:15","2","2");
    inputBean16 = new InputBean("THURSDAY","10:15","1","2");
    inputBean17 = new InputBean("THURSDAY","10:05","1","2");
    inputBean18 = new InputBean("THURSDAY","18:15","1","2");
    inputBean19 = new InputBean("THURSDAY","18:35","1","2");
    inputBean20 = new InputBean("THURSDAY","19:15","2","2");
    inputBean21 = new InputBean("FRIDAY","10:15","1","2");
    inputBean22 = new InputBean("FRIDAY","10:05","1","2");
    inputBean23 = new InputBean("FRIDAY","18:15","1","2");
    inputBean24 = new InputBean("FRIDAY","18:35","1","2");
    inputBean25 = new InputBean("FRIDAY","19:15","2","2");
    inputBean26 = new InputBean("SATURDAY","10:15","1","2");
    inputBean27 = new InputBean("SATURDAY","10:05","1","2");
    inputBean28 = new InputBean("SATURDAY","18:15","1","2");
    inputBean29 = new InputBean("SUNDAY","18:35","1","2");
    inputBean30 = new InputBean("SUNDAY","19:15","2","2");
    inputBean31 = new InputBean("MONDAY","10:15","1","2");
    inputBean32 = new InputBean("MONDAY","10:05","1","2");

    travelImpl = new TravelImpl();
    cache = travelImpl.getCache();
    cache.setPrevDay("MONDAY");

    interzonal1 = new InputBean("TUESDAY","18:25","2","1");
    interzonal2 = new InputBean("WEDNESDAY","10:15","2","1");
    interzonal3 = new InputBean("SATURDAY","21:15","2","1");
    interzonal4 = new InputBean("SUNDAY","10:15","2","1");

    stateReset1 = new InputBean("SUNDAY","10:15","2","1");
    stateReset2 = new InputBean("SUNDAY","10:15","2","1");

//        Day: WEDNESDAY  Time: 16:15 Path: 1-1 Fare: 25
    printBean = new InputBean("WEDNESDAY","16:15","1","1");
    }

    @After
    public void deleteTestData() {
        inputBean1=null;
        inputBean2=null;
        inputBean3=null;
        inputBean4=null;
        inputBean5=null;
        travelImpl =null;
    }
    @Test
//    @DisplayName("Calculate fare")
    public void testCalculateFare() {
        assertEquals(35, travelImpl.calculateFare(inputBean1));
        assertEquals(35, travelImpl.calculateFare(inputBean2));
        assertEquals(35, travelImpl.calculateFare(inputBean3));
        assertEquals(15, travelImpl.calculateFare(inputBean4));
        assertEquals(0, travelImpl.calculateFare(inputBean5));
        assertEquals(35, travelImpl.calculateFare(inputBean6));
        cache.setPrevDay("TUESDAY");
        assertEquals(35, travelImpl.calculateFare(inputBean7));
        assertEquals(35, travelImpl.calculateFare(inputBean8));
        assertEquals(15, travelImpl.calculateFare(inputBean9));
        assertEquals(0, travelImpl.calculateFare(inputBean10));
        assertEquals(35, travelImpl.calculateFare(inputBean11));
        cache.setPrevDay("WEDNESDAY");
        assertEquals(35, travelImpl.calculateFare(inputBean12));
        assertEquals(35, travelImpl.calculateFare(inputBean13));
        assertEquals(15, travelImpl.calculateFare(inputBean14));
        assertEquals(0, travelImpl.calculateFare(inputBean15));
        assertEquals(35, travelImpl.calculateFare(inputBean16));
        cache.setPrevDay("THURSDAY");
        assertEquals(35, travelImpl.calculateFare(inputBean17));
        assertEquals(35, travelImpl.calculateFare(inputBean18));
        assertEquals(15, travelImpl.calculateFare(inputBean19));
        assertEquals(0, travelImpl.calculateFare(inputBean20));
        assertEquals(35, travelImpl.calculateFare(inputBean21));
        cache.setPrevDay("FRIDAY");
        assertEquals(35, travelImpl.calculateFare(inputBean22));
        assertEquals(35, travelImpl.calculateFare(inputBean23));
        assertEquals(15, travelImpl.calculateFare(inputBean24));
        assertEquals(0, travelImpl.calculateFare(inputBean25));
        assertEquals(0, travelImpl.calculateFare(inputBean26));
        cache.setPrevDay("SATURDAY");
        assertEquals(0, travelImpl.calculateFare(inputBean27));
        assertEquals(0, travelImpl.calculateFare(inputBean28));
        assertEquals(0, travelImpl.calculateFare(inputBean29));
        assertEquals(0, travelImpl.calculateFare(inputBean30));
        cache.setPrevDay("SUNDAY");
        cache.setWeeklyCap(0);
        assertEquals(35, travelImpl.calculateFare(inputBean31));
        assertEquals(35, travelImpl.calculateFare(inputBean32));
    }

    @Test
    public void calculateInterZonalFare() {

        assertEquals(30, travelImpl.calculateFare(interzonal1));
        assertEquals(35, travelImpl.calculateFare(interzonal2));
        assertEquals(30, travelImpl.calculateFare(interzonal3));
        assertEquals(35, travelImpl.calculateFare(interzonal4));
    }

    @Test
    public void resetState() {
        cache.setDailyCap(120);
        cache.getMax().dailyCap=120;
        travelImpl.resetState("",false,"WEDNESDAY");
        assertEquals(0, cache.getDailyCap());
        assertEquals(0, cache.getMax().dailyCap);
    }

    @Test
    public void isWeekend() {
        assertEquals(false, travelImpl.isWeekend("MONDAY"));
        assertEquals(true, travelImpl.isWeekend("SATURDAY"));
    }

    @Test
    public void isPeakHour() {
        assertEquals(true, travelImpl.isPeakHour("7:00","10:00","9:21"));
        assertEquals(false, travelImpl.isPeakHour("7:00","10:00","12:21"));
    }

}