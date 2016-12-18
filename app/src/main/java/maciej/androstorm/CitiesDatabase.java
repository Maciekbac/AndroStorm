package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CitiesDatabase extends SQLiteOpenHelper {
    Context context;
    public CitiesDatabase(Context context) {
        super(context, "cities.db", null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Cities(" +
                "_id integer primary key," +
                "city text," +
                "fav integer," +
                "t_o integer," +
                "t_b integer," +
                "p_o integer," +
                "p_b integer," +
                "a_o integer," +
                "a_b integer);");
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("cities")));
            String line;
            int i=0;
            while ((line = reader.readLine()) != null){
                db.execSQL("INSERT INTO Cities VALUES ("+i+",'"+line+"',0,0,0,0,0,0,0)");
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
        String[] columns = {"_id","city","fav","t_o","t_b","p_o","p_b","a_o","a_b"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Cities",columns,null,null,null,null,null);
        return c;
    }

    public Cursor getFavCities(){
        String[] columns = {"_id","city","fav","t_o","t_b","p_o","p_b","a_o","a_b"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Cities",columns,"fav=?", new String[]{"1"},null,null,null);
        return c;
    }

    public void jsonToWeather(String json, int cityId) throws Exception {
        JSONObject jso = new JSONObject(json);
        SQLiteDatabase db = getWritableDatabase();
        int t_o = jso.getInt("t_o");
        int p_o = jso.getInt("p_o");
        int t_b = jso.getInt("t_b");
        int p_b = jso.getInt("p_b");
        int a_o = jso.getInt("a_o");
        int a_b = jso.getInt("a_b");

        db.execSQL("UPDATE Cities SET t_o = "+t_o+" WHERE _id = "+cityId);
        db.execSQL("UPDATE Cities SET p_o = "+p_o+" WHERE _id = "+cityId);
        db.execSQL("UPDATE Cities SET t_b = "+t_b+" WHERE _id = "+cityId);
        db.execSQL("UPDATE Cities SET p_b = "+p_b+" WHERE _id = "+cityId);
        db.execSQL("UPDATE Cities SET a_o = "+a_o+" WHERE _id = "+cityId);
        db.execSQL("UPDATE Cities SET a_b = "+a_b+" WHERE _id = "+cityId);
        db.close();
    }

}
