package com.sahaj.service;

import com.sahaj.model.AttributeCache;
import com.sahaj.model.InputBean;

public interface Travel {
    public int calculateFare(InputBean inputBean);
    public String resetState(String prevPath,boolean isWeekCompleted,String day);
    public boolean isWeekend(String day);
    public boolean isPeakHour(String startTime,String endTime,String userBuyTime );
    public void printFare(InputBean inputBean, AttributeCache cache);
}
