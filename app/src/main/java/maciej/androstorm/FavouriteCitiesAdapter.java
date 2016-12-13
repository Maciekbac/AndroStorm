package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class FavouriteCitiesAdapter extends CursorAdapter {
    public FavouriteCitiesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.favourite_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView)view.findViewById(R.id.fli_city);
        String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
        tv.setText(city);
    }
}
