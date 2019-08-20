package com.example.craftsbrewery;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private String url;

List<Beer> beerList;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "http://starlord.hackerearth.com/beercraft";

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Beers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        beerList = new ArrayList<>();

        beerList.add(
                new Beer("0.05",
                "",
                1436,
                "Pub Beer",
                "American Pale Lager", 12.0)
        );
//        BeerAdapter adapter = new BeerAdapter(this, beerList2);
//        recyclerView.setAdapter(adapter);
        new WebData().execute(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("resume",url);
        new WebData().execute(url);
    }

    public class WebData extends AsyncTask<String, String, String>

    {
        ProgressDialog pd;

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
            Log.d("pre",url);
            //pd = ProgressDialog.show(MainActivity.this, "Loading...", "Please Wait");
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(params[0]);
                Log.d("url",params[0]);
                connection =(HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (pd.isShowing())
            {
                pd.dismiss();
            }

            try {
                Log.d("miloo",result.toString());
                ArrayList<Beer> beerList2 = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    Contact_data cd = new Contact_data();
                    JSONObject objects = jsonArray.getJSONObject(i);
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    cd.abv =  json_data.getString("abv");
                    cd.ibu = json_data.getString("ibu");
                    cd.ounces = json_data.getDouble("ounces");
                    cd.style = json_data.getString("style");
                    cd.id = json_data.getInt("id");
                    cd.name = json_data.getString("name");
                    beerList2.add(new Beer(cd.abv, cd.ibu, cd.id, cd.name,  cd.style, cd.ounces));
                    System.out.println("List: " + beerList2);
                    //System.out.println(objects.toString());
                   // Log.d("arr" + i + " : " ,beerList2.get(i).toString() );


                }

                RecyclerView rv = findViewById(R.id.recyclerView_Beers);
                BeerAdapter adapter = new BeerAdapter(MainActivity.this, beerList2);
                recyclerView.setAdapter(adapter);
               // ContactAdaptor ad = new ContactAdaptor(MainActivity.this, ar);
                //lv.setAdapter(ad);
                JSONObject json = new JSONObject(result);
               // Log.d("miloooo",json.toString());
                String jsonStatus = json.getString("status");
                if(jsonStatus.equals("OK")) {
                    JSONArray jsonRows = json.getJSONArray("rows");
                    JSONArray jsonElements = jsonRows.getJSONObject(0).getJSONArray("elements");
                    JSONObject eleInside = jsonElements.getJSONObject(0);
                    int distance = eleInside.getJSONObject("distance").getInt("value");


                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    public class Contact_data {

        public String abv;
        public String ibu;

        public int id;
        public String name;
        public String style;
        public double ounces;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
