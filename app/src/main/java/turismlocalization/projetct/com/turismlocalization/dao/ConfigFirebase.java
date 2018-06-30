package turismlocalization.projetct.com.turismlocalization.dao;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Humberto on 27/06/2018.
 */

public class ConfigFirebase {

    private static DatabaseReference firebaseRef;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFirebaseRef(){
        if(firebaseRef ==null){
            firebaseRef = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseRef;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth== null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return  firebaseAuth;
    }
}
