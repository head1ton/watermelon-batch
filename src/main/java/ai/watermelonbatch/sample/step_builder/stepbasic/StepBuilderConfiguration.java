package ai.watermelonbatch.sample.step_builder.stepbasic;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@RequiredArgsConstructor
public class StepBuilderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                                     .start(step1())
                                     .next(step2())
                                     .next(step3())
                                     .incrementer(new RunIdIncrementer())
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
                                 .<String, String>chunk(3)
                                 .reader(new ItemReader<String>() {
                                     @Override
                                     public String read()
                                         throws Exception {
                                         return null;
                                     }
                                 })
                                 .processor(new ItemProcessor<String, String>() {
                                     @Override
                                     public String process(final String item) throws Exception {
                                         return null;
                                     }
                                 })
                                 .writer(new ItemWriter<String>() {
                                     @Override
                                     public void write(final List<? extends String> items)
                                         throws Exception {

                                     }
                                 })
                                 .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                                 .partitioner(step1())
                                 .gridSize(2)
                                 .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                                 .job(job())
                                 .build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                                 .flow(flow())
                                 .build();
    }

    private Job job() {
        return this.jobBuilderFactory.get("job")
                                     .start(step1())
                                     .next(step2())
                                     .next(step3())
                                     .build();
    }

    private Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2()).end();
        return flowBuilder.build();
    }

}
