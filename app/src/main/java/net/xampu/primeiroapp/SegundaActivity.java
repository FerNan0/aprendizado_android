package net.xampu.primeiroapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
        Intent intent = getIntent();

        Mano mano = (Mano) intent.getSerializableExtra("manoEditar");
        if(mano != null) {
            helper.preencheFormulario(mano);
        }
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

                if(mano.getId() != null) {
                    dao.altera(mano);
                } else {
                    dao.insereMano(mano);
                }

                dao.close();
                Context ctx = getApplicationContext();

                Intent intent = new Intent(ctx, PrimeiraActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

                b.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_check)
                        .setTicker("")
                        .setContentTitle("Efetuado com sucesso!")
                        .setContentText("Nome: " + mano.getNome() + "\n Esporte: " + mano.getEsporte())
                        .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                        .setContentIntent(contentIntent)
                        .setContentInfo("Info");


                NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, b.build());
                Toast.makeText(SegundaActivity.this, "Mano " + mano.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
