package me.packbag.android.db.api;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = Db.NAME, version = Db.VERSION, generatedClassSeparator = "_", foreignKeysSupported = true)
public class Db {

    public static final String NAME = "packback";

    public static final int VERSION = 4;
}
