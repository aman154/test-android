package com.ibusl.android.register.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ibusl.android.register.model.Discount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 20/4/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "DataBaseHelper";
    private static final String DATABASE_NAME = "ibusl_register";
    private static final int DATABASE_VERSION = 1;

    //discount table
    private static final String TABLE_DISCOUNT = "discount";

    //discount table columns
    private static final String DISCOUNT_NAME = "d_name";
    private static final String DISCOUNT_TYPE = "d_type";
    private static final String DISCOUNT_AMOUNT = "d_amount";
    private static final String DISCOUNT_ID = "d_id";
    //+ DISCOUNT_ID + " INTEGER PRIMARY KEY,"
    private static final String DISCOUNT_CREATED_AT = "d_create_at";

    private static final String CREATE_DISCOUNT_TABLE = "CREATE TABLE "
            + TABLE_DISCOUNT + "(" + DISCOUNT_NAME
            + " TEXT," + DISCOUNT_TYPE + " INTEGER," + DISCOUNT_AMOUNT + " TEXT," + DISCOUNT_CREATED_AT
            + " DATETIME" + ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DISCOUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_DISCOUNT_TABLE);

        // create new tables
        onCreate(db);
    }

    public long createDiscount(Discount discount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISCOUNT_NAME, discount.getDiscountName());
        values.put(DISCOUNT_TYPE, discount.getDiscountType());
        values.put(DISCOUNT_AMOUNT, discount.getDiscountQuantity());
        values.put(DISCOUNT_CREATED_AT, System.currentTimeMillis());

        return db.insert(TABLE_DISCOUNT, null, values);

    }

    public Discount getDiscount(String discountName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_DISCOUNT + " WHERE "
                + DISCOUNT_NAME + " = " + discountName;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Discount discount = new Discount();
        discount.setDiscountName(c.getString(c.getColumnIndex(DATABASE_NAME)));
        discount.setDiscountType((c.getInt(c.getColumnIndex(DISCOUNT_TYPE))));
        discount.setDiscountQuantity(c.getString(c.getColumnIndex(DISCOUNT_AMOUNT)));

        return discount;
    }

    public List<Discount> getAllDisCounts() {
        List<Discount> discounts = new ArrayList<Discount>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISCOUNT;

        Log.e(LOG_TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Discount discount = new Discount();
                discount.setDiscountName(c.getString(c.getColumnIndex(DATABASE_NAME)));
                discount.setDiscountType((c.getInt(c.getColumnIndex(DISCOUNT_TYPE))));
                discount.setDiscountQuantity(c.getString(c.getColumnIndex(DISCOUNT_AMOUNT)));

                // adding to discount list
                discounts.add(discount);
            } while (c.moveToNext());
        }

        c.close();
        return discounts;
    }

    public int updateDiscount(Discount discount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISCOUNT_NAME, discount.getDiscountName());
        values.put(DISCOUNT_TYPE, discount.getDiscountType());
        values.put(DISCOUNT_AMOUNT, discount.getDiscountQuantity());

        // updating row
        return db.update(TABLE_DISCOUNT, values, DISCOUNT_ID + " = ?",
                new String[]{String.valueOf(discount.getDiscountId())});
    }

    public void deleteDiscount(long discount_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISCOUNT, DISCOUNT_ID + " = ?",
                new String[]{String.valueOf(discount_id)});
    }

}
