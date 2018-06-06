package net.xampu.primeiroapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.xampu.primeiroapp.modelo.Mano;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodolfo on 05/06/2018.
 */
public class ManoDAO extends SQLiteOpenHelper{

    public ManoDAO(Context context) {
        super(context, "opa", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE tb_Manos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, esporte TEXT NOT NULL, cidade TEXT, email TEXT, nota REAL);";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS tb_Manos;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insereMano(Mano mano) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nome", mano.getNome());
        dados.put("esporte", mano.getEsporte());
        dados.put("cidade", mano.getCidade());
        dados.put("email", mano.getEmail());
        dados.put("nota", mano.getNota());

        db.insert("tb_Manos", null, dados);
    }

    public List<Mano> buscaManos() {
        String sql = "SELECT * FROM tb_Manos;";

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql,null);

        List<Mano> manos = new ArrayList<Mano>();

        while (c.moveToNext()) {
            Mano mano = new Mano();

            mano.setId(c.getLong(c.getColumnIndex("id")));
            mano.setNome(c.getString(c.getColumnIndex("nome")));
            mano.setEsporte(c.getString(c.getColumnIndex("esporte")));
            mano.setCidade(c.getString(c.getColumnIndex("cidade")));
            mano.setEmail(c.getString(c.getColumnIndex("email")));
            mano.setNota(c.getDouble(c.getColumnIndex("nota")));

            manos.add(mano);
        }
        c.close();

        return manos;
    }

    public void deletar(Mano man) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {man.getId().toString()};
        db.delete("tb_Manos", "id = ?", params);
    }
}
