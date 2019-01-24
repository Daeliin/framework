package com.blebail.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public final class DbMemory implements BeforeAllCallback, AfterAllCallback {

    private static final String APPLICATION_PROPERTIES_PATH = "/application.properties";
    private static final String DB_SCHEMA_SQL_PATH = "/db_schema.sql";
    private static final String DATASOURCE_URL_PROPERTY_KEY = "spring.datasource.url";
    private static final String DATASOURCE_USERNAME_PROPERTY_KEY = "spring.datasource.username";
    private static final String DATASOURCE_PASSWORD_PROPERTY_KEY = "spring.datasource.password";

    private DataSource dataSource;

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES_PATH));

        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(properties.getProperty(DATASOURCE_URL_PROPERTY_KEY));
        jdbcDataSource.setUser(properties.getProperty(DATASOURCE_USERNAME_PROPERTY_KEY));
        jdbcDataSource.setPassword(properties.getProperty(DATASOURCE_PASSWORD_PROPERTY_KEY));

        dataSource = jdbcDataSource;

        String createSchemaSql = Files.readString(Paths.get(getClass().getResource(DB_SCHEMA_SQL_PATH).toURI()));

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), Operations.sql(createSchemaSql));
        dbSetup.launch();
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
