package ai.watermelonbatch.batch.job.api;

import ai.watermelonbatch.batch.domain.ProductVo;
import ai.watermelonbatch.batch.rowmapper.ProductRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class QueryGenerator {

    public static ProductVo[] getProductList(DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductVo> productList = jdbcTemplate.query(
            "select type as type from product group by type",
            new ProductRowMapper() {
                @Override
                public ProductVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                    return ProductVo.builder().type(rs.getString("type")).build();
                }
            });

        return productList.toArray(new ProductVo[]{});
    }

    public static Map<String, Object> getParameterForQuery(final String parameter,
        final String value) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(parameter, value);
        return parameters;
    }
}
