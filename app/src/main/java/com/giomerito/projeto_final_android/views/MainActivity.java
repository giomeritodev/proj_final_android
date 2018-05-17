package com.giomerito.projeto_final_android.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.giomerito.projeto_final_android.CreateContatoOnClickListener;
import com.giomerito.projeto_final_android.R;
import com.giomerito.projeto_final_android.RetrieveOnLongClickListener;
import com.giomerito.projeto_final_android.conexao.ConexaoFirebase;
import com.giomerito.projeto_final_android.controllers.ContatoController;
import com.giomerito.projeto_final_android.models.Contato;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnNovoContato;
    private TextView textView_contador;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

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

    public void showPopup(View v)
    {
        PopupMenu popup = new PopupMenu(getBaseContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.itemMen_novo_contato){

        }else if(itemClicked == R.id.itemMenu_logout){
            Intent intent = new Intent(this, LoginActivity.class);
            auth.getInstance().signOut();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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

                String textViewContents = "Nome: " + nome + "\n" + "Email: " + email + "\n" + "Telefone: " + telefone;

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

    private void alerta(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.getInstance().signOut();
    }
}
