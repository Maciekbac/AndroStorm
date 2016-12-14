package maciej.androstorm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FavouriteCitiesActivity extends AppCompatActivity {
    FavouriteCitiesAdapter fca;
    CitiesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_cities);

        ListView lv = (ListView)findViewById(R.id.lv_fcities);
        db = new CitiesDatabase(this);
        Cursor c = db.getFavCities();
        fca = new FavouriteCitiesAdapter(this,c,true);
        lv.setAdapter(fca);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteCitiesActivity.this,CitiesListActivity.class);
                startActivity(i);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavouriteCitiesActivity.this,WeatherActivity.class);
                int cityId = (int)fca.getItemId(i);
                intent.putExtra("city",cityId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c = db.getFavCities();
        fca.changeCursor(c);
    }
}
