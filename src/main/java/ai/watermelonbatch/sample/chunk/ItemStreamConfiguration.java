package ai.watermelonbatch.sample.chunk;

import ai.watermelonbatch.sample.CustomJobParametersIncrementer;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
//@Configuration
public class ItemStreamConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
//                                .incrementer(new CustomJobParametersIncrementer())
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<String, String>chunk(5)
                                 .reader(itemReader())
                                 .writer(itemWriter())
                                 .build();
    }

    @Bean
    public CustomItemStreamReader itemReader() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            items.add(String.valueOf(i));
        }
        return new CustomItemStreamReader(items);
    }

    @Bean
    public ItemWriter<? super String> itemWriter() {
        return new CustomItemStreamWriter();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("step2 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    public static class CustomItemStreamReader implements ItemStreamReader<String> {

        private final List<String> items;
        private int index = -1;
        private boolean restart = false;

        public CustomItemStreamReader(List<String> items) {
            this.items = items;
            this.index = 0;
        }

        @Override
        public String read()
            throws Exception {

            String item = null;
            if (this.index < this.items.size()) {
                item = this.items.get(index);
                this.index++;
            }

            if (this.index == 6 && !restart) {
                System.out.println("index = " + index + " restart = " + restart);
                throw new RuntimeException("Restart is required");
            }

            return item;
        }

        @Override
        public void open(ExecutionContext executionContext) throws ItemStreamException {
            if (executionContext.containsKey("index")) {
                index = executionContext.getInt("index");
                System.out.println("index = " + index);
                this.restart = true;
            } else {
                index = 0;
                System.out.println("index = " + index);
                executionContext.put("index", index);
            }
        }

        @Override
        public void update(final ExecutionContext executionContext) throws ItemStreamException {
            executionContext.put("index", index);
        }

        @Override
        public void close() throws ItemStreamException {
            System.out.println("reader close");
        }
    }

    private static class CustomItemStreamWriter implements ItemStreamWriter<String> {

        @Override
        public void open(final ExecutionContext executionContext) throws ItemStreamException {
            System.out.println("writer open");
        }

        @Override
        public void update(final ExecutionContext executionContext) throws ItemStreamException {
            System.out.println("writer update");
        }

        @Override
        public void close() throws ItemStreamException {
            System.out.println("writer close");
        }

        @Override
        public void write(final List<? extends String> items) throws Exception {
            items.forEach(System.out::println);
        }
    }
}
