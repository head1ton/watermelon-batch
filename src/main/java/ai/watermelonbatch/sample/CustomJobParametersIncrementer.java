package ai.watermelonbatch.sample;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

public class CustomJobParametersIncrementer implements
    org.springframework.batch.core.JobParametersIncrementer {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    public JobParameters getNext(final JobParameters parameters) {

        String id = format.format(new Date());

        return new JobParametersBuilder()
            .addString("run.id", id)
            .toJobParameters();
    }
}
