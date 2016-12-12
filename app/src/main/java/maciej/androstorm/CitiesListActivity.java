package maciej.androstorm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CitiesListActivity extends AppCompatActivity {
    CitiesListAdapter cla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

        ListView lv = (ListView)findViewById(R.id.lv_cities);
        CitiesDatabase db = new CitiesDatabase(this);
        Cursor c = db.getAllCities();
        cla = new CitiesListAdapter(this,c,0);

        lv.setAdapter(cla);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CitiesListActivity.this,WeatherActivity.class);
                int cityId = (int)cla.getItemId(i);
                intent.putExtra("city",cityId);
                startActivity(intent);
            }
        });
    }
}
