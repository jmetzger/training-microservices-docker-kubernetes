package de.t3isp.saga;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SagaStarter {

    private static final Logger log = LoggerFactory.getLogger(SagaStarter.class);

    public static void main(String[] args) throws InterruptedException {
        String temporalHost = System.getenv().getOrDefault("TEMPORAL_HOST", "localhost");
        String target = temporalHost + ":7233";

        log.info("Warte 10s auf Worker-Start...");
        Thread.sleep(10_000);

        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
            WorkflowServiceStubsOptions.newBuilder()
                .setTarget(target)
                .build());

        WorkflowClient client = WorkflowClient.newInstance(service);

        // --- Erfolgsfall: 500 EUR ---
        log.info("=== Fall 1: Erfolgsfall (500 EUR) ===");
        BookingWorkflow w1 = client.newWorkflowStub(
            BookingWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue(SagaWorker.TASK_QUEUE)
                .setWorkflowId("booking-success-001")
                .build());
        try {
            String result = w1.bookTrip("booking-001", 500.0);
            log.info("ERGEBNIS: {}", result);
        } catch (WorkflowException e) {
            log.error("FEHLGESCHLAGEN (unerwartet): {}", e.getMessage());
        }

        Thread.sleep(2_000);

        // --- Fehlerfall: 2000 EUR ---
        log.info("=== Fall 2: Fehlerfall (2000 EUR) — Kompensationen werden erwartet ===");
        BookingWorkflow w2 = client.newWorkflowStub(
            BookingWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue(SagaWorker.TASK_QUEUE)
                .setWorkflowId("booking-failure-001")
                .build());
        try {
            w2.bookTrip("booking-002", 2000.0);
            log.error("ERGEBNIS: Haette fehlschlagen sollen!");
        } catch (WorkflowException e) {
            log.info("ERGEBNIS: Workflow fehlgeschlagen (erwartet) — Kompensationen gelaufen");
            log.info("Grund: {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        }

        log.info("Fertig. Web-UI: http://{}:8233", temporalHost.equals("localhost") ? "localhost" : "SERVER_IP");
    }
}
