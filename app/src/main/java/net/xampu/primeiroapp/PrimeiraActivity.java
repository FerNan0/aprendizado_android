package net.xampu.primeiroapp;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
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
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View item, int i, long l) {
                    Mano man = (Mano) lista.getItemAtPosition(i);
                    //Toast.makeText(PrimeiraActivity.this,"mano:" + man.getNome() + " " + man.getNota() + "!", Toast.LENGTH_SHORT).show();
                    Intent editar = new Intent(PrimeiraActivity.this, SegundaActivity.class);
                    editar.putExtra("manoEditar", man);
                    startActivity(editar);
                }
            });

            /*lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(PrimeiraActivity.this,"mano:clique longo!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });*/

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

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Mano man = (Mano) lista.getItemAtPosition(info.position);

        MenuItem site = menu.add("Pesquisar");

        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse("https://www.google.com.br/search?q=" + man.getNome() + " " + man.getEsporte()));
        site.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ManoDAO dao = new ManoDAO(PrimeiraActivity.this);

                dao.deletar(man);

                Toast.makeText(PrimeiraActivity.this,man.getEsporte() + " " + man.getNome() + " " + "Vacilao",Toast.LENGTH_SHORT).show();

                obtemListaManos();

                return false;
            }
        });
    }

}
