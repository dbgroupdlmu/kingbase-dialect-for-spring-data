package com.example.testkingbasedata;

import org.springframework.data.jdbc.core.dialect.JdbcMySqlDialect;
import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.data.jdbc.core.dialect.JdbcSqlServerDialect;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class KingbaseESDialectResolver implements DialectResolver.JdbcDialectProvider {

    @Override
    public Optional<Dialect> getDialect(JdbcOperations operations) {
        // 使用 ConnectionCallback 执行 SHOW DATABASE_MODE 查询
        return Optional.ofNullable(
                operations.execute((ConnectionCallback<Dialect>) this::determineDialect)
        );
    }

    private Dialect determineDialect(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW DATABASE_MODE")) {

            if (resultSet.next()) {
                String databaseMode = resultSet.getString(1).toLowerCase();

                // 根据兼容模式选择对应的方言
                switch (databaseMode) {
                    case "sqlserver":
                        return KingbaseSqlServerDialect.INSTANCE; // Sqlserver 兼容模式
                    case "oracle":
                        return KingbaseOracleDialect.INSTANCE; // Oracle 兼容模式
                    case "mysql":
                        return KingbaseMySqlDialect.INSTANCE; // MySQL 兼容模式
                    default:
                        throw new IllegalStateException("Unsupported Kingbase8 mode: " + databaseMode);
                }
            } else {
                throw new IllegalStateException("Failed to retrieve Kingbase8 mode");
            }
        } catch (SQLException e) {
            // 如果 SHOW DATABASE_MODE 不支持，可能是其他数据库，抛出异常或返回默认方言
            throw new RuntimeException("Error determining Kingbase8 mode", e);
        }
    }
}