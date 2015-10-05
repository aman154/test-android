package com.example.aman.zwan.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aman.zwan.models.Customer;

/**
 * Created by aman on 1/10/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "customersManager";

    // Contacts table name
    private static final String TABLE_CUSTOMER = "customers";

    // Contacts Table Columns names
    private static final String KEY_MOBILE = "mb";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "ps";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
                + KEY_MOBILE + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," +KEY_PASS + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);

        onCreate(db);
    }

    // Adding new contact
    void addContact(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOBILE,customer.getMobileNum()); //customer mob num
        values.put(KEY_NAME, customer.getName()); // customer Name
        values.put(KEY_EMAIL, customer.getEmail()); // customer Phone
        values.put(KEY_PASS, customer.getPassword()); //customer password

        // Inserting Row
        db.insert(TABLE_CUSTOMER, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Customer getContact(int mobNum) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CUSTOMER, new String[]{KEY_MOBILE,
                        KEY_NAME, KEY_EMAIL,KEY_PASS}, KEY_MOBILE + "=?",
                new String[]{String.valueOf(mobNum)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Customer contact = new Customer(cursor.getString(0),Integer.parseInt(cursor.getString(1)), cursor.getString(2),cursor.getString(3));
        // return contact
        return contact;
    }

   /* // Getting All Contacts
    public List<Customer> getAllContacts() {
        List<Customer> contactList = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer contact = new Customer();
                customer.setID(Integer.parseInt(cursor.getString(0)));
                customer.setName(cursor.getString(1));
                customer.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }*/

    // Updating single contact
    public int updateContact(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOBILE,customer.getMobileNum()); //customer mob num
        values.put(KEY_NAME, customer.getName()); // customer Name
        values.put(KEY_EMAIL, customer.getEmail()); // customer Phone
        values.put(KEY_PASS, customer.getPassword()); //customer password

        // updating row
        return db.update(TABLE_CUSTOMER, values, KEY_MOBILE + " = ?",
                new String[] { String.valueOf(customer.getMobileNum()) });
    }

   /* // Deleting single contact
    public void deleteContact(Customer contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, KEY_ID + " = ?",
                new String[] { String.valueOf(customer.getID()) });
        db.close();
    }*/


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
