package turismlocalization.projetct.com.turismlocalization.controller;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import turismlocalization.projetct.com.turismlocalization.R;
import turismlocalization.projetct.com.turismlocalization.fragments.PesquisaFragment;

public class PesquisaFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_fragment);

        PesquisaFragment fragment = PesquisaFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.pesquisaFragment, fragment, "PesquisaFragment");
        ft.commit();
    }
}
