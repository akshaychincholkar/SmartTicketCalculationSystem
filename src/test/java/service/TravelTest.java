package service;

import com.sahaj.model.InputBean;
import com.sahaj.service.Travel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class TravelTest {
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
    Travel travel;
    String prevDay;
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

    travel = new Travel();
    travel.prevDay="MONDAY";
    }

    @After
    public void deleteTestData() {
        inputBean1=null;
        inputBean2=null;
        inputBean3=null;
        inputBean4=null;
        inputBean5=null;
        travel=null;
    }
    @Test
    public void calculateFare() {
        assertEquals(35,travel.calculateFare(inputBean1));
        assertEquals(35,travel.calculateFare(inputBean2));
        assertEquals(35,travel.calculateFare(inputBean3));
        assertEquals(15,travel.calculateFare(inputBean4));
        assertEquals(0,travel.calculateFare(inputBean5));
        assertEquals(35,travel.calculateFare(inputBean6));
        travel.prevDay="TUESDAY";
        assertEquals(35,travel.calculateFare(inputBean7));
        assertEquals(35,travel.calculateFare(inputBean8));
        assertEquals(15,travel.calculateFare(inputBean9));
        assertEquals(0,travel.calculateFare(inputBean10));
        assertEquals(35,travel.calculateFare(inputBean11));
        travel.prevDay="WEDNESDAY";
        assertEquals(35,travel.calculateFare(inputBean12));
        assertEquals(35,travel.calculateFare(inputBean13));
        assertEquals(15,travel.calculateFare(inputBean14));
        assertEquals(0,travel.calculateFare(inputBean15));
        assertEquals(35,travel.calculateFare(inputBean16));
        travel.prevDay="THURSDAY";
        assertEquals(35,travel.calculateFare(inputBean17));
        assertEquals(35,travel.calculateFare(inputBean18));
        assertEquals(15,travel.calculateFare(inputBean19));
        assertEquals(0,travel.calculateFare(inputBean20));
        assertEquals(35,travel.calculateFare(inputBean21));
        travel.prevDay="FRIDAY";
        assertEquals(35,travel.calculateFare(inputBean22));
        assertEquals(35,travel.calculateFare(inputBean23));
        assertEquals(15,travel.calculateFare(inputBean24));
        assertEquals(0,travel.calculateFare(inputBean25));
        assertEquals(0,travel.calculateFare(inputBean26));
        travel.prevDay="SATURDAY";
        assertEquals(0,travel.calculateFare(inputBean27));
        assertEquals(0,travel.calculateFare(inputBean28));
        assertEquals(0,travel.calculateFare(inputBean29));
        assertEquals(0,travel.calculateFare(inputBean30));
    }
    @Test
    public void calculateZonalFare() {
    }

    @Test
    public void resetState() {
    }

    @Test
    public void isWeekend() {
    }

    @Test
    public void isPeakHour() {
    }

    @Test
    public void printFare() {
    }
}