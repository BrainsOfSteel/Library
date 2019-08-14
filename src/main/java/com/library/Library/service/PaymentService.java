package com.library.Library.service;

import java.text.ParseException;

public interface PaymentService {
    double fineCalculation(Long borrowedTimeSeconds, Long returnedTimeSeconds) throws ParseException;
}
