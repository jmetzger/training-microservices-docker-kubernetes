package de.t3isp.saga;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SagaWorker {

    static final String TASK_QUEUE = "booking-saga";
    private static final Logger log = LoggerFactory.getLogger(SagaWorker.class);

    public static void main(String[] args) throws InterruptedException {
        String temporalHost = System.getenv().getOrDefault("TEMPORAL_HOST", "localhost");
        String target = temporalHost + ":7233";

        log.info("Verbinde mit Temporal unter {}", target);

        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
            WorkflowServiceStubsOptions.newBuilder()
                .setTarget(target)
                .build());

        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(BookingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new BookingActivitiesImpl());

        factory.start();
        log.info("Worker gestartet — Task Queue: {}", TASK_QUEUE);
        log.info("Warte auf Workflows...");

        Thread.currentThread().join();
    }
}
