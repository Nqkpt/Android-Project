package com.example.btl.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl.model.Item;
import com.example.btl.model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "b20dcat103.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ITEMS = "items";

    // Users table columns
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    // Items table columns
    public static final String ITEM_ID = "id";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_DESCRIPTION = "des";
    public static final String ITEM_TIME = "time";
    public static final String ITEM_DATE = "date";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT, " +
                USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);

        // Create items table
        String createItemTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_NAME + " TEXT, " +
                ITEM_DESCRIPTION + " TEXT, " +
                ITEM_TIME + " TEXT, " +
                ITEM_DATE + " TEXT)";
        db.execSQL(createItemTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = db.query(TABLE_ITEMS, null, null, null, null, null, order);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String des = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, name, des, time, date));
        }
        return list;
    }

    public long addItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getName());
        values.put(ITEM_DESCRIPTION, item.getDes());
        values.put(ITEM_TIME, item.getTime());
        values.put(ITEM_DATE, item.getDate());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_ITEMS, null, values);
    }

    public List<Item> getItemsByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date LIKE ?";
        String[] whereArgs = {date};
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query(TABLE_ITEMS, null, whereClause, whereArgs, null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String des = rs.getString(2);
            String time = rs.getString(3);
            list.add(new Item(id, name, des, time, date));
        }
        return list;
    }

    public int updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getName());
        values.put(ITEM_DESCRIPTION, item.getDes());
        values.put(ITEM_TIME, item.getTime());
        values.put(ITEM_DATE, item.getDate());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(item.getId())};
        return db.update(TABLE_ITEMS, values, whereClause, whereArgs);
    }

    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_ITEMS, whereClause, whereArgs);
    }

    public List<Item> searchItemsByName(String key) {
        List<Item> list = new ArrayList<>();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query(TABLE_ITEMS, null, whereClause, whereArgs, null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String des = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, name, des, time, date));
        }
        return list;
    }

    public List<Item> searchItemsByDes(String des) {
        List<Item> list = new ArrayList<>();
        String whereClause = "des LIKE ?";
        String[] whereArgs = {"%" + des + "%"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query(TABLE_ITEMS, null, whereClause, whereArgs, null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, name, description, time, date));
        }
        return list;
    }

    public List<Item> searchItemsByDateRange(String from, String to) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(), to.trim()};
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query(TABLE_ITEMS, null, whereClause, whereArgs, null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String des = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, name, des, time, date));
        }
        return list;
    }

    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_USERS, null, values);
    }

    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(user.getId())};
        return db.update(TABLE_USERS, values, whereClause, whereArgs);
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, USER_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(USER_PASSWORD));
            cursor.close();
            return new User(id, name, userEmail, password);
        }
        return null;
    }


}
