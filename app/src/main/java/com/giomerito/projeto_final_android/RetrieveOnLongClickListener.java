package com.giomerito.projeto_final_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.giomerito.projeto_final_android.controllers.ContatoController;
import com.giomerito.projeto_final_android.models.Contato;
import com.giomerito.projeto_final_android.views.MainActivity;

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
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }

    public void editContatoPeloId(final int contatoID){
        //Montar formulário já com os dados populados
        Toast.makeText(context, "Editando "+contatoID, Toast.LENGTH_LONG).show();

        final ContatoController contatoController = new ContatoController(context);
        final Contato contato = contatoController.buscarContatoId(contatoID);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View contatoForm = inflater.inflate(R.layout.activity_contato__form, null, false);

        //Popular nome, email e telefone com os dados da lista
        final EditText editText_nome = contatoForm.findViewById(R.id.editText_nome);
        final EditText editText_email = contatoForm.findViewById(R.id.editText_email);
        final EditText editText_telefone = contatoForm.findViewById(R.id.editText_telefone);

        editText_nome.setText(contato.getNome());
        editText_email.setText(contato.getEmail());
        editText_telefone.setText(contato.getTelefone());

        new AlertDialog.Builder(context).setView(contatoForm)
                .setTitle("Editar")
                .setPositiveButton("Atualizar dados", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Contato editContato = new Contato();
                        editContato.setId(contatoID);
                        editContato.setNome(editText_nome.getText().toString());
                        editContato.setEmail(editText_email.getText().toString());
                        editContato.setTelefone(editText_telefone.getText().toString());

                        boolean isUpdate = contatoController.update(editContato);

                        if(isUpdate){
                            Toast.makeText(context, "Dados atualizados com sucesso.", Toast.LENGTH_LONG).show();
                            try {
                                ((MainActivity) context).atualizarListaDeContatos();
                            }catch(Exception ex){
                                Toast.makeText(context, "A lista não de contatos não foi atualizada.", Toast.LENGTH_LONG).show();
                                ex.printStackTrace();
                            }
                        }else{
                            Toast.makeText(context, "Falha ao salvar contato", Toast.LENGTH_LONG).show();
                        }
                        dialog.cancel();
                    }
                }).show();
    }
}
