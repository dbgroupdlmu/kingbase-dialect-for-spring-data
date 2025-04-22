package com.example.testkingbasedata.dialect;

import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.data.relational.core.dialect.LimitClause;
import org.springframework.data.relational.core.sql.IdentifierProcessing;

public class KingbasePgDialect extends JdbcPostgresDialect {

    public static final KingbasePgDialect INSTANCE = new KingbasePgDialect();

    @Override
    public LimitClause limit() {
        return new KingbasePgDialect.PgLimitClause();
    }

    static class PgLimitClause implements LimitClause {

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