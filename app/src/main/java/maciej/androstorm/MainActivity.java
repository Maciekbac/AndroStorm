package maciej.androstorm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText c_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c_id = (EditText)findViewById(R.id.editText);
        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,WeatherActivity.class);
                int city_id = Integer.parseInt(c_id.getText().toString());
                i.putExtra("city",city_id);
                startActivity(i);
            }
        });
    }
}
