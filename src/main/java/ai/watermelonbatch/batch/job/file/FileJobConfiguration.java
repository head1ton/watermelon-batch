package ai.watermelonbatch.batch.job.file;

import ai.watermelonbatch.batch.chunk.processor.FileItemProcessor;
import ai.watermelonbatch.batch.domain.Product;
import ai.watermelonbatch.batch.domain.ProductVo;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                                .start(fileStep())
                                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep1")
                                 .<ProductVo, Product>chunk(10)
                                 .reader(fileItemReader(null))
                                 .processor(fileItemProcessor())
                                 .writer(fileItemWriter())
                                 .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProductVo> fileItemReader(
        @Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVo>()
            .name("flatFile")
            .resource(new ClassPathResource("product_" + requestDate + ".csv"))
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
            .targetType(ProductVo.class)
            .linesToSkip(1)
            .delimited().delimiter(",")
            .names("id", "name", "price", "type")
            .build();
    }

    @Bean
    public ItemProcessor<ProductVo, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
            .entityManagerFactory(entityManagerFactory)
            .usePersist(true)
            .build();
    }


}
