package publisher.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@ToString
@JsonIgnoreProperties
@Getter
public class JsonMessage implements Runnable {
    @JsonIgnore
    private static volatile Long idCounter = 0L;
    @JsonIgnore
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    @JsonIgnore
    private static final String URL = "http://localhost:9090";
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Long id;
    private Long msisdn;
    private String action;
    private Long timestamp;

    @Override
    public void run() {
        synchronized (JsonMessage.class) {
            this.id = ++idCounter;
        }
        this.msisdn = new Random().nextLong();
        this.action = Action.getRandomAction().toString();
        this.timestamp = System.currentTimeMillis() / 1000L;
        logger.info("Send : " + this);
        ResponseEntity<JsonMessage> responseEntity = REST_TEMPLATE.postForEntity(URL, this, JsonMessage.class);
        logger.info("Required : " + responseEntity.getBody());
    }

    private enum Action {
        PURCHASE,
        SUBSCRIPTION;

        public static Action getRandomAction() {
            return Action.values()[new Random().nextInt(2)];
        }
    }
}
