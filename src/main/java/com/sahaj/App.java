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
            if(travel.firsday.equalsIgnoreCase(element.getDay())&&travel.firsday!=""){
                travel.resetState(travel.prevPath,true,element.getDay());
                travel.isFirstday=true;
            }
            if(travel.isFirstday){
                while (!Util.head.day.equalsIgnoreCase(element.getDay())){
                    Util.head=Util.head.next;
                }
                travel.isFirstday=false;
            }
            int cost = travel.calculateFare(element);
            System.out.println(element.getDay()+"Fare: "+cost);
            if(element.getPrevDay().equalsIgnoreCase(TicketConstants.SUNDAY)&&element.getDay().equalsIgnoreCase(TicketConstants.MONDAY)){
                travel.resetState(travel.prevPath,true,element.getDay());
                System.out.println("Weekly Cap:"+travel.weeklyCap+" \nMaximum weekly cap applicable::"+travel.weeklyCap);
                System.out.println("**********************************************************************");
            }else{
                System.out.println("Weekly Cap:"+travel.weeklyCap+" \nMaximum weekly cap applicable::"+travel.weeklyCap);
            }
            if(cost!=-1)                System.out.println("----------------------------------");
            else {
                System.out.println("Execution aborted...");
                return;
            }
            travel.prevPath=element.getDestination();
            element.setPrevDay(element.getDay());
        }

        //Step 3: Printing the output


    }
}
