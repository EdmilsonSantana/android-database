package main.android_database.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import main.android_database.R;

public class QueryResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result);
        LinearLayout layout = (LinearLayout) findViewById(R.id.query_result);
        Intent intent = getIntent();
        ArrayList<CharSequence> data = intent.getCharSequenceArrayListExtra("data");

        for (CharSequence entry : data) {
            TextView text = new TextView(this);
            text.setText(entry);
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(text);
        }
    }
}
