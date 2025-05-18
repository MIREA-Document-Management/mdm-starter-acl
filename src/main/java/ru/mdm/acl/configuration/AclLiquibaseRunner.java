package ru.mdm.acl.configuration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Liquibase executor
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AclLiquibaseRunner implements ApplicationListener<ApplicationStartedEvent> {

    private static final String JDBC_URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s?currentSchema=%s";
    private static final String DB_CHANGELOG_FILE = "db/acl-db.changelog-master.xml";

    private final AclDatabaseProperties databaseProperties;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        String schema =
                StringUtils.isBlank(databaseProperties.getSchema()) ? "public" : databaseProperties.getSchema();
        String url = String.format(JDBC_URL_TEMPLATE, databaseProperties.getHost(), databaseProperties.getPort(),
                databaseProperties.getName(), schema);
        Database database = null;
        Boolean autocommit = null;

        try (
                Connection connection = DriverManager.getConnection(url, databaseProperties.getUser(),
                        databaseProperties.getPassword());
                JdbcConnection connection1 = new JdbcConnection(connection)){
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection1);
            database.setLiquibaseSchemaName(databaseProperties.getSchema());
            autocommit = database.getAutoCommitMode();
            try (var liquibase = new Liquibase(DB_CHANGELOG_FILE, new ClassLoaderResourceAccessor(), database)) {
                liquibase.update("default");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (database != null) {
                try {
                    if (autocommit != null) {
                        database.setAutoCommit(autocommit);
                    }
                    database.close();
                } catch (DatabaseException e) {
                    // ignore
                }
            }
        }
    }
}
