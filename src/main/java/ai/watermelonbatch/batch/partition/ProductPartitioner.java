package ai.watermelonbatch.batch.partition;

import ai.watermelonbatch.batch.domain.ProductVo;
import ai.watermelonbatch.batch.job.api.QueryGenerator;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(final int gridSize) {
        ProductVo[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;

        for (int i = 0; i < productList.length; i++) {

            ExecutionContext value = new ExecutionContext();

            result.put("partition" + number, value);
            value.put("product", productList[i]);

            number++;
        }

        return result;
    }
}
