package com.example.testkingbasedata;

import org.springframework.data.jdbc.core.dialect.JdbcMySqlDialect;
import org.springframework.data.relational.core.dialect.LimitClause;
import org.springframework.data.relational.core.sql.IdentifierProcessing;

public class KingbaseMySqlDialect extends JdbcMySqlDialect {

    public static final KingbaseMySqlDialect INSTANCE = new KingbaseMySqlDialect();

    public KingbaseMySqlDialect() {
        super();
    }

    @Override
    public LimitClause limit() {
        return new MySqlLimitClause();
    }

    static class MySqlLimitClause implements LimitClause {

        @Override
        public String getLimit(long limit) {
            return " LIMIT " + limit;
        }

        @Override
        public String getOffset(long offset) {
            return " OFFSET " + offset;
        }

        @Override
        public String getLimitOffset(long limit, long offset) {
            return " LIMIT " + limit + " OFFSET " + offset;
        }

        @Override
        public Position getClausePosition() {
            return LimitClause.Position.AFTER_ORDER_BY;
        }
    }

    @Override
    public IdentifierProcessing getIdentifierProcessing() {
        return IdentifierProcessing.create(
                IdentifierProcessing.Quoting.ANSI,
                IdentifierProcessing.LetterCasing.AS_IS
        );
    }
}