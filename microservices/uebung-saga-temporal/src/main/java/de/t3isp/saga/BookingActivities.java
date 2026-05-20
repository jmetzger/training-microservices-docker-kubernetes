package de.t3isp.saga;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface BookingActivities {
    String bookHotel(String bookingId);
    void   cancelHotel(String bookingId);
    String bookFlight(String bookingId);
    void   cancelFlight(String bookingId);
    void   chargePayment(String bookingId, double amount);
}
