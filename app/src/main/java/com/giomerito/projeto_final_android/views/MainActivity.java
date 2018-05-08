package com.giomerito.projeto_final_android.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.giomerito.projeto_final_android.CreateContatoOnClickListener;
import com.giomerito.projeto_final_android.R;
import com.giomerito.projeto_final_android.controllers.ContatoController;
import com.giomerito.projeto_final_android.models.Contato;

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
}
