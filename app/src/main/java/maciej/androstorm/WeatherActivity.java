package maciej.androstorm;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    int city_id;
    TextView tv_city;
    TextView tv_storm_p;
    TextView tv_storm_t;
    TextView tv_rain_p;
    TextView tv_rain_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tv_city = (TextView)findViewById(R.id.tv_city_v);
        tv_storm_p = (TextView)findViewById(R.id.tv_storm_p_v);
        tv_storm_t = (TextView)findViewById(R.id.tv_storm_t_v);
        tv_rain_p = (TextView)findViewById(R.id.tv_rain_p_v);
        tv_rain_t = (TextView)findViewById(R.id.tv_rain_t_v);

        Intent i = getIntent();
        city_id = i.getIntExtra("city",-1);
        if(city_id!=-1) new Connection().execute();
    }

    private class Connection extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {
            JsonDownloader jd = new JsonDownloader(city_id);
            String json = jd.getJson();
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject j = null;
            try {
                j = new JSONObject(s);
                tv_city.setText(j.getString("m"));
                tv_storm_p.setText(j.getString("p_b"));
                tv_storm_t.setText(j.getString("t_b"));
                tv_rain_p.setText(j.getString("p_o"));
                tv_rain_t.setText(j.getString("t_o"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
