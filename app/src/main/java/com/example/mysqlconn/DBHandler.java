package com.example.mysqlconn;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "paymentDB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "transactions";

    // below variable is for our id column.
    private static final String ID_COL = "id";


    OTP_Receiver otData = new OTP_Receiver();
    // below variable is for our Custemer_Name column.
    String Custemer_Name = otData.getCustemer_Name();

    // below variable is for our Transacgtion_Id column.
    String Transaction_Id = otData.getTransaction_Id();

    // below variable is for our Payed_Amount column.
    String Payed_Amount = otData.getPayed_Amount();

    // below variable is for our Customer_Phone column.
    String Customer_Phone = otData.getCustemer_Phone();

    // below variable is for our Payment_Bank_Name column.
    String Payment_Bank_Name = otData.getPayment_Bank_Name();

    // below variable is for our Transaction_Detail column.
    String Transaction_Detail = otData.getTransaction_Detail();
    // below variable is for our Date column.
    String Date = "Date";




    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Custemer_Name + " TEXT,"
                + Transaction_Id + " TEXT,"
                + Payed_Amount + " TEXT,"
                + Payment_Bank_Name + " TEXT,"
                + Transaction_Detail + "TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewPayment() {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Custemer_Name, this.Custemer_Name);
        values.put(Transaction_Id, this.Transaction_Id);
        values.put(Payed_Amount, this.Payed_Amount);
        values.put(Payment_Bank_Name, this.Payment_Bank_Name);
        values.put(Transaction_Detail,this.Transaction_Detail);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addNewTxn(String msg) {
    }
}
