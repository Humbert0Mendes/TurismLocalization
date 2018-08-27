package turismlocalization.projetct.com.turismlocalization.fragments;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener {

    private GoogleMap mMap;
    private Marker currentLocation;
    private LatLng currentLatLong;
    private LatLng destino;
    Location location;
    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        //GPS Enable
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, this);
    }

    @Override
    public  void onPause(){
        super.onPause();

        //GPS Disable
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            location = new Location(provider);

            mMap = googleMap;
            mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);

        }catch (SecurityException err){
            Log.i("Error Map : ", "Erro ao iniciar GoogleMaps"+err);
        }

        if(location != null){
            currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
            refreshMap();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(), "Coordenadas :" + latLng.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        if(currentLocation !=null){
            currentLocation.remove();
        }
        currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLong);
        markerOptions.title("Você está aqui!");
        currentLocation = mMap.addMarker(markerOptions);

        final CameraPosition cameraPosition = new CameraPosition.Builder()
              .target(currentLatLong) //Location
              .zoom(17)             //Zoom
              .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void refreshMap(){

        mMap.clear();
        if(currentLatLong != null){
            mMap.addMarker(new MarkerOptions().position(currentLatLong).title("Você está aqui!"));
        }

        if(destino != null){
            mMap.addMarker(new MarkerOptions().position(destino).title("Destino"));
        }

        if(currentLatLong !=null){
            if(destino !=null){
                LatLngBounds area = new LatLngBounds.Builder().include(currentLatLong).include(destino).build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(area, 50));
            }else{
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 17.0f));
            }
        }
        //final CameraPosition cameraPosition = new CameraPosition.Builder()
        //        .target(currentLatLong) //Location
        //        .zoom(17)             //Zoom
        //        .build();
    }
}
