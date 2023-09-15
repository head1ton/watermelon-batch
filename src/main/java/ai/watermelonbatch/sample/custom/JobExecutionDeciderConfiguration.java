package ai.watermelonbatch.sample.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobExecutionDeciderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                                .incrementer(new RunIdIncrementer())
                                .start(step())
                                .next(decider())
                                .from(decider())
                                .on("ODD").to(oddStep())
                                .from(decider())
                                .on("EVEN").to(evenStep())
                                .end()
                                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new CustomDecider();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("startStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("This is the start tasklet");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("This is the start tasklet");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println(">> oddStep has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }


}
