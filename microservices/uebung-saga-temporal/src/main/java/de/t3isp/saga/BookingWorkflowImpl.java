package de.t3isp.saga;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class BookingWorkflowImpl implements BookingWorkflow {

    private final BookingActivities activities = Workflow.newActivityStub(
        BookingActivities.class,
        ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(10))
            .setRetryOptions(RetryOptions.newBuilder()
                .setMaximumAttempts(1)
                .build())
            .build());

    @Override
    public String bookTrip(String bookingId, double amount) {
        Saga saga = new Saga(
            new Saga.Options.Builder()
                .setParallelCompensation(false)
                .build());

        try {
            activities.bookHotel(bookingId);
            saga.addCompensation(activities::cancelHotel, bookingId);

            activities.bookFlight(bookingId);
            saga.addCompensation(activities::cancelFlight, bookingId);

            activities.chargePayment(bookingId, amount);

            return "Buchung " + bookingId + " erfolgreich abgeschlossen";

        } catch (Exception e) {
            saga.compensate();
            throw Workflow.wrap(e);
        }
    }
}
