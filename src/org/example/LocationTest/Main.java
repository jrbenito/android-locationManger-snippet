/* 
 * this package is based on examples obtained in the stackflow website
 * mainly http://stackoverflow.com/questions/938719/android-using-locationmanager-does-not-give-a-geo-fix
 * it is supposed to serve as proof of concept only. No warranty.
 *  
 */
package org.example.LocationTest;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity implements LocationListener{
    private LocationManager myManager;
    private TextView tv;
    private long deltaT = 0, lastT = 0, greaterDeltaT = 0;


    /********************************************************************** 
     * Activity overrides below 
     **********************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // get a handle to our TextView so we can write to it later
        tv = (TextView) findViewById(R.id.TextView01);

        // set up the LocationManager
        myManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onDestroy() {
        stopListening();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        stopListening();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startListening();
        super.onResume();
    }



    /**********************************************************************
     * helpers for starting/stopping monitoring of GPS changes below 
     **********************************************************************/
    private void startListening() {
        myManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 
            300000, 
            0, 
            this
        );
    }

    private void stopListening() {
        if (myManager != null)
            myManager.removeUpdates(this);
    }




    /**********************************************************************
     * LocationListener overrides below 
     **********************************************************************/
    public void onLocationChanged(Location location) {
    	long currentT = 0;
        // we got new location info.
    	currentT = location.getTime();
    	
    	if (lastT != 0) {
    		deltaT = currentT - lastT;
    	
    		// Calculate greater delta T
    		if ((deltaT > greaterDeltaT) && (deltaT != currentT)) {
    			greaterDeltaT = deltaT;
    		}
    	}
    	
    	//  lets display it in the textview
        String s = "";
        s += "Time     : "   + currentT + "\n";
        s += "Delta   T: "   + deltaT + "\n";
        s += "Greater T: "   + greaterDeltaT + "\n";
        s += "Provider: "    + location.getProvider() + "\n";
        s += "\tLatitude:  " + location.getLatitude()  + "\n";
        s += "\tLongitude: " + location.getLongitude() + "\n";
        s += "\tAccuracy:  " + location.getAccuracy()  + "\n";
                
        tv.setText(s);
        lastT = currentT;
    }    

    public void onProviderDisabled(String provider) {}    

    public void onProviderEnabled(String provider) {}    

    public void onStatusChanged(String provider, int status, Bundle extras) {}
}