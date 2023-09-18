package ai.watermelonbatch;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(final String item) throws Exception {
        cnt++;
        return item + cnt;
    }
}
