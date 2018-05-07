package com.giomerito.projeto_final_android.controllers;

import android.content.Context;

import com.giomerito.projeto_final_android.models.Contato;

import java.util.List;

public class ContatoController {

    public ContatoController(Context context){
        //Integração com o banco de dados
    }

    public Boolean create(Contato contato){
        return true;
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
