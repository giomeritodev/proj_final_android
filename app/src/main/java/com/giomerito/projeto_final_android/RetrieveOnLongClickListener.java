package com.giomerito.projeto_final_android;

import android.view.View;
import android.widget.Toast;

public class RetrieveOnLongClickListener implements View.OnLongClickListener{

    @Override
    public boolean onLongClick(View v) {

        Toast.makeText(v.getContext(), "Clicado em um item da lista", Toast.LENGTH_LONG).show();
        return true;
    }

}
