package maciej.androstorm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class FavouriteCitiesActivity extends AppCompatActivity {
    FavouriteCitiesAdapter fca;
    CitiesDatabase db;
    SwipeRefreshLayout swipelayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_cities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = (ListView)findViewById(R.id.lv_fcities);
        db = new CitiesDatabase(this);
        Cursor c = db.getFavCities();
        fca = new FavouriteCitiesAdapter(this,c,true);
        lv.setAdapter(fca);

        refreshAllCities();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteCitiesActivity.this,CitiesListActivity.class);
                startActivity(i);
            }
        });

        swipelayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipelayout.setRefreshing(false);
                refreshAllCities();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c = db.getFavCities();
        fca.changeCursor(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            refreshAllCities();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshAllCities()
    {
        ArrayList<Integer> cities = new ArrayList<Integer>();
        Cursor cursor = db.getFavCities();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            cities.add(cursor.getInt(cursor.getColumnIndex("_id")));
            cursor.moveToNext();
        }
        cursor.close();
        new Connection().execute(cities);
    }

    private class Connection extends AsyncTask<ArrayList<Integer>, Integer, Integer>{
        ProgressDialog pDialog;
        @Override
        protected Integer doInBackground(ArrayList<Integer>... cities) {
            for(int i = 0;i<cities[0].size();i++)
            {
                int id = cities[0].get(i);
                String json = new JsonDownloader(id).getJson();
                try {
                    db.jsonToWeather(json,id);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }
                publishProgress(new Integer[]{i,cities[0].size()});
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(FavouriteCitiesActivity.this,"Pobieranie danych", "Pobieranie",true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer!=0)
                Toast.makeText(FavouriteCitiesActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
            Cursor c = db.getFavCities();
            fca.changeCursor(c);
            pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pDialog.setMessage("Pobrano " + (values[0]+1) + "/" + values[1]);
        }
    }

}
