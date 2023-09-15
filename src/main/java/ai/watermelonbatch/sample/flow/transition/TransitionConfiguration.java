package ai.watermelonbatch.sample.flow.transition;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
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
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class TransitionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @Primary
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                                     .start(step1())
                                     .on("FAILED").to(step2())
                                     .on("FAILED").stop()
                                     .from(step1())
                                     .on("*").to(step3())
                                     .next(step4())
                                     .from(step2())
                                     .on("*").to(step5())
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

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
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

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
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

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
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

