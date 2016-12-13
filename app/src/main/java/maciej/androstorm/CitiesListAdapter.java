package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class CitiesListAdapter extends CursorAdapter {

    CheckBox cb;

    public CitiesListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.cities_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView tv = (TextView)view.findViewById(R.id.cli_city);
        cb = (CheckBox)view.findViewById(R.id.cli_cBox);

        String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
        tv.setText(city);

        final int cityId = cursor.getInt(cursor.getColumnIndex("_id"));
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                SQLiteDatabase db = new CitiesDatabase(context).getWritableDatabase();
                if (checked){
                    db.execSQL("UPDATE Cities SET fav = 1 WHERE _id = "+cityId);
                }

                else{
                    db.execSQL("UPDATE Cities SET fav = 0 WHERE _id = "+cityId);
                }
                db.close();
            }
        });

        cb.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("fav"))!=0);
    }
}
