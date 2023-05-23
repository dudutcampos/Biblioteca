package com.example.biblioteca;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LivrosUsuario extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.livros_usuario);

        BD bd = new BD(this);
        Sessao sessao = new Sessao();

        boolean fundo_black = sessao.getFundo_black();

        LinearLayout l = findViewById(R.id.back);
        if(fundo_black){
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
        }else{
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo));
        }

        final String[][] livros = bd.livrosPorUsuario(sessao);

        ListView lista = (ListView) findViewById(R.id.idlistalivros);

        AdaptadorLista adapter = new AdaptadorLista(livros, this);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id){
                // Elemento selecionado = (Elemento) adapter.getItemAtPosition(pos);

                Intent intent = new Intent(LivrosUsuario.this, VerLivroUsuario.class);
                intent.putExtra("elemento", pos);
                startActivity(intent);
            }
        });

        ImageButton home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarTela();
                finish();
            }

            private void chamarTela(){
                startActivity(new Intent(LivrosUsuario.this, TelaUsuario.class)); //abre nova tela
            }
        });

        String[][] itensLista;
        itensLista = bd.livrosPorUsuario(sessao);
        AdaptadorLista A = new AdaptadorLista(itensLista, this);

        Button usuario_buscar = findViewById(R.id.usuario_buscar);

        if(A.getCount() > 0) {

            TextView sem_reserva = findViewById(R.id.sem_reserva);
            sem_reserva.setText("Livros Reservados");


            usuario_buscar.setVisibility(View.INVISIBLE);
        }

        usuario_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarTela();
                finish();
            }

            private void chamarTela(){
                startActivity(new Intent(LivrosUsuario.this, ListarLivros.class)); //abre nova tela
            }
        });

    }
}
