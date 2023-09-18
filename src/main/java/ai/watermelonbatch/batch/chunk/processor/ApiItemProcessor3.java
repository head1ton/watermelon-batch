package ai.watermelonbatch.batch.chunk.processor;

import ai.watermelonbatch.batch.domain.ApiRequestVo;
import ai.watermelonbatch.batch.domain.ProductVo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ApiItemProcessor3 implements
    ItemProcessor<ProductVo, ApiRequestVo> {

    @Override
    public ApiRequestVo process(final ProductVo productVo) throws Exception {
        return ApiRequestVo.builder()
                           .id(productVo.getId())
                           .productVo(productVo)
                           .build();
    }
}
