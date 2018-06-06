package net.xampu.primeiroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.xampu.primeiroapp.modelo.Mano;

public class SegundaActivity extends AppCompatActivity {

    private PrimeiraClass helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        helper = new PrimeiraClass(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_primeira, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_skate:
                Mano mano = helper.pegaMano();
                ManoDAO dao = new ManoDAO(this);
                dao.insereMano(mano);
                dao.close();

                Toast.makeText(SegundaActivity.this, "Mano " + mano.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
