package com.zimo.guo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zimo.guo.service.FloatWindowOptimizationService;
import com.zimo.guo.service.FloatWindowService;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button open;
    private Button close;
    private Button openOther;
    private Button closeOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open = (Button) findViewById(R.id.open_float_window);
        close = (Button) findViewById(R.id.close_float_window);
        openOther = (Button) findViewById(R.id.open_float_window_other);
        closeOther = (Button) findViewById(R.id.close_float_window_other);

        open.setOnClickListener(this);
        close.setOnClickListener(this);
        openOther.setOnClickListener(this);
        closeOther.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_float_window:
                Intent open = new Intent(MainActivity.this, FloatWindowService.class);
                startService(open);
                break;
            case R.id.close_float_window:
                Intent close = new Intent(MainActivity.this, FloatWindowService.class);
                stopService(close);
                break;
            case R.id.open_float_window_other:
                Intent start = new Intent(MainActivity.this, FloatWindowOptimizationService.class);
                startService(start);
                break;
            case R.id.close_float_window_other:
                Intent stop = new Intent(MainActivity.this, FloatWindowOptimizationService.class);
                stopService(stop);
                break;
        }

    }
}
