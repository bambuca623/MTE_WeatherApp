package com.example.adamh.mte_weatherapp;

import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adamh.mte_weatherapp.Model.Weather;

import org.json.JSONException;

public class ShowWeather extends AppCompatActivity {
    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;

    private TextView hum;
    private ImageView imgView;
    private String selectedName;

    public static String PACKAGE_NAME;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowWeather.this, MainActivity.class));
            }
        });

        PACKAGE_NAME = getApplicationContext().getPackageName();

        Intent receivedIntent = getIntent();
        selectedName = receivedIntent.getStringExtra("name");
        String city = selectedName;

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        imgView = (ImageView) findViewById(R.id.condIcon);

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }


    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new MapWeather()).getWeatherData(params[0]));
            if(data == "Incorrect city" || data == null) {
                return null;
            }
            else {
                try {
                    weather = DataParser.getWeather(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return weather;
            }
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            if (weather == null)
            {
                System.out.println("Incorrect city");
                cityText.setText("City not found");
            }
            else {
                int imageResource = 0;
                switch (weather.currentCondition.getIcon()) {
                    case "01d":
                        imageResource = getResources().getIdentifier("drawable/img01d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "01n":
                        imageResource = getResources().getIdentifier("drawable/img01n", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "02d":
                        imageResource = getResources().getIdentifier("drawable/img02d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "02n":
                        imageResource = getResources().getIdentifier("drawable/img02n", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "03d":
                        imageResource = getResources().getIdentifier("drawable/img03d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "03n":
                        imageResource = getResources().getIdentifier("drawable/img03d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "04d":
                        imageResource = getResources().getIdentifier("drawable/img04d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "04n":
                        imageResource = getResources().getIdentifier("drawable/img04d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "09d":
                        imageResource = getResources().getIdentifier("drawable/img09d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "09n":
                        imageResource = getResources().getIdentifier("drawable/img09d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "10d":
                        imageResource = getResources().getIdentifier("drawable/img10d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "10n":
                        imageResource = getResources().getIdentifier("drawable/img10n", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "11d":
                        imageResource = getResources().getIdentifier("drawable/img11d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "11n":
                        imageResource = getResources().getIdentifier("drawable/img11d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "13d":
                        imageResource = getResources().getIdentifier("drawable/img13d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "13n":
                        imageResource = getResources().getIdentifier("drawable/img13d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "50d":
                        imageResource = getResources().getIdentifier("drawable/img50d", null, ShowWeather.PACKAGE_NAME);
                        break;
                    case "50n":
                        imageResource = getResources().getIdentifier("drawable/img50d", null, ShowWeather.PACKAGE_NAME);
                        break;
                }
                imgView.setImageResource(imageResource);


                cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
                condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
                temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
                hum.setText("" + weather.currentCondition.getHumidity() + "%");
                press.setText("" + weather.currentCondition.getPressure() + " hPa");
                windSpeed.setText("" + weather.wind.getSpeed() + " m/s");

            }
        }
    }
}
