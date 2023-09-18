package ai.watermelonbatch.batch.chunk.writer;

import ai.watermelonbatch.batch.domain.ApiRequestVo;
import ai.watermelonbatch.batch.domain.ApiResponseVo;
import ai.watermelonbatch.service.AbstractApiService;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class ApiItemWriter2 extends FlatFileItemWriter<ApiRequestVo> {

    private final AbstractApiService apiService;

    public ApiItemWriter2(final AbstractApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void write(final List<? extends ApiRequestVo> items) throws Exception {
        System.out.println("----------------------------------");
        items.forEach(item -> System.out.println("items = " + item));
        System.out.println("----------------------------------");

        ApiResponseVo response = apiService.service(items);
        System.out.println("response = " + response);

        items.forEach(item -> item.setApiResponseVo(response));

        super.setResource(new FileSystemResource("/Users/apple/Downloads/product2.txt"));
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());
        super.setAppendAllowed(true);
        super.write(items);
    }
}
