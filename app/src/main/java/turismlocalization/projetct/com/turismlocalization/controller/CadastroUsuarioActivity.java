package turismlocalization.projetct.com.turismlocalization.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import turismlocalization.projetct.com.turismlocalization.model.Usuarios;

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

    public void cadastroUser(View view){
        final String email = txtEmail.getText().toString();
        final String senha = txtSenha.getText().toString();
        final String idd = txtIdade.getText().toString();
        final Integer idade = Integer.parseInt(idd);
        final String usuario = txtNome.getText().toString();
        final String contato = txtContato.getText().toString();

        //Cadastro do usuário
        try {
            //Tratamento e validação no cadastro do usuário
            if (!email.isEmpty() && !senha.isEmpty() && !usuario.isEmpty()) {
                progres.setMessage("Cadastrando Usuário");
                progres.show();
                autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar usuário" + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            Usuarios user = new Usuarios();
                            FirebaseUser userFire = task.getResult().getUser();
                            user.criarUsuario(usuario, contato, idade, email, senha, user);
                            user.setId(userFire.getUid());
                            usuarioReference.child(user.getId()).setValue(user);
                            Intent itMain = new Intent(CadastroUsuarioActivity.this, TelaInicialActivity.class);
                            startActivity(itMain);
                            finish();
                        }
                        progres.dismiss();
                    }
                });
            } else {
                Toast.makeText(this, "Preencha todos os campos para concluir o cadastro", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "Não foi possível cadastrar usuário", Toast.LENGTH_LONG).show();
        }

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
