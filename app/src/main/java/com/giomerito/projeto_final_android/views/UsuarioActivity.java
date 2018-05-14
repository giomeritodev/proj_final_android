package com.giomerito.projeto_final_android.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.giomerito.projeto_final_android.R;
import com.giomerito.projeto_final_android.conexao.ConexaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UsuarioActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        inicializaComponents();
        eventoClicks();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFirebase.getFirebaseAuth();
        if(auth == null){
            alerta("Erro na conexão com o Firebase");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
        auth.signOut();
    }

    private static class ViewHolder{
        EditText editText_email;
        EditText editText_senha;
        Button button_cadastrar;
        Button button_cancelar;
    }

    private void inicializaComponents(){

        viewHolder.button_cancelar = findViewById(R.id.button_cadastro_usuario_cancelar);
        viewHolder.button_cadastrar = findViewById(R.id.button_cadastro_usuario_cadastrar);
        viewHolder.editText_email = findViewById(R.id.editText_cadastro_usuario_email);
        viewHolder.editText_senha = findViewById(R.id.editText_cadastro_usuario_senha);
    }

    private void eventoClicks(){

        viewHolder.button_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        viewHolder.button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaUser(v);
            }

        });
    }

    private void criaUser(View view){

        String edit_email = viewHolder.editText_email.getText().toString();
        String edit_senha = viewHolder.editText_senha.getText().toString();


        if(edit_email.equals("")){
            alerta("O campo email não pode ser vazio");
        }else if(edit_senha.equals("")){
            alerta("O campo senha não pode ser vazio");
        }else if(edit_senha.length() < 6){
            alerta("A senha precisa ter no minimo 6 caracteres");
        }else {

            auth.createUserWithEmailAndPassword(edit_email, edit_senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                alerta("Usuário cadastrado");
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                finish();
                                startActivity(intent);
                            } else {
                                alerta("Erro ao salvar o usuário");
                            }
                        }
                    });
        }
    }

    private void alerta(String mgs){
        Toast.makeText(this, mgs, Toast.LENGTH_LONG).show();
    }
}
