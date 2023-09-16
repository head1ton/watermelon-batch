package ai.watermelonbatch.sample.scope;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class JobScope_StepScope_Configuration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        System.out.println(getClass().getName());
        return jobBuilderFactory.get("batchJob")
                                .start(step1(null))
                                .next(step2())
                                .listener(new JobListener())
                                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters['message']}") String message) {
        System.out.println("message = " + message);
        System.out.println(getClass().getName());
        return stepBuilderFactory.get("step1")
                                 .tasklet(tasklet1(null, null))
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet(tasklet1(null, null))
                                 .listener(new CustomStepListener())
                                 .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name']}") String name,
        @Value("#{stepExecutionContext['name2']}") String name2) {
        System.out.println("name = " + name);
        System.out.println("name2 = " + name2);
        return (contribution, chunkContext) -> {
            System.out.println("tasklet1 has executed");
            return RepeatStatus.FINISHED;
        };
    }

    public static class JobListener implements JobExecutionListener {

        @Override
        public void beforeJob(final JobExecution jobExecution) {
            jobExecution.getExecutionContext().putString("name", "user1");
        }

        @Override
        public void afterJob(final JobExecution jobExecution) {

        }
    }

    public static class CustomStepListener implements StepExecutionListener {

        @Override
        public void beforeStep(final StepExecution stepExecution) {
            stepExecution.getExecutionContext().putString("name2", "user2");
        }

        @Override
        public ExitStatus afterStep(final StepExecution stepExecution) {
            return null;
        }
    }
}
