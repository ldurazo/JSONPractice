package com.internship.jsonpractice.app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    ListView httpData;
    HttpClient client;
    List<String> objectsList = new ArrayList<String>();
    CurrentConditionModel currentConditionModel = new CurrentConditionModel();
    final static String URL = "http://raywenderlich.com/demos/weather_sample/weather.php?format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> oListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,objectsList);
        httpData = (ListView) findViewById(R.id.listView);
        client = new DefaultHttpClient();
        new Read().execute();
        httpData.setAdapter(oListAdapter);
    }

    private JSONObject someInfo() throws ClientProtocolException, IOException, JSONException{
        HttpGet get = new HttpGet(URL);
        HttpResponse r = client.execute(get);
        int status = r.getStatusLine().getStatusCode();
        if (status==200){
            HttpEntity e = r.getEntity();
            JSONObject parentData = new JSONObject(EntityUtils.toString(e));
            JSONObject objectNameData = parentData.getJSONObject("data");
            JSONArray currentCondition = objectNameData.getJSONArray("current_condition");
            JSONObject container = currentCondition.getJSONObject(0);
            for (int i=0; i<container.length();i++) {
                switch (i){
                    case 0: currentConditionModel.cloudcover = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.cloudcover);
                        break;
                    case 1: currentConditionModel.humidity = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.humidity);
                        break;
                    case 2: currentConditionModel.observation_time = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.observation_time);
                        break;
                    case 3: currentConditionModel.precipMM = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.precipMM);
                        break;
                    case 4: currentConditionModel.pressure = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.pressure);
                        break;
                    case 5: currentConditionModel.temp_c = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.temp_c);
                        break;
                    case 6: currentConditionModel.temp_f = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.temp_f);
                        break;
                    case 7: currentConditionModel.visibility = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.visibility);
                        break;
                    case 8: currentConditionModel.weatherCode = container.getString(currentConditionModel.currentCondition[i]);
                        objectsList.add(currentConditionModel.weatherCode);
                        break;
                }
            }
            return container;
        }
        else{
            return null;
        }
    }

    public class Read extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params){
            try{
                JSONObject JSONinfo = someInfo();
                return  "Objects loaded";
            } catch (ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            return "Failed to load content";
        }

        protected void onPostExecute(String result){

            super.onPostExecute(result);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
