package guru.qa.niffler.data.jdbc;

import guru.qa.niffler.data.DataBase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* Гарантированный синглтон, ленивая инициализация */
public enum DataSourceProvider {
    INSTANCE;

    private final Map<DataBase, DataSource> store = new ConcurrentHashMap<>();

    private DataSource computeDataSource(DataBase db){
        return store.computeIfAbsent(db, key -> {
            PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
            pgDataSource.setUrl(db.getJdbcUrl());
            pgDataSource.setUser("postgres");
            pgDataSource.setPassword("secret");
            return pgDataSource;
        });
    }

    public static DataSource dataSource(DataBase db){
        return INSTANCE.computeDataSource(db);
    }
}
