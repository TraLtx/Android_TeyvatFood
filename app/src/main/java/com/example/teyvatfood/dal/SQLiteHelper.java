package com.example.teyvatfood.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.teyvatfood.model.Account;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String name = "account.tevatfood";
    private static int version = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE account(" +
                "id INTEGER PRIMARY KEY," +
                "username TEXT," +
                "password TEXT)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //ADD = Login
    public long loginSuccess(Account account){

        ContentValues values = new ContentValues();
        values.put("id", account.getId());
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        SQLiteDatabase db = getWritableDatabase();

        return db.insert("account",null,values);
    }

    //GET
    public Account getAccount(){
        Account acc = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("account",null,null,null,null,null,null);
        if(cursor.moveToNext() && cursor != null){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);

            acc = new Account(id, username,password);
        }

        return acc;
    }

    //DELETE = logout
    public int logout(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};

        SQLiteDatabase db = getWritableDatabase();
        return db.delete("account", whereClause,whereArgs);
    }
}
