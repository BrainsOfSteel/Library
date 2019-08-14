package com.library.Library.serviceImpl;


import com.library.Library.service.PaymentService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final static double finePercentagePerDay = 0.03d;
    private final static int CUTOFF_TIME_FOR_FINE_DAYS = 30;

    @Override
    public double fineCalculation(Long borrowedTimeSeconds, Long returnedTimeSeconds) throws ParseException {
        long days = getDayDifference(borrowedTimeSeconds, returnedTimeSeconds);
        //Should go to a rule engine for complex fine calculation
        if(days > CUTOFF_TIME_FOR_FINE_DAYS){
            return (days - CUTOFF_TIME_FOR_FINE_DAYS) * finePercentagePerDay;
        }
        return 0;
    }

    private long getDayDifference(Long borrowedTimeSeconds, Long returnedTimeSeconds) throws ParseException{
        Date borrowedDate = new Date(borrowedTimeSeconds);
        Date returnedDate = new Date(returnedTimeSeconds);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        f.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String borrowedDateFormat = f.format(borrowedDate);
        String returnedDateFormat = f.format(returnedDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse(borrowedDateFormat);
        Date date2 = simpleDateFormat.parse(returnedDateFormat);
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
