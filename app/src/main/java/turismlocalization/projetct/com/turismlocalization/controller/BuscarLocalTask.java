package turismlocalization.projetct.com.turismlocalization.controller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;
import java.util.Locale;

/**
 * Created by Humberto on 26/07/2018.
 */

public class BuscarLocalTask extends AsyncTaskLoader<List<Address>> {

    Context mContext;
    String mLocal;
    List<Address> mfoundAddresses;

    public BuscarLocalTask (Context activity, String local) {
        super(activity);
        mContext = activity;
        mLocal = local;
    }

    @Override
     protected void onStartLoading(){
         if (mfoundAddresses ==null){
             forceLoad();
         }else{
             deliverResult(mfoundAddresses);
         }
        }

    @Nullable
    @Override
    public List<Address> loadInBackground() {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try{

            mfoundAddresses = geocoder.getFromLocationName(mLocal, 10);
        }catch(Exception e){
            e.printStackTrace();

        }

        return mfoundAddresses;
    }
}
