package com.example.testkingbasedata;

import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.data.relational.core.dialect.LimitClause;
import org.springframework.data.relational.core.dialect.PostgresDialect;
import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.data.relational.core.sql.LockOptions;

public class KingbaseOracleDialect extends JdbcPostgresDialect {

    public static final KingbaseOracleDialect INSTANCE = new KingbaseOracleDialect();

    @Override
    public LimitClause limit() {
        return new OracleLimitClause();
    }

    static class OracleLimitClause implements LimitClause {

        @Override
        public String getLimit(long limit) {
            return " FETCH FIRST " + limit + " ROWS ONLY";
        }

        @Override
        public String getOffset(long offset) {
            return " OFFSET " + offset + " ROWS";
        }

        @Override
        public String getLimitOffset(long limit, long offset) {
            return " OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY";
        }

        @Override
        public Position getClausePosition() {
            return Position.AFTER_ORDER_BY;
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