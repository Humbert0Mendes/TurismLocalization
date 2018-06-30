package turismlocalization.projetct.com.turismlocalization.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import turismlocalization.projetct.com.turismlocalization.R;
import turismlocalization.projetct.com.turismlocalization.dao.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth autenticacao;
    DatabaseReference firebaseDatabase;
    FirebaseUser fireUser;
    FirebaseAuth.AuthStateListener listener;
    EditText edtLogin, edtSenha;
    Button btnLogar;
    String login;
    String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfigFirebase.getFirebaseAuth();
        edtLogin = findViewById(R.id.cpLogin);
        edtSenha = findViewById(R.id.cpSenha);
        btnLogar = findViewById(R.id.btnLogin);
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fireUser = firebaseAuth.getCurrentUser();

                if(fireUser !=null){
                    Log.i("Login Usuário", "Usuário está logado"+fireUser.getEmail().toString());

                }else{
                    Log.i("Login Usuário", "Usuário não foi logado");
                }

            }
         };

    }

    public void validaLogin(View view){

        login = edtLogin.getText().toString();
        senha = edtSenha.getText().toString();

        try {
            autenticacao.signInWithEmailAndPassword(login, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i("Login:", "Usuário logado com sucesso");
                        Intent it = new Intent(LoginActivity.this, TelaInicialActivity.class);
                        startActivity(it);
                        finish();
                        fireUser = autenticacao.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Bem vindo:" + fireUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("Login:", "Usuário  não foi logado");
                        Toast.makeText(LoginActivity.this, "Erro ao logar usuário, tente novamente" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Erro ao logar usuário:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        autenticacao.addAuthStateListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listener != null) {
            autenticacao.removeAuthStateListener(listener);
        }
    }

}
