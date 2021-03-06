package com.giomerito.projeto_final_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.giomerito.projeto_final_android.controllers.ContatoController;
import com.giomerito.projeto_final_android.models.Contato;
import com.giomerito.projeto_final_android.views.MainActivity;

import java.sql.SQLException;

public class CreateContatoOnClickListener implements View.OnClickListener{

    Context context;

    @Override
    public void onClick(View v) {

        context = v.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.activity_contato__form, null, false);

        final EditText editText_nome = formElementsView.findViewById(R.id.editText_nome);
        final EditText editText_email = formElementsView.findViewById(R.id.editText_email);
        final EditText editText_telefone = formElementsView.findViewById(R.id.editText_telefone);


        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Criar contato")
                .setPositiveButton("Incluir",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                    //Regras para incluir novos contatos
                                    String contatoNome = editText_nome.getText().toString();
                                    String contatoEmail = editText_email.getText().toString();
                                    String contatoTelefone = editText_telefone.getText().toString();

                                if (contatoNome.equals("")) {
                                    alerta("O Campo nome não pode ser vazio!");
                                } else if (contatoTelefone.equals("")) {
                                    alerta("O Campo telefone não pode ser vazio!");
                                } else {

                                    Contato contato = new Contato();
                                    contato.setNome(contatoNome);
                                    contato.setEmail(contatoEmail);
                                    contato.setTelefone(contatoTelefone);

                                    boolean criadoComSucesso = new ContatoController(context).create(contato);


                                    if (criadoComSucesso) {
                                        alerta("Contato incluído com sucesso.");

                                        //TODO O código abaixo esta apresentando erro na aplicação
                                        try {
                                            ((MainActivity) context).contadorDeRegistros();
                                            ((MainActivity) context).atualizarListaDeContatos();
                                        } catch (Exception e) {
                                            alerta("Os dados não foram atualizados \n para visualização.");
                                            e.printStackTrace();
                                        }

                                    } else {
                                        alerta("Não foi possivel incluir o contato.");
                                    }

                                    dialog.cancel();
                                }
                            }
                        }).show();
    }

    public void alerta(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
