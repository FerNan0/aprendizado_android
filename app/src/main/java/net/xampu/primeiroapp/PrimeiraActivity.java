package net.xampu.primeiroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import net.xampu.primeiroapp.modelo.Mano;

import java.util.List;

public class PrimeiraActivity extends AppCompatActivity {

    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira);

        lista = (ListView) findViewById(R.id.lista);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent segue_add = new Intent(PrimeiraActivity.this, SegundaActivity.class);
                startActivity(segue_add);
            }
        });
        registerForContextMenu(lista);
    }

    private void obtemListaManos() {
        ManoDAO dao = new ManoDAO(this);

        List<Mano> manos = dao.buscaManos();
        dao.close();

        lista = (ListView) findViewById(R.id.lista);
        ArrayAdapter<Mano> adpt = new ArrayAdapter<Mano>(this, android.R.layout.simple_list_item_1, manos);
        lista.setAdapter(adpt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtemListaManos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Mano man = (Mano) lista.getItemAtPosition(info.position);
                ManoDAO dao = new ManoDAO(PrimeiraActivity.this);

                dao.deletar(man);

                Toast.makeText(PrimeiraActivity.this,man.getEsporte() + " " + man.getNome() + " " + "Vacilao",Toast.LENGTH_SHORT).show();

                obtemListaManos();

                return false;
            }
        });
    }

}
