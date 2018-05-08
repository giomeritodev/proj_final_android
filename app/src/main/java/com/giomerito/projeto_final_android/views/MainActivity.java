package com.giomerito.projeto_final_android.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.giomerito.projeto_final_android.CreateContatoOnClickListener;
import com.giomerito.projeto_final_android.R;
import com.giomerito.projeto_final_android.controllers.ContatoController;
import com.giomerito.projeto_final_android.models.Contato;

public class MainActivity extends AppCompatActivity {

    private Button btnNovoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovoContato = findViewById(R.id.button_criarContato);
        btnNovoContato.setOnClickListener(new CreateContatoOnClickListener());
    }
}
