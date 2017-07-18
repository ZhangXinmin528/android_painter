package com.example.customview_simple;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.customview_simple.canvas.CanvasActivity;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initParamsAndValues();
    }

    private void initParamsAndValues() {
        mContext = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_canvas://Canvas
                Intent cancas = new Intent(mContext, CanvasActivity.class);
                startActivity(cancas);
                break;
            case R.id.menu_Paint://Paint

                break;
            case R.id.menu_Path://Path

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
