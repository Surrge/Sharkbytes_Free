package com.vie.sharkbytes.free;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;






public class HowToMenuActivity extends Activity {
	AdView adView;
	Button buttonType, buttonReasons, buttonPrevent, buttonDefense, buttonProtect, buttonImpact;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto_menu);
        this.setTitle("Avoiding Shark Attacks");
        
        buttonType = (Button) findViewById(R.id.buttonType);
        buttonReasons = (Button) findViewById(R.id.buttonReasons);
        buttonPrevent = (Button) findViewById(R.id.buttonPrevent);
        buttonDefense = (Button) findViewById(R.id.buttonDefense);
        buttonProtect = (Button) findViewById(R.id.buttonProtect);
        buttonImpact = (Button) findViewById(R.id.buttonImpact);
        
        buttonType.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HowToMenuActivity.this, HowToActivity.class);
				intent.putExtra("attack", "Types of attacks");
				startActivity(intent);
			}
		});
        
        buttonReasons.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HowToMenuActivity.this, HowToActivity.class);
				intent.putExtra("attack", "Reasons for attacks");
				startActivity(intent);
			}
		});
        
        buttonPrevent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HowToMenuActivity.this, HowToActivity.class);
				intent.putExtra("attack", "Prevention");
				startActivity(intent);
			}
		});
        
        buttonDefense.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HowToMenuActivity.this, HowToActivity.class);
				intent.putExtra("attack", "Self-defense");
				startActivity(intent);
			}
		});
        
        buttonProtect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HowToMenuActivity.this, HowToActivity.class);
				intent.putExtra("attack", "Dolphins' protection");
				startActivity(intent);
			}
		});
        
        buttonImpact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HowToMenuActivity.this, HowToActivity.class);
				intent.putExtra("attack", "Media impacts");
				startActivity(intent);
			}
		});

        // GoogleAnalytics, log screen and view
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getApplicationContext()) == ConnectionResult.SUCCESS) {
            Tracker t = ((Global) getApplication()).getTracker(Global.TrackerName.APP_TRACKER);
            t.setScreenName("Avoiding Attacks - Menu");
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        
        //AdMob
		adView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
			.build();
		adView.loadAd(adRequest);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//AdMob
		if (adView != null) {
			adView.resume();
	    }
	}
	
	@Override
	protected void onPause() {
		//AdMob
		if (adView != null) {
			adView.pause();
	    }
		
		super.onPause();
	}	
	
	@Override
	public void onDestroy() {
		//AdMob
		if (adView != null) {
			adView.destroy();
		}
		
		super.onDestroy();
	}
}
