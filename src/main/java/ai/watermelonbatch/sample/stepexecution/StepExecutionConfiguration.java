package ai.watermelonbatch.sample.stepexecution;

import ai.watermelonbatch.sample.step.CustomTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepExecutionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job BatchJob() {
        return this.jobBuilderFactory.get("Job")
                                     .start(step1())
                                     .next(step2())
                                     .next(step3())
                                     .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("Step1")
                                 .tasklet((contribution, chunkContext) -> {

                                     System.out.println("Step1 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("Step2")
                                 .tasklet((contribution, chunkContext) -> {

                                     System.out.println("Step2 has executed");
//                                     throw new RuntimeException("Step2 has failed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    private Step step3() {
        return stepBuilderFactory.get("Step3")
                                 .tasklet((contribution, chunkContext) -> {

                                     System.out.println("Step3 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
}
