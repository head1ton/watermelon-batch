package ai.watermelonbatch;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements
    RowMapper<Customer> {

    @Override
    public Customer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new Customer(
            rs.getLong("id"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("birthDate"));
    }
}
