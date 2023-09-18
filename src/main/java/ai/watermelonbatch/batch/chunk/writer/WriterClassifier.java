package ai.watermelonbatch.batch.chunk.writer;

import ai.watermelonbatch.batch.domain.ApiRequestVo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

public class WriterClassifier<C, T> implements Classifier<C, T> {

    private Map<String, ItemWriter<ApiRequestVo>> writerMap = new HashMap<>();

    @Override
    public T classify(C classifiable) {
        return (T) writerMap.get(((ApiRequestVo) classifiable).getProductVo().getType());
    }

    public void setWriterMap(Map<String, ItemWriter<ApiRequestVo>> writerMap) {
        this.writerMap = writerMap;
    }
}
