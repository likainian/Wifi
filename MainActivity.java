package com.example.dinghao.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvNet;
    private Button mBtGetNet;
    private LinearLayout mActivityMain;
    private Button mBtGetWifi;
    private ListView mLvWifi;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTvNet = (TextView) findViewById(R.id.tv_net);
        mBtGetNet = (Button) findViewById(R.id.bt_getNet);
        mBtGetNet.setOnClickListener(this);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mBtGetWifi = (Button) findViewById(R.id.bt_getWifi);
        mBtGetWifi.setOnClickListener(this);
        mLvWifi = (ListView) findViewById(R.id.lv_wifi);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getNet:
                if (isNetworkAvailable(this)) {
                    Toast.makeText(getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_getWifi:
                WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);  //获得系统wifi服务
                List<ScanResult> scanResults = wifiManager.getScanResults();
                for (int i = 0; i < scanResults.size(); i++) {
                    list.add(scanResults.get(i).SSID);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
                mLvWifi.setAdapter(adapter);
                break;
        }
    }
    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < networkInfo.length; i++) {
                    sb.append(i + ".状态:" + networkInfo[i].getState() + ",类型:" + networkInfo[i].getTypeName() + "\n");
                }
                mTvNet.setText(sb.toString());
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
