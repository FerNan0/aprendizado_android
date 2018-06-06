package net.xampu.primeiroapp;

import android.widget.EditText;
import android.widget.RatingBar;

import net.xampu.primeiroapp.modelo.Mano;

/**
 * Created by Rodolfo on 05/06/2018.
 */
public class PrimeiraClass {
    private  EditText campoEmail;
    private  EditText campoCidade;
    private  EditText campoEsporte;
    private  EditText campoNome;
    private  RatingBar campoNota;

    private Mano mano;

    public PrimeiraClass(SegundaActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.nome_id);
        campoEsporte = (EditText) activity.findViewById(R.id.esporte_id);
        campoCidade = (EditText) activity.findViewById(R.id.cidade_id);
        campoEmail = (EditText) activity.findViewById(R.id.email_id);
        campoNota = (RatingBar) activity.findViewById(R.id.nota_id);
        mano = new Mano();
    }

    public Mano pegaMano() {
        mano.setNome(campoNome.getText().toString());
        mano.setEsporte(campoEsporte.getText().toString());
        mano.setCidade(campoCidade.getText().toString());
        mano.setEmail(campoEmail.getText().toString());
        mano.setNota(Double.valueOf(campoNota.getProgress()));

        return mano;
    }

    public void preencheFormulario(Mano mano) {
        campoCidade.setText(mano.getCidade());
        campoEmail.setText(mano.getEmail());
        campoEsporte.setText(mano.getEsporte());
        campoNome.setText(mano.getNome());
        campoNota.setProgress((int)mano.getNota());
        this.mano = mano;
    }
}
