package maciej.androstorm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView tvCity = (TextView)view.findViewById(R.id.fli_city);
        String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
        tvCity.setText(city);

        TextView tvWarn = (TextView)view.findViewById(R.id.fli_noWarnings);
        TextView tvRp = (TextView)view.findViewById(R.id.fli_rain_p);
        tvRp.setVisibility(View.GONE);
        TextView tvRpv = (TextView)view.findViewById(R.id.fli_rain_p_v);
        tvRpv.setVisibility(View.GONE);
        TextView tvRt = (TextView)view.findViewById(R.id.fli_rain_t);
        tvRt.setVisibility(View.GONE);
        TextView tvRtv = (TextView)view.findViewById(R.id.fli_rain_t_v);
        tvRtv.setVisibility(View.GONE);
        TextView tvSp = (TextView)view.findViewById(R.id.fli_storm_p);
        tvSp.setVisibility(View.GONE);
        TextView tvSpv = (TextView)view.findViewById(R.id.fli_storm_p_v);
        tvSpv.setVisibility(View.GONE);
        TextView tvSt = (TextView)view.findViewById(R.id.fli_storm_t);
        tvSt.setVisibility(View.GONE);
        TextView tvStv = (TextView)view.findViewById(R.id.fli_storm_t_v);
        tvStv.setVisibility(View.GONE);
        int a_o = cursor.getInt(cursor.getColumnIndexOrThrow("a_o"));
        int a_b = cursor.getInt(cursor.getColumnIndexOrThrow("a_b"));
        if (a_o == 0 && a_b == 0)
            tvWarn.setVisibility(View.VISIBLE);
        else{
            tvWarn.setVisibility(View.GONE);
            if(a_o==1){
                tvRp.setVisibility(View.VISIBLE);
                tvRpv.setVisibility(View.VISIBLE);
                tvRt.setVisibility(View.VISIBLE);
                tvRtv.setVisibility(View.VISIBLE);
                int t_o = cursor.getInt(cursor.getColumnIndexOrThrow("t_o"));
                tvRtv.setText(String.valueOf(t_o));
                int p_o = cursor.getInt(cursor.getColumnIndexOrThrow("p_o"));
                tvRpv.setText(String.valueOf(p_o));
            }
            if(a_b==1){
                tvSp.setVisibility(View.VISIBLE);
                tvSpv.setVisibility(View.VISIBLE);
                tvSt.setVisibility(View.VISIBLE);
                tvStv.setVisibility(View.VISIBLE);
                int t_b = cursor.getInt(cursor.getColumnIndexOrThrow("t_b"));
                tvStv.setText(String.valueOf(t_b));
                int p_b = cursor.getInt(cursor.getColumnIndexOrThrow("p_b"));
                tvSpv.setText(String.valueOf(p_b));
            }
        }

    }
}
