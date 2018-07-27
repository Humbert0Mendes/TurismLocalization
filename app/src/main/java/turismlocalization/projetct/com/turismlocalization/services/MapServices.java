package turismlocalization.projetct.com.turismlocalization.services;

import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Humberto on 27/07/2018.
 */

public class MapServices {

    public MapServices(){

        //Default Constructor
    }

    public void refreshMap(GoogleMap mMap, LatLng currentLatLong, LatLng destino){

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

    public void buscarEndereco(){

    }

}
