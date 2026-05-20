package de.t3isp.saga;

public class Main {
    public static void main(String[] args) throws Exception {
        String mode = args.length > 0 ? args[0] : "worker";
        switch (mode) {
            case "worker"  -> SagaWorker.main(args);
            case "starter" -> SagaStarter.main(args);
            default        -> System.err.println("Unbekannter Modus: " + mode + " (worker|starter)");
        }
    }
}
