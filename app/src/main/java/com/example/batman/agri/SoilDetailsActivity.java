package com.example.batman.agri;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SoilDetailsActivity extends AppCompatActivity {

    //private final static String SOIL_DATA_URL = "https://rest.soilgrids.org/query?lon=5.39&lat=51.57";
    private final static String LOG_TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        new AsyncTaskParseJson().execute();

    }

        private void updateUi(JSONObject soil_object) {

        Bundle bundle = getIntent().getExtras();
        double latitude = bundle.getDouble("lat");
        double longitude = bundle.getDouble("lon");

        String lat = String.format("%.2f",latitude);
        String lon = String.format("%.2f",longitude);

      //  String JObject = "{https://rest.soilgrids.org/query?lon=" + lon + "&lat=" + lat+"}";
      //  String JObject = "https://rest.soilgrids.org/query?lon=5.39&lat=51.57";

        try {

            JSONObject properties_object = soil_object.getJSONObject("properties");

            String bulk_density = properties_object.getJSONObject("BLDFIE").getJSONObject("M").getString("sl2");
            String bulk_density_units = properties_object.getJSONObject("BLDFIE").getString("units_of_measure");

            String sand_fraction = properties_object.getJSONObject("SNDPPT").getJSONObject("M").getString("sl2");
            String sand_fraction_units = properties_object.getJSONObject("SNDPPT").getString("units_of_measure");

            String clay_fractioin = properties_object.getJSONObject("CLYPPT").getJSONObject("M").getString("sl2");
            String clay_fractioin_units = properties_object.getJSONObject("CLYPPT").getString("units_of_measure");

            String slit_fraction = properties_object.getJSONObject("SLTPPT").getJSONObject("M").getString("sl2");
            String slit_fractioin_units = properties_object.getJSONObject("SLTPPT").getString("units_of_measure");

            String total_nitrogen = properties_object.getJSONObject("NTO").getJSONObject("M").getString("xd1");
            String total_nitrogen_units = properties_object.getJSONObject("NTO").getString("units_of_measure");

            String organic_carbon = properties_object.getJSONObject("ORCDRC").getJSONObject("M").getString("sl2");
            String organic_carbon_units = properties_object.getJSONObject("ORCDRC").getString("units_of_measure");

            String soil_ph = properties_object.getJSONObject("PHIHOX").getJSONObject("M").getString("sl2");
            String soil_ph_units = properties_object.getJSONObject("PHIHOX").getString("units_of_measure");


            TextView textView = (TextView) findViewById(R.id.title_text_view);
            textView.setText("Soil Details");

            ArrayList<DetailsSet> details_set = new ArrayList<DetailsSet> ();

            details_set.add(new DetailsSet("Location:","Latitude:"+lat+" & Longitude:"+lon ));
            details_set.add(new DetailsSet("Bulk Density ("+bulk_density_units+") :",bulk_density ));
            details_set.add(new DetailsSet("Soil texture fraction Sand ("+sand_fraction_units+") :",sand_fraction ));
            details_set.add(new DetailsSet("Soil texture fraction Slit ("+slit_fractioin_units+") :",slit_fraction ));
            details_set.add(new DetailsSet("Soil texture fraction Clay  ("+clay_fractioin_units+") :",clay_fractioin ));
            details_set.add(new DetailsSet("Total Nitrogen ("+total_nitrogen_units+") :",total_nitrogen ));
            details_set.add(new DetailsSet("Organic Carbon Content ("+organic_carbon_units+") :",organic_carbon ));
            details_set.add(new DetailsSet("Soil pH ("+soil_ph_units+") :",soil_ph ));



            ListView listView = (ListView) findViewById(R.id.list);

            DetailsAdapter soil_details = new DetailsAdapter(this,details_set);

            listView.setAdapter(soil_details);


//            TextView coordinates_text_view = (TextView) findViewById(R.id.coordinates);
//            coordinates_text_view.setText("Latitude:"+lat+" & Longitude:"+lon);
//
//            TextView usda_text_view = (TextView) findViewById(R.id.TaxUsda);
//            usda_text_view.setText(Tax_Usda);
//
//            TextView ref_text_view = (TextView) findViewById(R.id.TaxRef);
//            ref_text_view.setText(Tax_Ref);

        }

        catch(JSONException e) {

            Log.e("MYAPP", "unexpected JSON exception", e);
        }
        }

    private class AsyncTaskParseJson extends AsyncTask<URL, Void , JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {

            Bundle bundle = getIntent().getExtras();
        double latitude = bundle.getDouble("lat");
        double longitude = bundle.getDouble("lon");

        String lat = String.format("%.2f",latitude);
        String lon = String.format("%.2f",longitude);

        String SOIL_DATA_URL = "https://rest.soilgrids.org/query?lon=" + lon + "&lat=" + lat;


            URL url = makeUrl(SOIL_DATA_URL);

            String jsonResponse = "";
            JSONObject soil_jsonObject = null;

            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                 soil_jsonObject = new  JSONObject(jsonResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return soil_jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            updateUi(jsonObject);
        }

        private URL makeUrl(String urlString){
            URL url = null;

            try {
                url = new URL(urlString);

            }
            catch(MalformedURLException e){
                Log.e(LOG_TAG,"Error with creating URL:",e);
                return null;
            }

            return url;
        }

        private String makeHttpRequest (URL url) throws IOException {
            String jsonResponse = "";

            if (url == null){
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);

                }

            }

            catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }


            return  jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {


            StringBuilder output = new StringBuilder();

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line = reader.readLine();
                while(line !=null){

                    output.append(line);
                    line = reader.readLine();

                }



            }

            return output.toString();
        }


    }
}
