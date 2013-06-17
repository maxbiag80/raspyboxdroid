package com.raspyboxdroid;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {
    
	private static final String LOG_TAG = "RASPYBOXDROID";
	
	private static final String DEFAULT_IP_ADDRESS = "10.0.2.2";	// Emulator Host IP
	private static final int DEFAULT_PORT = 80;
	
	private static final String KEY_IP_ADDRESS = "ip_address";
	private static final String KEY_PORT = "port";
	
	private RaspyBoxWsClient raspyBoxWsClient;
	
	private Map<String, ButtonMapEntry> buttonMap;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);      
        this.initButtonMap();
        this.initClient();
    }

    public boolean onCreateOptionsMenu(Menu menu) {        
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
		
	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()) {
		case R.id.action_settings:
			showSettings();
			break;
		}		
		return super.onOptionsItemSelected(item);
	}	
				
	protected void onResume() {
		super.onResume();
		this.setRaspberryIPAddress();
	}

	private void showSettings() {
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		startActivity(intent);
	}	
	
	private void setRaspberryIPAddress() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);		
		TextView label = ((TextView)findViewById(R.id.lblIpAddress));
		String ip = prefs.getString(KEY_IP_ADDRESS, null);
		if (null != ip) {
			label.setText(this.getString(R.string.raspberrypi_address) + " : " + ip);	
		} else {
			label.setText(this.getString(R.string.raspberrypi_missing_address));
		}				
	}
	
	private void initClient() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.raspyBoxWsClient = new RaspyBoxWsClient();
		this.raspyBoxWsClient.setAddress(prefs.getString(KEY_IP_ADDRESS, DEFAULT_IP_ADDRESS));
		this.raspyBoxWsClient.setPort(prefs.getInt(KEY_PORT, DEFAULT_PORT));
		
		this.initButtons();
	}
	
	private void initButtons() {
		//TODO Leggere device da web service ed impostare i pulsanti
		
		for (int i = 1; i <= 4; i++) {
			this.initButton(this.buttonMap.get(String.valueOf(i)).getOnButtonId(), this.buttonMap.get(String.valueOf(i)).getOffButtonId(), i); 
		}
	}
	
	private void initButton(int onButtonId, int offButtonId, final int channel) {
		final Button onButton = (Button) findViewById(onButtonId);
		onButton.setOnClickListener(new OnClickListener() {						
			public void onClick(View v) {
				raspyBoxWsClient.powerOn(channel, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.d(LOG_TAG, response);
					}
					public void onFailure(Throwable t, String e) {
						Log.d(LOG_TAG, e);
					}
				});
				
			}
		});
		final Button offButton = (Button) findViewById(offButtonId);
		offButton.setOnClickListener(new OnClickListener() {						
			public void onClick(View v) {
				raspyBoxWsClient.powerOff(channel, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Log.d(LOG_TAG, response);
					}
					public void onFailure(Throwable t, String e) {
						Log.d(LOG_TAG, e);
					}
				});
				
			}
		});		
	}
	
	private void initButtonMap() {
		buttonMap = new HashMap<String, ButtonMapEntry>();
		buttonMap.put("1", new ButtonMapEntry(R.id.btnOnDevice1, R.id.btnOffDevice1));
		buttonMap.put("2", new ButtonMapEntry(R.id.btnOnDevice2, R.id.btnOffDevice2));
		buttonMap.put("3", new ButtonMapEntry(R.id.btnOnDevice3, R.id.btnOffDevice3));
		buttonMap.put("4", new ButtonMapEntry(R.id.btnOnDevice4, R.id.btnOffDevice4));
	}
	
}
