package com.weathertellerapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityEntered;
    Button getWeather;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEntered=(EditText) findViewById(R.id.countryEntered);
        getWeather=(Button) findViewById(R.id.getWeatherButton);
        result=(TextView) findViewById(R.id.result);

        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(cityEntered.getWindowToken(),0);
                String res;
                String msg="";
                try {
                    DownloadJSON weatherInfo = new DownloadJSON();
                    String encodedCityName = URLEncoder.encode(cityEntered.getText().toString(), "UTF-8");
                    res = weatherInfo.execute("http://api.openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=0ed677703381fcca1fc5adbe0a4e8b5e").get();
                    Log.i("Result",res);
                    JSONObject jsonObject = new JSONObject(res);
                    String myString = jsonObject.getString("weather");
                    Log.i("String",myString);
                    JSONArray jsonArray = new JSONArray(myString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonPart = jsonArray.getJSONObject(i);
                        String main = "";
                        String description = "";
                        main = jsonPart.getString("main");
                        description = jsonPart.getString("description");
                        if(main!="" && description!="")
                        {
                            msg=main+": "+description+"\n";
                        }
                    }
                    if(msg!="")
                    {
                        result.setText(msg);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry, couldd not retrieve the weather", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Sorry, could not retrieve the weather", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
