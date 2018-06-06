package net.xampu.primeiroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.xampu.primeiroapp.modelo.Mano;

import java.util.List;

public class PrimeiraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent segue_add = new Intent(PrimeiraActivity.this, SegundaActivity.class);
                startActivity(segue_add);
            }
        });
    }

    private void obtemListaManos() {
        ManoDAO dao = new ManoDAO(this);

        List<Mano> manos = dao.buscaManos();
        dao.close();

        final ListView lista = (ListView) findViewById(R.id.lista);
        ArrayAdapter<Mano> adpt = new ArrayAdapter<Mano>(this, android.R.layout.simple_list_item_1, manos);
        lista.setAdapter(adpt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtemListaManos();
    }
}
