package ai.watermelonbatch.sample.jobrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepositoryListener jobRepositoryListener;

    @Bean
    public Job BatchJob() {
        return this.jobBuilderFactory.get("batchJob")
                                     .start(step1())
                                     .next(step2())
                                     .listener(jobRepositoryListener)
                                     .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("Step1")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(
                                         final StepContribution contribution,
                                         final ChunkContext chunkContext) throws Exception {

                                         System.out.println("Step1 has executed");
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("Step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("Step2 has executed");
//                                         throw new RuntimeException("Step2 has failed");
                                     return null;
                                 })
                                 .build();
    }
}
