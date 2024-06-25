package com.example.mysqlconn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ListView txnListView;
    TextView otpNumber;
    EditText txnEditText;
    ArrayList<String> list;
    Button btnAdd;
    ArrayAdapter<String> arrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView =(ListView)findViewById(R.id.listView);
        txnEditText = (EditText)findViewById(R.id.txnEdittext);
        txnListView = (ListView)findViewById(R.id.txnListView);
        btnAdd = (Button)findViewById(R.id.btnhsowtxn);

        list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,list);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txn = txnEditText.getText().toString();
                list.add(txn);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        requestsmspermission();
        OTP_Receiver otp = new OTP_Receiver();
        otp.setEditListView(listView);
        otp.setEditListView(txnListView);
//        sms.onReceive(this.getApplicationContext(),this.getIntent());
    }
    private void requestsmspermission() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, smspermission);
        // to check if read SMS permission is granted or not
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = smspermission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sms(View view){

        //we ask user permission to auto read sms
//        listView =(ListView)findViewById(R.id.listView);
        Intent intent = new Intent();
        requestsmspermission();
        OTP_Receiver otp_receiver = new OTP_Receiver();
        otp_receiver.onReceive(getApplicationContext(),intent);
//        ArrayAdapter onRCadt;
//        ArrayList<String> smslist = new ArrayList<String>();
//        onRCadt = otp_receiver.setListView(this.getApplicationContext(),android.R.layout.simple_list_item_1, smslist);
//        new OTP_Receiver().setListView(this,listView);
//        listView.setAdapter(onRCadt);
//        onRCadt.notifyDataSetChanged();

    }
    public void onStartSync(View view) {
        BackgroundWorker bg = new BackgroundWorker(this);
        bg.execute("Start");
    }
}
