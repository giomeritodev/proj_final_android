package com.giomerito.projeto_final_android.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.giomerito.projeto_final_android.database.DataBaseAdapter;
import com.giomerito.projeto_final_android.models.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoController extends DataBaseAdapter{

    public ContatoController(Context context){
        //Integração com o banco de dados
        super(context);
    }

    public Boolean create(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean isCreate = db.insert("contatos", null, values) > 0;
        db.close();

        return isCreate;
    }

    public int totalDeContatos(){

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM contatos";

        int contador = db.rawQuery(sql, null).getCount();

        return contador;
    }

    public List<Contato> listarContatos(){
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos ORDER by id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String nome = cursor.getString(cursor.getColumnIndex("nome"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String telefone = cursor.getString(cursor.getColumnIndex("telefone"));

                Contato contato = new Contato();
                contato.setId(id);
                contato.setNome(nome);
                contato.setEmail(email);
                contato.setTelefone(telefone);

                contatos.add(contato);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contatos;
    }

    public Contato buscarContatoId(int contatoId){

        Contato contato = new Contato();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM contatos WHERE id = " + contatoId;

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));

            contato.setId(contatoId);
            contato.setNome(nome);
            contato.setEmail(email);
            contato.setTelefone(telefone);
        }

        return contato;
    }

    public boolean update(Contato contato){
        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(contato.getId())};

        SQLiteDatabase db = this.getWritableDatabase();

        boolean isUpdate = db.update("contatos", values, where, whereArgs) > 0;

        db.close();

        return isUpdate;
    }

    public boolean delete(int contatoId){

        boolean isDelete = false;

        SQLiteDatabase db = this.getWritableDatabase();
        isDelete = db.delete("contatos", "id = '" + contatoId + "'", null) > 0;
        db.close();

        return isDelete;
    }
}
