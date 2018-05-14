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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializaComponents();
        eventoClicks();
        setupAuth();
    }

    private void setupAuth() {
        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finishAffinity();
                    startActivity(intent);
                }else{
                    alerta("Logout");
                }
            }
        };
    }

    private void inicializaComponents() {
        mViewHolder.buttonCadastrarSe = findViewById(R.id.button_login_cadastra_se);
        mViewHolder.editText_email = findViewById(R.id.editText_login_email);
        mViewHolder.editText_senha = findViewById(R.id.editText_login_senha);
        mViewHolder.buttonLogar = findViewById(R.id.button_login_logar);
    }

    private void eventoClicks() {

        mViewHolder.buttonCadastrarSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });

        mViewHolder.buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              login(v);
            }
        });
    }

    public void login(View view){

            String email = mViewHolder.editText_email.getText().toString();
            String senha = mViewHolder.editText_senha.getText().toString();

            if(email.equals("")){
                alerta("O campo email não podem ser vazio");
            }else if(senha.equals("")){
                alerta("O campo senha não podem ser vazio");
            }else{
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                alerta("Failed to login");
                            }
                        }
                    });
            }
    }

    private static class ViewHolder{
        Button buttonCadastrarSe;
        Button buttonLogar;
        EditText editText_email;
        EditText editText_senha;
    }

    private void alerta(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //auth = FirebaseConection.getFirebaseAuth();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

}
