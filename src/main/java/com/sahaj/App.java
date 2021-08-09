package com.sahaj;

import com.sahaj.constants.TicketConstants;
import com.sahaj.model.AttributeCache;
import com.sahaj.model.InputBean;
import com.sahaj.service.TravelImpl;
import com.sahaj.utils.Util;
import java.util.List;

public class App {
    public static void main(String[] args) {
        //Step 1: Reading the input file
        List<InputBean> inputBeans = Util.readInputFile();
        TravelImpl travelImpl = new TravelImpl();

        //Step 2: Calculating the fare for each day
        AttributeCache cache = travelImpl.getCache();
        for(InputBean element:inputBeans){
            if(!cache.getPrevDay().equalsIgnoreCase(TicketConstants.MONDAY)&&element.getDay().equalsIgnoreCase(TicketConstants.MONDAY)){
                cache.setWeeklyCap(0);
                travelImpl.resetState(cache.getPrevPath(),true,element.getDay());
                System.out.println("********START OF NEW WEEK*********");
                cache.setFirsday("");
        }
        travelImpl.calculateFare(element);

        //Step 3: Printing the output
        travelImpl.printFare(element,cache);
        System.out.println("----------------------------------");
        cache.setPrevPath(element.getDestination());
        cache.setPrevDay( element.getDay());
        }
    }
}
