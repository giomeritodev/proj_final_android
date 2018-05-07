package com.giomerito.projeto_final_android.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.giomerito.projeto_final_android.database.DataBaseAdapter;
import com.giomerito.projeto_final_android.models.Contato;

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
        return 0;
    }

    public List<Contato> listarContatos(){
        return null;
    }

    public Contato buscarContatoId(int contatoId){
        return null;
    }

    public Boolean update(Contato contato){
        return true;
    }

    public Boolean delete(int contatoId){
        return true;
    }
}
