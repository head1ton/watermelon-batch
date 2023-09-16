package ai.watermelonbatch.sample.flow.batchstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//@Configuration
@RequiredArgsConstructor
public class BatchStatusExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
//    @Primary
//    public Job batchJob() {
//        return this.jobBuilderFactory.get("batchJob")
//                                     .start(step1())
//                                     .next(step2())
//                                     .build();
//    }

    @Bean
    @Primary
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                                     .start(step1())
                                     .on("FAILED").to(step2())
                                     .end()
                                     .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(
                                         final StepContribution contribution,
                                         final ChunkContext chunkContext) throws Exception {
//                                         throw new RuntimeException("step1 was failed");
                                         contribution.setExitStatus(ExitStatus.FAILED);
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(
                                         final StepContribution contribution,
                                         final ChunkContext chunkContext) throws Exception {
//                    throw new RuntimeException("step1 was failed");
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }


}
