package ai.watermelonbatch;

public class CustomItemProcessor2 implements
    org.springframework.batch.item.ItemProcessor<ProcessorInfo, ProcessorInfo> {

    @Override
    public ProcessorInfo process(final ProcessorInfo item) throws Exception {
        System.out.println("CustomItemProcessor2");
        return item;
    }
}
