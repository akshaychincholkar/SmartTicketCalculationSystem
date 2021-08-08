package com.sahaj;

import com.sahaj.constants.TicketConstants;
import com.sahaj.model.InputBean;
import com.sahaj.service.Travel;
import com.sahaj.utils.Util;

import java.util.List;

public class App {
    public static void main(String[] args) {
        //Step 1: Reading the input file
        List<InputBean> inputBeans = Util.readInputFile();
        Travel travel = new Travel();
        //Step 2: Calculating the fare for each day
        for(InputBean element:inputBeans){

            if(!travel.prevDay.equalsIgnoreCase(TicketConstants.MONDAY)&&element.getDay().equalsIgnoreCase(TicketConstants.MONDAY)){
                travel.weeklyCap=0;
                travel.resetState(travel.prevPath,true,element.getDay());
                System.out.println("**********************************************************************");
                travel.firsday="";
            }
            travel.calculateFare(element);
            travel.printFare(element);
//            System.out.println("Day: "+element.getDay()+" Fare: "+travel.fare);
            System.out.println("----------------------------------");
            travel.prevPath=element.getDestination();
            travel.prevDay= element.getDay();
        }
    }
}
