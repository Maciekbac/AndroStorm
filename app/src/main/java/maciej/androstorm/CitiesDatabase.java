package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CitiesDatabase extends SQLiteOpenHelper {
    Context context;
    public CitiesDatabase(Context context) {
        super(context, "cities.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Cities(" +
                "_id integer primary key," +
                "city text," +
                "fav integer);");
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("cities")));
            String line;
            int i=0;
            while ((line = reader.readLine()) != null){
                db.execSQL("INSERT INTO Cities VALUES ("+i+",'"+line+"',0)");
                i++;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Cities");
        onCreate(db);
    }

    public Cursor getAllCities(){
        String[] columns = {"_id","city","fav"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Cities",columns,null,null,null,null,"city ASC");
        return c;
    }


}
