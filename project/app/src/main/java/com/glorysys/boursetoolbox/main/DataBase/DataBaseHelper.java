package com.glorysys.boursetoolbox.main.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "buy_database";
    static String TABLE_BUY = "buy_table";
    ///////////
    static String TABLE_REVIEW = "review_table";
    private static final String COLUMN_COUNTER = "column_counter";
    private static final String COLUMN_BOOLEAN_SHOW = "column_boolean_show";
    ////////
    static String TABLE_SELL = "sell_table";
    static String TABLE_PORTFOLIO = "Portfolio_table";
    private static final String COLUMN_NAME = "column_name";
    private static final String COLUMN_COMPANY_NAME = "column_company_name";
    private static final String COLUMN_PRICE = "column_price";
    private static final String COLUMN_SOLD_PRICE = "column_sold_price";
    private static final String COLUMN_SOLD_DATE = "column_sold_date";
    private static final String COLUMN_NUMBER = "column_number";
    private static final String COLUMN_DATE = "column_date";
    private static final String COLUMN_ID = "column_ID";
    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTable = "create table " + TABLE_BUY + " (" + COLUMN_NAME + " TEXT primary key, "
                + COLUMN_PRICE + " INTEGER, " + COLUMN_NUMBER + " INTEGER, " + COLUMN_DATE + " TEXT)";
        String CreateTableSell = "create table " + TABLE_SELL + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT , "
                + COLUMN_PRICE + " INTEGER, " + COLUMN_SOLD_PRICE + " INTEGER, " + COLUMN_NUMBER + " INTEGER, " + COLUMN_DATE + " TEXT" +
                ", " + COLUMN_SOLD_DATE + " TEXT, " + COLUMN_COMPANY_NAME + " TEXT)";
        String CreateTablePortofio = "create table " + TABLE_PORTFOLIO + " (" + COLUMN_NAME + " TEXT primary key )";
        String CreateTableReview = "create table " + TABLE_REVIEW + " (" + COLUMN_NAME + " TEXT primary key, " + COLUMN_COUNTER + " INTEGER, " +
                COLUMN_BOOLEAN_SHOW + " INTEGER)";


        sqLiteDatabase.execSQL(CreateTable);
        sqLiteDatabase.execSQL(CreateTableSell);
        sqLiteDatabase.execSQL(CreateTablePortofio);
        sqLiteDatabase.execSQL(CreateTableReview);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BUY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SELL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PORTFOLIO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COLUMN_BOOLEAN_SHOW);
        onCreate(sqLiteDatabase);
    }

    public void DB_buy(String name, long price, long number, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        int int_price = 0, int_number = 0;
        ContentValues cv = new ContentValues();
        if (Exists(name)) {
            String query = "SELECT * FROM " + TABLE_BUY + " where " + COLUMN_NAME + "=?";
            Cursor cursor = database.rawQuery(query, new String[]{name});
            if (cursor.getCount() == 0) {
              //  Toast.makeText(context, "NO DATA.", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    int_number = cursor.getInt(2);
                    int_price = cursor.getInt(1);
                }

                price = (price + int_price) / 2;
                number = number + int_number;
                cv.put(COLUMN_NAME, name);
                cv.put(COLUMN_PRICE, price);
                cv.put(COLUMN_NUMBER, number);
                cv.put(COLUMN_DATE, date);
                long Result = database.update(TABLE_BUY, cv, COLUMN_NAME + "=?", new String[]{name});

                if (Result == -1) {
                    Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "آپدیت شد ", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_PRICE, price);
            cv.put(COLUMN_NUMBER, number);
            cv.put(COLUMN_DATE, date);
            long Result = database.insert(TABLE_BUY, null, cv);
            if (Result == -1) {
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "عملیات خرید با موفقیت انجام شد ", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void DB_Sell(String name, int price, int soldPrice, int number, String BuyDate, String SoldDate, String coName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_PRICE, price);
            cv.put(COLUMN_SOLD_PRICE, soldPrice);
            cv.put(COLUMN_NUMBER, number);
            cv.put(COLUMN_DATE, BuyDate);
            cv.put(COLUMN_SOLD_DATE, SoldDate);
            cv.put(COLUMN_COMPANY_NAME, coName);
        } catch (Exception e) {
            e.getStackTrace();
        }
        long Result = database.insert(TABLE_SELL, null, cv);
        if (Result == -1) {
            Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "عملیات فروش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean Exists(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select " + COLUMN_NAME + " from " + TABLE_BUY + " where " + COLUMN_NAME + "=?", new String[]{name});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor ReadAllData_Table_Buy() {
        String query = "SELECT * FROM " + TABLE_BUY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor ReadAllData_Table_sell() {
        String query = "SELECT * FROM " + TABLE_SELL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void row_delete_buy(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        String Query = COLUMN_NAME + "=?";
        long result = database.delete(TABLE_BUY, Query, new String[]{name});
        if (result == -1) {
            Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

        } else {
          //  Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
        }

    }

    public void row_delete_sell(String ID) {
        SQLiteDatabase database = this.getWritableDatabase();
        String Query = COLUMN_ID + "=?";
        long result = database.delete(TABLE_SELL, Query, new String[]{ID});
        if (result == -1) {
            Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

        } else {
         //   Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
        }

    }

    /////////////////////////////////PORTOFIO///////////////////////


    public void DB_Portfolio(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (Exists_Portfolio(name)) {
            String query = "SELECT * FROM " + TABLE_PORTFOLIO + " where " + COLUMN_NAME + "=?";
            Cursor cursor = database.rawQuery(query, new String[]{name});
            if (cursor.getCount() == 0) {
              //  Toast.makeText(context, "NO DATA.", Toast.LENGTH_SHORT).show();
            } else {
                String Query = COLUMN_NAME + "=?";
                long Result = database.delete(TABLE_PORTFOLIO, Query, new String[]{name});

                if (Result == -1) {
                    Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

                } else {
                //    Toast.makeText(context, "successfull delete", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            cv.put(COLUMN_NAME, name);
            long Result = database.insert(TABLE_PORTFOLIO, null, cv);
            if (Result == -1) {
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();

            } else {
                //Toast.makeText(context, "inserted", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public boolean Exists_Portfolio(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select " + COLUMN_NAME + " from " + TABLE_PORTFOLIO + " where " + COLUMN_NAME + "=?", new String[]{name});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor ReadAllData_Table_portfolio() {
        String query = "SELECT * FROM " + TABLE_PORTFOLIO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

//////////////////////////////////////////////////
                 //REVIEW
//////////////////////////////////////////////////

    public Cursor ReadData_Table_Review(String review) {
        String query = "SELECT * FROM " + TABLE_REVIEW +" WHERE "+ COLUMN_NAME+"=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query,new String[]{review});
        }
        return cursor;
    }


    ////
    public void DB_Write_Review(String name, int counter, int intBooleanShow) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_COUNTER, counter);
            cv.put(COLUMN_BOOLEAN_SHOW, intBooleanShow);
        } catch (Exception e) {
            e.getStackTrace();
        }
        long Result = database.insert(TABLE_REVIEW, null, cv);
        if (Result == -1) {
           // Toast.makeText(context, "review faild", Toast.LENGTH_SHORT).show();

        } else {
          //  Toast.makeText(context, "review success", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean Exists_review(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select " + COLUMN_NAME + " from " + TABLE_REVIEW + " where " + COLUMN_NAME + "=?", new String[]{name});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    ////
    public void DB_Update_Review(String name, int counter, int intBooleanShow) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_COUNTER, counter);
            cv.put(COLUMN_BOOLEAN_SHOW, intBooleanShow);
        } catch (Exception e) {
            e.getStackTrace();
        }
        long Result = database.update(TABLE_REVIEW, cv,COLUMN_NAME + "=?",new String[]{name});
        if (Result == -1) {
           // Toast.makeText(context, "review update faild", Toast.LENGTH_SHORT).show();

        } else {
            //Toast.makeText(context, "review update success", Toast.LENGTH_SHORT).show();
        }
    }
}
