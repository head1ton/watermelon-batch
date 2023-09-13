package ai.watermelonbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class WatermelonBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatermelonBatchApplication.class, args);
    }

}
