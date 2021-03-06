package turismlocalization.projetct.com.turismlocalization.controller;

import android.app.ProgressDialog;
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

    private ProgressDialog progres;
    FirebaseAuth autenticacao;
    DatabaseReference firebaseDatabase;
    FirebaseUser fireUser;
    FirebaseAuth.AuthStateListener listener;
    private EditText edtLogin, edtSenha;
    private Button btnLogar;
    private String login;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfigFirebase.getFirebaseAuth();
        edtLogin = findViewById(R.id.cpLogin);
        edtSenha = findViewById(R.id.cpSenha);
        btnLogar = findViewById(R.id.btnLogin);
        progres = new ProgressDialog(this);
        progres.setIndeterminate(true);
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
            progres.setMessage("Logando Usuário");
            progres.show();
            autenticacao.signInWithEmailAndPassword(login, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("Login:", "Usuário logado com sucesso");
                            Intent it = new Intent(LoginActivity.this, TelaInicialActivity.class);
                            startActivity(it);
                            finish();
                            progres.dismiss();
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

    public void naoTemConta(View view) {
        Intent it = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(it);
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
