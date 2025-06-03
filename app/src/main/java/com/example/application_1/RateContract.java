// com/example/application_1/RateContract.java
package com.example.application_1;

import android.provider.BaseColumns;

public final class RateContract {
    private RateContract() {}

    /** 汇率表 **/
    public static class RateEntry implements BaseColumns {
        public static final String TABLE_NAME = "rates";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATE = "rate";
    }

    /** 元数据表，用来存储最后一次更新日期 **/
    public static class MetaEntry implements BaseColumns {
        public static final String TABLE_NAME = "meta";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_VALUE = "value";
    }
}
