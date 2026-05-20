package de.t3isp.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingActivitiesImpl implements BookingActivities {

    private static final Logger log = LoggerFactory.getLogger(BookingActivitiesImpl.class);

    @Override
    public String bookHotel(String bookingId) {
        log.info("[{}] ✓ Hotel gebucht", bookingId);
        return "hotel-" + bookingId;
    }

    @Override
    public void cancelHotel(String bookingId) {
        log.info("[{}] ↩ Hotel STORNIERT (Kompensation C1)", bookingId);
    }

    @Override
    public String bookFlight(String bookingId) {
        log.info("[{}] ✓ Flug gebucht", bookingId);
        return "flight-" + bookingId;
    }

    @Override
    public void cancelFlight(String bookingId) {
        log.info("[{}] ↩ Flug STORNIERT (Kompensation C2)", bookingId);
    }

    @Override
    public void chargePayment(String bookingId, double amount) {
        if (amount > 1000) {
            log.warn("[{}] ✗ Zahlung abgelehnt: {} EUR > Limit 1000 EUR", bookingId, amount);
            throw new RuntimeException("Zahlung abgelehnt: " + amount + " EUR ueberschreitet Limit");
        }
        log.info("[{}] ✓ Zahlung von {} EUR erfolgreich", bookingId, amount);
    }
}
