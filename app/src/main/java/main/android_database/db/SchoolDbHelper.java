package main.android_database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aluno on 19/10/2016.
 */
public class SchoolDbHelper extends SQLiteOpenHelper {

    public SchoolDbHelper(Context context ) {
        super(context, SchoolContract.DATABASE_NAME, null, SchoolContract.DATABASE_VERSION);

    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SchoolContract.SQL_CREATE_STUDENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SchoolContract.SQL_DELETE_STUDENT);
        this.onCreate(db);
    }
}
