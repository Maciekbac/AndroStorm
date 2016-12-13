package maciej.androstorm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PrepareDatabase().execute();
    }

    private class PrepareDatabase extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            CitiesDatabase db = new CitiesDatabase(MainActivity.this);
            db.getReadableDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(MainActivity.this, CitiesListActivity.class);
            startActivity(i);
            finish();
        }
    }
}
