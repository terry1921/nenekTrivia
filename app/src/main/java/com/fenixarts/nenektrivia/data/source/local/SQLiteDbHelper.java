package com.fenixarts.nenektrivia.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * NenekTrivia
 * Created by terry0022 on 24/01/18 - 12:15.
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "NenekTrivia.db";

    private static final String CREATE_TABLE = "CREATE TABLE ";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_BEST_PLAYERS =
            CREATE_TABLE + PersistenceContract.BestPlayer.TABLE_NAME + " (" +
                    PersistenceContract.BestPlayer._ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    PersistenceContract.BestPlayer.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.BestPlayer.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.BestPlayer.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.BestPlayer.COLUMN_NAME_POINTS + INTEGER_TYPE +
                    " )";

    private static final String SQL_CREATE_QUESTIONS =
            CREATE_TABLE + PersistenceContract.Questions.TABLE_NAME + " (" +
                    PersistenceContract.Questions._ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_ANSWER_BAD_01 + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_ANSWER_BAD_02 + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_ANSWER_BAD_03 + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_ANSWER_GOOD + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.Questions.COLUMN_NAME_QUESTION + TEXT_TYPE + " )";

    SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BEST_PLAYERS);
        db.execSQL(SQL_CREATE_QUESTIONS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
        if (newVersion > oldVersion){
            /* borramos tablas antiguas */
            db.execSQL(DROP_TABLE + PersistenceContract.BestPlayer.TABLE_NAME);
            db.execSQL(DROP_TABLE + PersistenceContract.Questions.TABLE_NAME);

            /* creamos tablas nuevas */
            db.execSQL(SQL_CREATE_BEST_PLAYERS);
            db.execSQL(SQL_CREATE_QUESTIONS);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
