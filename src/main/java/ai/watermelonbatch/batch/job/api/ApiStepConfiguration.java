package ai.watermelonbatch.batch.job.api;

import ai.watermelonbatch.batch.chunk.processor.ApiItemProcessor1;
import ai.watermelonbatch.batch.chunk.processor.ApiItemProcessor2;
import ai.watermelonbatch.batch.chunk.processor.ApiItemProcessor3;
import ai.watermelonbatch.batch.chunk.processor.ProcessorClassifier;
import ai.watermelonbatch.batch.chunk.writer.ApiItemWriter1;
import ai.watermelonbatch.batch.chunk.writer.ApiItemWriter2;
import ai.watermelonbatch.batch.chunk.writer.ApiItemWriter3;
import ai.watermelonbatch.batch.chunk.writer.WriterClassifier;
import ai.watermelonbatch.batch.domain.ApiRequestVo;
import ai.watermelonbatch.batch.domain.ProductVo;
import ai.watermelonbatch.batch.partition.ProductPartitioner;
import ai.watermelonbatch.service.ApiService1;
import ai.watermelonbatch.service.ApiService2;
import ai.watermelonbatch.service.ApiService3;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class ApiStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private final int chunkSize = 10;

    @Bean
    public Step apiMasterStep() throws Exception {
        return stepBuilderFactory.get("apiMasterStep")
                                 .partitioner(apiSlaveStep().getName(), partitioner())
                                 .step(apiSlaveStep())
                                 .gridSize(3)
                                 .taskExecutor(taskExecutor())
                                 .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(6);
        taskExecutor.setThreadNamePrefix("api-thread-");

        return taskExecutor;
    }

    @Bean
    public ProductPartitioner partitioner() {
        ProductPartitioner productPartitioner = new ProductPartitioner();
        productPartitioner.setDataSource(dataSource);
        return productPartitioner;
    }

    @Bean
    public Step apiSlaveStep() throws Exception {
        return stepBuilderFactory.get("apiSlaveStep")
                                 .<ProductVo, ProductVo>chunk(chunkSize)
                                 .reader(itemReader(null))
                                 .processor(itemProcessor())
                                 .writer(itemWriter())
                                 .build();
    }

    @Bean
    @StepScope
    public ItemReader<? extends ProductVo> itemReader(
        @Value("#{stepExecutionContext['product']}") ProductVo productVo) throws Exception {

        JdbcPagingItemReader<ProductVo> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(dataSource);
        reader.setPageSize(chunkSize);
        reader.setRowMapper(new BeanPropertyRowMapper<>(ProductVo.class));

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, name, price, type");
        queryProvider.setFromClause("from product");
        queryProvider.setWhereClause("where type = :type");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.DESCENDING);
        queryProvider.setSortKeys(sortKeys);

        reader.setParameterValues(QueryGenerator.getParameterForQuery("type", productVo.getType()));
        reader.setQueryProvider(queryProvider);
        reader.afterPropertiesSet();

        return reader;
    }

    @Bean
    public ItemProcessor itemProcessor() {
        ClassifierCompositeItemProcessor<ProductVo, ApiRequestVo> processor = new ClassifierCompositeItemProcessor<>();
        ProcessorClassifier<ProductVo, ItemProcessor<?, ? extends ApiRequestVo>> classifier = new ProcessorClassifier();

        Map<String, ItemProcessor<ProductVo, ApiRequestVo>> processorMap = new HashMap<>();
        processorMap.put("1", new ApiItemProcessor1());
        processorMap.put("2", new ApiItemProcessor2());
        processorMap.put("3", new ApiItemProcessor3());

        classifier.setProcessorMap(processorMap);

        processor.setClassifier(classifier);

        return processor;
    }

    @Bean
    public ItemWriter itemWriter() {
        ClassifierCompositeItemWriter<ApiRequestVo> writer = new ClassifierCompositeItemWriter<>();
        WriterClassifier<ApiRequestVo, ItemWriter<? super ApiRequestVo>> classifier = new WriterClassifier();

        Map<String, ItemWriter<ApiRequestVo>> writerMap = new HashMap<>();
        writerMap.put("1", new ApiItemWriter1(new ApiService1()));
        writerMap.put("2", new ApiItemWriter2(new ApiService2()));
        writerMap.put("3", new ApiItemWriter3(new ApiService3()));

        classifier.setWriterMap(writerMap);

        writer.setClassifier(classifier);

        return writer;
    }
}
