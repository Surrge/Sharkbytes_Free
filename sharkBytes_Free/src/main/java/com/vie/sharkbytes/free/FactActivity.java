package com.vie.sharkbytes.free;

import java.util.ArrayList;

import org.json.JSONArray;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class FactActivity extends Activity {
	LinearLayout factLayout;
	AdView adView;
	ProgressDialog spinner;
	ArrayList<GetInfoTask> jsonTasks = new ArrayList<GetInfoTask>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);
        this.setTitle(R.string.fact_main_text);
        
        factLayout = (LinearLayout) findViewById(R.id.factLayout);

        // GoogleAnalytics, log screen and view
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getApplicationContext()) == ConnectionResult.SUCCESS) {
            Tracker t = ((Global) getApplication()).getTracker(Global.TrackerName.APP_TRACKER);
            t.setScreenName("Shark Facts");
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        
        //AdMob
        adView = (AdView) findViewById(R.id.adView);
        //adView.setAdListener(new ToastAdListener(this));
        AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	    	.build();
	    adView.loadAd(adRequest);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		//AdMob
		if (adView != null) {
			adView.resume();
	    }
		
		//Show Spinner
        spinner = new ProgressDialog(this);
		spinner.setMessage("Loading Facts...");
		spinner.show();
		
		//Get json data from server
		factLayout.removeAllViews();
        jsonTasks.add((GetInfoTask) new GetInfoTask(this).execute("getFacts", ""));
	}
	
	@Override
	protected void onStop() {
		//AdMob
		if (adView != null) {
			adView.pause();
	    }
		
		//Cancel threads while reference is valid
		for(GetInfoTask t: jsonTasks) {t.cancel(true);}
		jsonTasks.clear();
		
		super.onStop();
	}	
	
	@Override
	public void onDestroy() {
		//AdMob
		if (adView != null) {
			adView.destroy();
		}
		
		super.onDestroy();
	}
	
	public void onTaskFinish(GetInfoTask task, String data) {
		jsonTasks.remove(task);
		
		try {
			JSONArray json = new JSONArray(data);
			for(int i = 0; i < json.length(); i++) {
				//Add tile
				LayoutInflater inflater = LayoutInflater.from(this);
				View view = inflater.inflate(R.layout.fact_tile, null);
				TextView factText = (TextView) view.findViewById(R.id.textFact);
				factText.setText(json.getJSONObject(i).getString("fact"));
				
				factLayout.addView(view);
				factLayout.refreshDrawableState();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Problem retrieving data", Toast.LENGTH_LONG).show();
		}
		
		//Dismiss Spinner if done
		if(jsonTasks.isEmpty() && spinner != null && spinner.isShowing()) {
			spinner.dismiss();
		}
	}
}
