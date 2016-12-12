package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CitiesDatabase extends SQLiteOpenHelper {
    public CitiesDatabase(Context context) {
        super(context, "cities.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Cities(" +
                "_id integer primary key," +
                "city text," +
                "fav integer);");
        db.execSQL("INSERT INTO Cities VALUES (0,'Aleksandrów Kujawski',0)");
        db.execSQL("INSERT INTO Cities VALUES (1,'Aleksandrów Łódzki',0)");
        db.execSQL("INSERT INTO Cities VALUES (350,'Szczecin',0)");
        db.execSQL("INSERT INTO Cities VALUES (134,'Kołobrzeg',0)");
        db.execSQL("INSERT INTO Cities VALUES (144,'Koszalin',0)");
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
