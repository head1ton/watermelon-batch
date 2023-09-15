package ai.watermelonbatch.sample.job_batch.simplejob;

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

//@Configuration
@RequiredArgsConstructor
public class IncrementerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                                     .start(step1())
                                     .next(step2())
                                     .next(step3())
                                     .incrementer(new CustomJobParametersIncrementer())
                                     .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(StepContribution contribution,
                                         ChunkContext chunkContext) throws Exception {
                                         System.out.println("step1 has executed");
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
//                                     throw new RuntimeException("step2 was failed");
                                     System.out.println("step2 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(StepContribution contribution,
                                         ChunkContext chunkContext) throws Exception {
//                                         chunkContext.getStepContext().getStepExecution().setStatus(
//                                             BatchStatus.FAILED);
//                                         contribution.setExitStatus(ExitStatus.STOPPED);

                                         System.out.println("step3 has executed");
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }

}
