package turismlocalization.projetct.com.turismlocalization.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import turismlocalization.projetct.com.turismlocalization.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesquisaFragment extends Fragment {

    TextView txtDest, txtPart;
    Button btnBuscar;


    public PesquisaFragment() {
        // Required empty public constructor
    }

    public static PesquisaFragment newInstance(){

        PesquisaFragment fragment = new PesquisaFragment();
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_pesquisa, container, false);

        txtPart = layout.findViewById(R.id.txtPartida);
        txtDest = layout.findViewById(R.id.txtDestino);
        return layout;
    }

}
