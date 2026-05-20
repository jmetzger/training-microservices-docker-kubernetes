package de.t3isp.saga;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BookingWorkflow {
    @WorkflowMethod
    String bookTrip(String bookingId, double amount);
}
