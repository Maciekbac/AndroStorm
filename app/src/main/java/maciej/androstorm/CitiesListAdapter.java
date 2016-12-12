package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class CitiesListAdapter extends CursorAdapter {


    public CitiesListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.cities_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView)view.findViewById(R.id.cli_city);
        String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
        tv.setText(city);
    }
}
