package com.giomerito.projeto_final_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class RetrieveOnLongClickListener implements View.OnLongClickListener{

    Context context;
    String id;

    @Override
    public boolean onLongClick(View v) {

        context = v.getContext();
        id = v.getTag().toString();

        final CharSequence[] itens = {"Editar", "Deletar"};

        new AlertDialog.Builder(context).setTitle("Detalhes do contato")
                .setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            //Editar
                            editContatoPeloId(Integer.parseInt(id));
                        }else if(item == 1){
                            //Deletar
                        }
                    }
                }).show();

        Toast.makeText(v.getContext(), "Clicado em um item da lista", Toast.LENGTH_LONG).show();
        return false;
    }

    public void editContatoPeloId(int contatoID){


    }
}
