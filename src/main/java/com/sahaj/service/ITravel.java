package com.sahaj.service;

import com.sahaj.model.InputBean;

public interface ITravel {
    public int calculateFare(InputBean inputBean);
    public int calculateZonalFare();
}
