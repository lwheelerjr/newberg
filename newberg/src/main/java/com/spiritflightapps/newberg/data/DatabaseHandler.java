package com.spiritflightapps.newberg.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "stocksManager";

    private static final String TABLE_STOCKS = "stocks";

    private static final String KEY_ID = "id";
    private static final String KEY_SYMBOL = "symbol";
    private static final String KEY_COMPANYNAME = "companyname";
    private static final String KEY_TIP = "tip";
    private static final String KEY_DATA = "data";
    private static final String KEY_TIMESTAMP = "timestamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STOCKS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_STOCKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_SYMBOL + " TEXT,"
                + KEY_COMPANYNAME + " TEXT,"
                + KEY_TIP + " TEXT,"
                + KEY_DATA + " TEXT,"
                + KEY_TIMESTAMP + " INTEGER " + ")";
        db.execSQL(CREATE_STOCKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.err.println("Updating Database to version: " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKS);
        onCreate(db);
    }

    public void addStockRow(StockRow stockRow) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SYMBOL, stockRow.getSymbol());
        values.put(KEY_COMPANYNAME, stockRow.getCompanyName());
        values.put(KEY_TIP, stockRow.getTip());
        values.put(KEY_DATA, stockRow.getData());
        values.put(KEY_TIMESTAMP, stockRow.getTimestamp());

        long rowId = db.insert(TABLE_STOCKS, null, values);
        stockRow.setID(rowId);
        db.close(); // Closing database connection
    }

    public List<StockRow> getAllStockRows() {
        List<StockRow> stockRowList = new ArrayList<StockRow>();

        String selectQuery = "SELECT "
                + KEY_ID + ","
                + KEY_SYMBOL + ","
                + KEY_COMPANYNAME + ","
                + KEY_TIP + ","
                + KEY_DATA + ","
                + KEY_TIMESTAMP
                + " FROM " + TABLE_STOCKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StockRow stockRow = new StockRow();
                stockRow.setID(Long.parseLong(cursor.getString(0)));
                stockRow.setSymbol(cursor.getString(1));
                stockRow.setCompanyName(cursor.getString(2));
                stockRow.setTip(cursor.getString(3));
                stockRow.setData(cursor.getString(4));
                stockRow.setTimestamp(Long.parseLong(cursor.getString(5)));

                stockRowList.add(stockRow);
            } while (cursor.moveToNext());
        }

        return stockRowList;
    }

    public void deleteAllRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STOCKS, null, null);
        db.close();
    }

}