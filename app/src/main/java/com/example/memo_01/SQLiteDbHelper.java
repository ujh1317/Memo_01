package com.example.memo_01;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteDbHelper {

    // DB 관련 상수
    private static final String dbName = "my Memo";
    private static final String table1 = "MemoTable";
    private static final int dbVersion = 1;

    // DB 관련 객체
    private OpenHelper opener;
    private SQLiteDatabase db;

    // 부가적인 객체
    private Context context;

    public SQLiteDbHelper(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // table 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create = "CREATE TABLE " + table1 + "(" +
                    "seq integer PRIMARY KEY AUTOINCREMENT," +
                    "maintext text," +
                    "subtext text," +
                    "isdone integer)";
            sqLiteDatabase.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table1);
            onCreate(sqLiteDatabase);
        }
    }

    // INSERT INTO MemoTable VALUES(NULL, 'MAINTEXT', 'SUBTEXT', 0);
    public void insertMemo(Memo memo) {
        String sql = "INSERT INTO " + table1 + "VALUES(NULL, '" + memo.maintext + "','" + memo.subtext + "'," + memo.getIsdone() + ");";
        db.execSQL(sql);
    }

    //DELETE FROM MemoTable WHERE seq = 0;
    public void deleteMemo(int position) {
        String sql = "DELETE FROM " + table1 + " WHERE seq = " + position + ";";
        db.execSQL(sql);
    }

    // SELECT * FROM MemoTable;
    public ArrayList<Memo> selectAll() {
        String sql = "SELECT * FROM " + table1;

        ArrayList<Memo> list = new ArrayList<>();

        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {

            Memo memo = new Memo(results.getInt(0), results.getString(1), results.getString(2), results.getInt(3));
            list.add(memo);
            results.moveToNext();
        }
        results.close();
        return list;
    }
}