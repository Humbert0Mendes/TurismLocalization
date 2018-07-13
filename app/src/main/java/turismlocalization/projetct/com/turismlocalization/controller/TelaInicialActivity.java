package turismlocalization.projetct.com.turismlocalization.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Map;

import turismlocalization.projetct.com.turismlocalization.R;
import turismlocalization.projetct.com.turismlocalization.dao.ConfigFirebase;
import turismlocalization.projetct.com.turismlocalization.fragments.MapsFragment;
import turismlocalization.projetct.com.turismlocalization.model.Usuarios;

public class TelaInicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private IntentIntegrator qrScan;
    FirebaseAuth firebaseAuth = ConfigFirebase.getFirebaseAuth();
    FirebaseUser fireUser;
    DatabaseReference firebaseRef = ConfigFirebase.getFirebaseRef();
    DatabaseReference userRef = firebaseRef.child("usuarios");
    DatabaseReference userReferencia;
    TextView nameUser;
    TextView mailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameUser = (TextView) findViewById(R.id.txtNameUser);
        mailUser = (TextView) findViewById(R.id.txtMailUser);

        //Layout MenuLateral
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Autenticação Firebase
        firebaseAuth= ConfigFirebase.getFirebaseAuth();
        fireUser = firebaseAuth.getCurrentUser();
        String idUser = fireUser.getUid().toString();
        userReferencia = userRef.child(idUser);

        //Fragments GoogleMaps
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerMaps, new MapsFragment(), "MapsFrament");
        fragmentTransaction.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios user = dataSnapshot.getValue(Usuarios.class);
                //Map<String, Object> mapa = (Map<String, Object>) user;
                Log.i("USUARIO", "USER"+user.nome);
                Log.i("USUARIO", "USER"+user.email);
                mailUser.setText("123");
                nameUser.setText("Novo");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TelaInicialActivity.this, "Erro:"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        userReferencia.addValueEventListener(userListener);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cameraMenu) {

           IntentIntegrator integrator = new IntentIntegrator(this);
           integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.initiateScan();

            // Handle the camera action
        } else if (id == R.id.configMenu) {

        } else if (id == R.id.logoutMenu) {
            firebaseAuth.signOut();
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Você cancelou o scan", Toast.LENGTH_LONG).show();
            }
            else{
                String teste = result.getContents();
                Uri uri = Uri.parse(teste);
                Intent intent = new Intent(Intent.ACTION_DEFAULT, uri);
                startActivity(intent);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
