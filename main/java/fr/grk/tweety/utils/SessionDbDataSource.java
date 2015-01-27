package fr.grk.tweety.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import fr.grk.tweety.model.Session;
import fr.grk.tweety.model.SessionDb;

/**
 * Created by grk on 27/01/15.
 */
public class SessionDbDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_HANDLE, MySQLiteHelper.COLUMN_TOKEN };


    public SessionDbDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public synchronized void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public synchronized void close() {
        dbHelper.close();
    }

    public synchronized long saveUserSession(String handle, String token) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_HANDLE, handle);
        values.put(MySQLiteHelper.COLUMN_TOKEN, token);
        long insertId = database.insert(MySQLiteHelper.TABLE_SESSION, null,
                values);
        return insertId;
    }


    //only one session in the db
    public synchronized SessionDb getUserSession(){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SESSION,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            return cursorToSession(cursor);
        }
        return null;
    }

    //disconnect
    public synchronized void deleteUserSession(){
        database.delete(MySQLiteHelper.TABLE_SESSION, null, null);
    }

    private SessionDb cursorToSession(Cursor cursor) {
        SessionDb sessionDb = new SessionDb();
        sessionDb.setId(cursor.getLong(0));
        sessionDb.setHandle(cursor.getString(1));
        sessionDb.setToken(cursor.getString(2));
        return sessionDb;
    }

}
