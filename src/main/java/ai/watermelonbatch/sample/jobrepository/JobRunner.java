package ai.watermelonbatch.sample.jobrepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "batchJob")
            .addDate("requestDate", new SimpleDateFormat("yyyy/MM/dd").parse("2020/01/04"))
            .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
