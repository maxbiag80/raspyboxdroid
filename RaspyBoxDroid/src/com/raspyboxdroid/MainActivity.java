package com.raspyboxdroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);        
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
		String ip = prefs.getString("ip_address", null);
		if (null != ip) {
			label.setText(this.getString(R.string.raspberrypi_address) + " : " + ip);	
		} else {
			label.setText(this.getString(R.string.raspberrypi_missing_address));
		}
				
	}
		
}
