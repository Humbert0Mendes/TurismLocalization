package turismlocalization.projetct.com.turismlocalization.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import turismlocalization.projetct.com.turismlocalization.R;
import turismlocalization.projetct.com.turismlocalization.dao.ConfigFirebase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText txtNome, txtIdade, txtContato, txtEmail, txtSenha;
    private Toolbar toolbar;
    private ProgressDialog progres;

    FirebaseAuth autenticacao;
    DatabaseReference firebaseDatabase = ConfigFirebase.getFirebaseRef();
    DatabaseReference usuarioReference = firebaseDatabase.child("usuarios");
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        autenticacao = ConfigFirebase.getFirebaseAuth();
        //Atribuição dos itens
        txtNome =  findViewById(R.id.txtNome);
        txtIdade = findViewById(R.id.txtIdade);
        txtContato = findViewById(R.id.txtContato);
        txtEmail = findViewById(R.id.txtmail);
        txtSenha = findViewById(R.id.txtSenha);
        progres = new ProgressDialog(this);
        progres.setIndeterminate(true);

        //Configurando Tollbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        setSupportActionBar(toolbar);


        //Implementação do listener
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fireUser = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        autenticacao.addAuthStateListener(listener);

    }

    @Override
    public void onStop(){
        super.onStop();
        if(listener!=null){
            autenticacao.removeAuthStateListener(listener);
        }
    }

    public void voltarLogin(View view) {
        Intent itLogin = new Intent(this, LoginActivity.class);
        startActivity(itLogin);
        finish();
    }
}
