package com.giomerito.projeto_final_android.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giomerito.projeto_final_android.CreateContatoOnClickListener;
import com.giomerito.projeto_final_android.R;
import com.giomerito.projeto_final_android.RetrieveOnLongClickListener;
import com.giomerito.projeto_final_android.controllers.ContatoController;
import com.giomerito.projeto_final_android.models.Contato;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnNovoContato;
    private TextView textView_contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovoContato = findViewById(R.id.button_criarContato);
        btnNovoContato.setOnClickListener(new CreateContatoOnClickListener());

        contadorDeRegistros();
        atualizarListaDeContatos();

        //new ContatoController(this).buscarContatoId();
    }

    public void contadorDeRegistros() {
        String msg = "";
        int contador = new ContatoController(this).totalDeContatos();

        textView_contador = findViewById(R.id.textView_contador_contatos);

        if(contador == 0){
            msg = "Nenhum contato cadastrado.";
        }else if(contador == 1){
            msg = contador + " contato cadastrado.";
        }else{
            msg = contador + " contatos cadastrados.";
        }

        textView_contador.setText(msg);
    }

    public void atualizarListaDeContatos(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearlayout_objetos_contatos);
        linearLayoutRecords.removeAllViews();

        List<Contato> contatos = new ContatoController(this).listarContatos();

        if(contatos.size() > 0){
            for(Contato obj : contatos){
                int id = obj.getId();
                String nome = obj.getNome();
                String email = obj.getEmail();
                String telefone = obj.getTelefone();

                String textViewContents = nome + " - " + email + " - " + telefone;

                /*713268*/
                TextView textViewContatoItem = new TextView(this);
                textViewContatoItem.setPadding(0, 10, 0, 10);
                textViewContatoItem.setText(textViewContents);
                textViewContatoItem.setTag(Integer.toString(id));

                linearLayoutRecords.addView(textViewContatoItem);
                textViewContatoItem.setOnLongClickListener(new RetrieveOnLongClickListener());
            }
        }else{
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8,8,8,8);
            locationItem.setText("Nenhum cadastro cadastrato.");

            linearLayoutRecords.addView(locationItem);
        }
    }
}
