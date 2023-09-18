package ai.watermelonbatch.batch.rowmapper;

import ai.watermelonbatch.batch.domain.ProductVo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<ProductVo> {

    @Override
    public ProductVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return ProductVo.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .price(rs.getInt("price"))
                        .type(rs.getString("type"))
                        .build();
    }
}
