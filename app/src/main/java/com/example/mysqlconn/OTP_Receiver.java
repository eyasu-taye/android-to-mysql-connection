package com.example.mysqlconn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class OTP_Receiver extends BroadcastReceiver {
    protected static  ArrayList<String> smsTxt;
    private static ArrayAdapter<String> smsArrayAdapter;
    protected static ListView listView;
    private DBHandler dbHandler;

    // below variable is for our table name.
    private static final String TABLE_NAME = "transactions";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our Custemer_Name column.
    private static  String Custemer_Name = "Custemer_Name";

    // below variable is for our Transacgtion_Id column.
    private static String Transaction_Id = "Transaction_Id";

    // below variable is for our Payed_Amount column.
    private static String Payed_Amount = "Payed_Amount";

    // below variable is for our Custemer_Phone column.
    private static String Custemer_Phone = "Custemer_Phone";

    // below variable is for our Payment_Bank_Name column.
    private static String Payment_Bank_Name = "Payment_Bank_Name";

    // below variable is for our Transaction_Detail column.
    private static String Transaction_Detail = "Transaction_Detail";
    // below variable is for our Date column.
    private static String Date = "Date";

    MainActivity mn  = new MainActivity();


    protected String getCustemer_Name(){
        return Custemer_Name;
    }
    protected String getTransaction_Id(){
        return Transaction_Id;
    }
    protected String getPayed_Amount(){
        return Payed_Amount;
    }
    protected String getCustemer_Phone(){
        return Custemer_Phone;
    }
    protected String getPayment_Bank_Name(){
        return Payment_Bank_Name;
    }
    protected String getTransaction_Detail(){
        return Transaction_Detail;
    }
    // Below code will set values
    private void setCustemer_Name(String Customer_Name){
        this.Custemer_Name = Customer_Name;
    }
    private void setTransaction_Id(String Transaction_Id){
        this.Transaction_Id = Transaction_Id;
    }
    private void setPayed_Amount(String Payed_Amount){
        this.Payed_Amount = Payed_Amount;
    }
    private void setCustemer_Phone(String Customer_Phone){
        this.Custemer_Phone = Customer_Phone;
    }
    private void setPayment_Bank_Name(String Payment_Bank_Name){
        this.Payment_Bank_Name = Payment_Bank_Name;
    }
    private void setTransaction_Detail(String Transaction_Detail){
        this.Transaction_Detail = Transaction_Detail;
    }
    // OnReceive will keep trace when sms is been received in mobile
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

      @Override
    public void onReceive(Context context, Intent intent) {
        //message will be holding complete sms that is received
        dbHandler = new DBHandler(context);
        smsTxt = new ArrayList<String>();
       SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
         for(SmsMessage sms : messages)
       {
            String msg = sms.getMessageBody();
            // msg = sms.getOriginatingAddress();
            // here we are splitting the sms using " : " symbol
//            String otp = msg.split(": ")[1];
            smsTxt.add(msg);
           // on below line we are calling a method to add new
           // course to sqlite data and pass all our values to it.
           setCustemer_Name(msg);
           setTransaction_Id(msg);
           setCustemer_Phone(msg);
           setPayed_Amount(msg);
           setPayment_Bank_Name(msg);
           setTransaction_Detail(msg);

           // after adding the data we are displaying a toast message.


        }
         BackgroundWorker bk = new BackgroundWorker(context);
         bk.doInBackground();
         DBHandler dbh = new DBHandler(context);
         dbh.addNewPayment();
        smsTxt.add("12347");

        //we ask user permission to auto read sms
        smsArrayAdapter = new ArrayAdapter<String>(context.getApplicationContext(),
                android.R.layout.simple_list_item_1,smsTxt);


//        smsArrayAdapter.add(smsTxt);
        listView.setAdapter(smsArrayAdapter);
        smsArrayAdapter.notifyDataSetChanged();

//        mn.txnListView.setAdapter(smsArrayAdapter);



    }


    public void setEditListView(ListView listView) {
            OTP_Receiver.listView =listView;

    }
}