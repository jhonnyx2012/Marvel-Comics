package jhonny.marvelcomics;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MarvelDB.NAME, version = MarvelDB.VERSION)
public class MarvelDB {
    static final String NAME = "MarvelDB";
    public static final int VERSION = 2;
}