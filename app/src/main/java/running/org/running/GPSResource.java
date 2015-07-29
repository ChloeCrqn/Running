package running.org.running;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GPSResource extends Resource {
    private static final String LOG_TAG = "GPSResource";

    protected static GPSResource instance = null;

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    public static GPSResource getInstance() {
        Log.i(LOG_TAG, "getInstance -- begin");
        if (instance == null) {
            instance = new GPSResource();
        }
        return instance;
    }

    protected GPSResource() {
        Log.i(LOG_TAG, "GPSResource -- begin");
        locationManager = (LocationManager) RunningApp.applicationContext.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                setState(location);
            }

            @Override
            public void onProviderDisabled(final String provider) {
            }

            @Override
            public void onProviderEnabled(final String provider) {
            }

            @Override
            public void onStatusChanged(final String provider, final int status, final Bundle extras) {
            }
        };

        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public boolean isGPSenabled() {
        Log.i(LOG_TAG, "isGPSenabled -- begin");
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void destroy() {
        Log.i(LOG_TAG, "destroy -- begin");
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
        locationManager = null;
        locationListener = null;
        instance = null;
    }
}