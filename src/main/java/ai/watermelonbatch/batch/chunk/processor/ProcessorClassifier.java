package ai.watermelonbatch.batch.chunk.processor;

import ai.watermelonbatch.batch.domain.ApiRequestVo;
import ai.watermelonbatch.batch.domain.ProductVo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    private Map<String, ItemProcessor<ProductVo, ApiRequestVo>> processorMap = new HashMap<>();

    @Override
    public T classify(C classifiable) {
        return (T) processorMap.get(((ProductVo) classifiable).getType());
    }

    public void setProcessorMap(
        final Map<String, ItemProcessor<ProductVo, ApiRequestVo>> processorMap) {
        this.processorMap = processorMap;
    }
}
