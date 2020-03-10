package com.blebail.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public final class SqlMemoryDatabase implements BeforeAllCallback, AfterAllCallback {

    private static final String APPLICATION_PROPERTIES_PATH = "/application.properties";

    private static final String DB_SCHEMA_SQL_PATH = "/db_schema.sql";

    private static final String DATASOURCE_PROPERTY_PREFIX = "spring.datasource.";

    private DataSource dataSource;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        loadDataSource();
        createSchema();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        closeConnection();
    }

    private void loadDataSource() throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES_PATH));

        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(properties.getProperty(DATASOURCE_PROPERTY_PREFIX + "url"));
        jdbcDataSource.setUser(properties.getProperty(DATASOURCE_PROPERTY_PREFIX + "username"));
        jdbcDataSource.setPassword(properties.getProperty(DATASOURCE_PROPERTY_PREFIX + "password"));

        dataSource = jdbcDataSource;
    }

    private void createSchema() throws IOException, URISyntaxException {
        Path schemaPath = Paths.get(getClass().getResource(DB_SCHEMA_SQL_PATH).toURI());
        String createSchemaSql = Files.readString(schemaPath);

        new DbSetup(new DataSourceDestination(dataSource), Operations.sql(createSchemaSql)).launch();
    }

    private void closeConnection() {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSource dataSource() {
        return dataSource;
    }

    public int countRows(String tableName) throws Exception {
        ResultSet resultSet = dataSource.getConnection()
                .createStatement()
                .executeQuery("SELECT COUNT(*) AS rowCount FROM " + tableName);

        resultSet.next();
        return resultSet.getInt("rowCount");
    }
}
