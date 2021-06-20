package publisher;

import publisher.domain.JsonMessage;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {
        sendJsons();
    }

    public static void sendJsons() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        for (int i = 0; i < 5; i++) {
            executor.scheduleAtFixedRate(new JsonMessage(), 0, 15, TimeUnit.SECONDS);
        }
    }
}
