package com.giomerito.projeto_final_android.controllers;

import android.content.Context;
import android.content.SharedPreferences;

public class SecuratyPreferences {

               //Classe para encapsular os conceitos da SharedPreferences
        private final SharedPreferences mSharedPreferences;

        //Contrutor para uso das classe que chamar esta classe e precisará informar um Context
        public SecuratyPreferences(Context context){
            //o SharedPreferences é uma Interface por isto não instânceia por isto passamos uma chave para ser utilizada e com um context de modo privado
            this.mSharedPreferences = context.getSharedPreferences("Contato", Context.MODE_PRIVATE);

        }


    //Metodo para salvar um dado com SharedPreferences
        public void storeString(String key, String value){
            //Com isto consigo salvar um dado
            this.mSharedPreferences.edit().putString(key, value).apply();
        }

        //Metodo para recuperar um dado já incluso
        public String getStoreString(String key){
            return this.mSharedPreferences.getString(key, "");
        }


}
