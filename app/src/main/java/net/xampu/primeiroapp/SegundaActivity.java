package net.xampu.primeiroapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse("https://www.google.com.br/search?q=" + mano.getNome() + " " + mano.getEsporte()));


                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intentSite, PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

                NotificationCompat.Builder notification = b.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_check)
                        .setTicker("")
                        .setContentTitle("Efetuado com sucesso!")
                        .setContentText("Nome: " + mano.getNome() + "\n Esporte: " + mano.getEsporte())
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                        .setContentIntent(contentIntent)
                        .setContentInfo("Info")
                        .setAutoCancel(true);

                NotificationCompat.Action actionProcurar = new NotificationCompat.Action.Builder(R.drawable.ic_browser, "Procurar",pendingIntent).build();

                notification.addAction(actionProcurar);

                Intent intentDelete = new Intent(this, NotificationActionReceiver.class);
                intentDelete.setAction("delete");
                intentDelete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, b.build());
                Toast.makeText(SegundaActivity.this, "Mano " + mano.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }


    public class NotificationActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equalsIgnoreCase("delete")) {

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(11111);

            }
        }
    }
}
