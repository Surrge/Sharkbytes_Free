package com.vie.sharkbytes.free;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class SearchActivity extends Activity {
	AdView adView;
    WebView searchBeachView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(R.style.AppBaseTheme);   //ignore app style, bad text color
        setContentView(R.layout.activity_search);
        this.setTitle("Search Your Beach");

        searchBeachView = (WebView) findViewById(R.id.searchBeachView);
        searchBeachView.clearCache(true);
        searchBeachView.setBackgroundColor(getResources().getColor(R.color.white_trans));

        WebSettings webSettings = searchBeachView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        // GoogleAnalytics, log screen and view
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getApplicationContext()) == ConnectionResult.SUCCESS) {
            Tracker t = ((Global) getApplication()).getTracker(Global.TrackerName.APP_TRACKER);
            t.setScreenName("Search Your Beach");
            t.send(new HitBuilders.AppViewBuilder().build());
        }

        //AdMob
        adView = (AdView) findViewById(R.id.adView);
        //adView.setAdListener(new ToastAdListener(this));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

        searchBeachView.loadUrl("http://www.sharkbytes.co/searchyourbeach.php?lat=" + lastKnownLocation.getLatitude() + "&lon=" + lastKnownLocation.getLongitude());
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
