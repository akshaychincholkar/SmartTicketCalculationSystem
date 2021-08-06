package com.sahaj.utils;

import com.sahaj.constants.TicketConstants;
import com.sahaj.entities.CurrentDay;
import com.sahaj.model.InputBean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Util {
    public static HashMap<String,Integer> dailyCappingLimits = new HashMap<>();
    public static HashMap<String,Integer> weeklyCappingLimits = new HashMap<>();
    public static CurrentDay head;
    public static FileInputStream fis = null;

    static {
        try {
            fis = new FileInputStream(ResourceBundle.getBundle("application").getString("file.path"));
            CurrentDay sun=new CurrentDay(TicketConstants.SUNDAY);
            CurrentDay sat=new CurrentDay(TicketConstants.SATURDAY,sun);
            CurrentDay fri=new CurrentDay(TicketConstants.FRIDAY,sat);
            CurrentDay thurs=new CurrentDay(TicketConstants.THURSDAY,fri);
            CurrentDay wed=new CurrentDay(TicketConstants.WEDNESDAY,thurs);
            CurrentDay tuesday=new CurrentDay(TicketConstants.TUESDAY,wed);
            CurrentDay monday=new CurrentDay(TicketConstants.MONDAY,tuesday);
            sun.next=monday;
            head = monday;
            dailyCappingLimits.put(TicketConstants.ROUTE_1_1,100);
            dailyCappingLimits.put(TicketConstants.ROUTE_1_2,120);
            dailyCappingLimits.put(TicketConstants.ROUTE_2_1,120);
            dailyCappingLimits.put(TicketConstants.ROUTE_2_2,80);
            weeklyCappingLimits.put(TicketConstants.ROUTE_1_1,500);
            weeklyCappingLimits.put(TicketConstants.ROUTE_1_2,600);
            weeklyCappingLimits.put(TicketConstants.ROUTE_2_1,600);
            weeklyCappingLimits.put(TicketConstants.ROUTE_2_2,400);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<InputBean> readInputFile(){
        List<InputBean> inputBeans=new ArrayList<>();
            Scanner scanner = new Scanner(fis);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] arr = line.split(",");
                InputBean inputBean = new InputBean();
                inputBean.setDay(arr[0]);
                inputBean.setTime(arr[1]);
                inputBean.setSource(arr[2]);
                inputBean.setDestination(arr[3]);
                inputBean.setPrevDay("");
                inputBeans.add(inputBean);

            }
            scanner.close();
        return inputBeans;
    }
}
