package com.example.currencyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView chfText;
    TextView usdText;
    TextView tryText;
    TextView jpyText;
    TextView cadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chfText = findViewById(R.id.textView1);
        usdText = findViewById(R.id.textView2);
        jpyText = findViewById(R.id.textView3);
        tryText = findViewById(R.id.textView4);
        cadText = findViewById(R.id.textView5);

        getRates();
    }

    public void getRates(){
        DownloadData downloadData = new DownloadData();

        try {
            String url = "http://data.fixer.io/api/latest?access_key=6c79ab646c3706345abd2cdf3fdd8213";
            downloadData.execute(url);
        } catch (Exception e){

        }
    }

    private class DownloadData extends AsyncTask<String, Void, String>{
        @Override
        protected  String doInBackground(String... strings){
            StringBuilder results = new StringBuilder();
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data > 0){
                    char character = (char) data;
                    results.append(character);
                    data = inputStreamReader.read();

                }
                return results.toString();
            } catch (Exception e){
                return null;
            }
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            System.out.println("alınan data" + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");

                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishLira = jsonObject1.getString("TRY");
                tryText.setText("TRY: " + turkishLira);

                String usd = jsonObject1.getString("USD");
                usdText.setText("USD: " + usd);

                String cad = jsonObject1.getString("CAD");
                cadText.setText("CAD: " + cad);

                String chf = jsonObject1.getString("CHF");
                chfText.setText("CHF: " + chf);

                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("JPY: " + jpy);
            } catch (Exception e){

            }
        }
    }
}