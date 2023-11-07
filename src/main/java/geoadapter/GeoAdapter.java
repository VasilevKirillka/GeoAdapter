package geoadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class GeoAdapter {
    public static void main(String[] args) {
            SpringApplication.run(GeoAdapter.class, args);
    }
}