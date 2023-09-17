package ai.watermelonbatch.sample.chunk;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class CustomItemWriter implements ItemWriter<Customer> {

    @Override
    public void write(final List<? extends Customer> items) throws Exception {
        items.forEach(System.out::println);
    }
}
