package ai.watermelonbatch.batch.chunk.processor;

import ai.watermelonbatch.batch.domain.Product;
import ai.watermelonbatch.batch.domain.ProductVo;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVo, Product> {

    @Override
    public Product process(final ProductVo item) throws Exception {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(item, Product.class);
    }
}
