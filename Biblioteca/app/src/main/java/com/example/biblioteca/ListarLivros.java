package com.example.biblioteca;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

public class ListarLivros extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.listar_livros);

        BD bd = new BD(this);

        String[][] livros = bd.buscarLivro();

        ListView lista = (ListView) findViewById(R.id.idlistalivros);
        ImageButton home = findViewById(R.id.home);

        Sessao sessao = new Sessao();

        boolean fundo_black = sessao.getFundo_black();

        LinearLayout l = findViewById(R.id.back);
        if(fundo_black){
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
        }else{
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo));
        }

        if(sessao.getCpf() == null){
            home.setVisibility(View.INVISIBLE);
        }


        AdaptadorLista adapter = new AdaptadorLista(livros, this);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id){
               // Elemento selecionado = (Elemento) adapter.getItemAtPosition(pos);

                Intent intent = new Intent(ListarLivros.this, VerLivro.class);
                intent.putExtra("elemento", pos);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                chamarTela();
            }


            private void chamarTela(){
                startActivity(new Intent(ListarLivros.this, TelaUsuario.class)); //abre nova tela
            }
        });
    }


}
