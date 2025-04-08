package com.example.testkingbasedata;

import org.springframework.data.relational.core.dialect.LimitClause;
import org.springframework.data.relational.core.dialect.SqlServerDialect;
import org.springframework.data.relational.core.sql.LockOptions;

public class KingbaseSqlServerDialect extends SqlServerDialect {

    public static final KingbaseSqlServerDialect INSTANCE = new KingbaseSqlServerDialect();

    @Override
    public LimitClause limit() {
        return new SqlServerLimitClause();
    }

    static class SqlServerLimitClause implements LimitClause {

        @Override
        public String getLimit(long limit) {
            return "";
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
            return LimitClause.Position.AFTER_ORDER_BY;
        }
    }
}