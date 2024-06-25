package com.example.mysqlconn;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    BackgroundWorker (Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_uri = "https://kooolba.com/payment";
         if(type.equals("Start")){
             try {

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

                 URL url = new URL("https://kooolba.com/payapi/savepay.php");

                 HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 httpURLConnection.setDoInput(true);
                 OutputStream outputStream = httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                 String post_data = URLEncoder.encode("full_name", "UTF-8")+"="+URLEncoder.encode(Custemer_Name, "UTF-8") + "&"
                         +URLEncoder.encode("tnx_id", "UTF-8")+"="+URLEncoder.encode(Transaction_Id, "UTF-8") + "&"
                         +URLEncoder.encode("mobile", "UTF-8")+"="+URLEncoder.encode(Customer_Phone, "UTF-8") + "&"
                         +URLEncoder.encode("amount", "UTF-8")+"="+URLEncoder.encode(Payed_Amount, "UTF-8");
                 bufferedWriter.write(post_data);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 outputStream.close();
                 InputStream inputStream = httpURLConnection.getInputStream();
                 inputStream.close();
                 return "Success";
             }catch (MalformedURLException e){
                 e.printStackTrace();
             }catch ( IOException e){
                 e.printStackTrace();;
             }
         }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Delivery status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
