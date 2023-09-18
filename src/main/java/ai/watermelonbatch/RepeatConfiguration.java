package ai.watermelonbatch;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepeatConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                                .incrementer(new RunIdIncrementer())
                                .start(step1())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<String, String>chunk(5)
                                 .reader(new ItemReader<String>() {
                                     int i = 0;

                                     @Override
                                     public String read()
                                         throws Exception {
                                         i++;
                                         return i > 3 ? null : "item" + i;
                                     }
                                 })
                                 .processor(new ItemProcessor<String, String>() {
                                     final RepeatTemplate template = new RepeatTemplate();

                                     @Override
                                     public String process(final String item) throws Exception {
                                         template.setExceptionHandler(
                                             simpleLimitExceptionHandler());

                                         template.iterate(new RepeatCallback() {
                                             @Override
                                             public RepeatStatus doInIteration(
                                                 final RepeatContext context)
                                                 throws Exception {
                                                 System.out.println("repeatTest");
                                                 throw new RuntimeException(
                                                     "Exception is occurred");
                                             }
                                         });

                                         return item;
                                     }
                                 })
                                 .writer(new ItemWriter<String>() {
                                     @Override
                                     public void write(final List<? extends String> items)
                                         throws Exception {
                                         System.out.println(items);
                                     }
                                 })
                                 .build();
    }

    @Bean
    public SimpleLimitExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(5);
    }
}
