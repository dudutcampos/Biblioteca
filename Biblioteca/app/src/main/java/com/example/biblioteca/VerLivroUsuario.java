package com.example.biblioteca;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerLivroUsuario extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.ver_livro_usuario);

        Intent intent = getIntent();

        final int posicao = (int) intent.getSerializableExtra("elemento");

        final BD bd = new BD(this);


        final Sessao sessao = new Sessao();

        final String[][] livros = bd.livrosPorUsuario(sessao);
        final int id = Integer.parseInt(livros[posicao][0]);

        final String[] data = bd.dt_livros(sessao, id);

        TextView tituloLivro = findViewById(R.id.tituloLivro);
        TextView autorLivro = findViewById(R.id.autorLivro);
        TextView editoraLivro = findViewById(R.id.editoraLivro);
        TextView edicaoLivro = findViewById(R.id.edicaoLivro);
        TextView ano_pubLivro = findViewById(R.id.ano_pubLivro);
        TextView n_pagLivro = findViewById(R.id.n_pagLivro);
        TextView disponivelLivro = findViewById(R.id.disponivelLivro);
        final TextView dt_reserva = findViewById(R.id.dt_reserva);
        final TextView dt_devolucao = findViewById(R.id.dt_devolocao);


        tituloLivro.setText(livros[posicao][1]);
        autorLivro.setText("Autor: " + livros[posicao][3]);
        editoraLivro.setText("Editora: " + livros[posicao][4]);
        edicaoLivro.setText("Edição: " + livros[posicao][2]);
        ano_pubLivro.setText("Publicação: " + livros[posicao][5]);
        n_pagLivro.setText("Paginas: " + livros[posicao][6] + "  ");
        disponivelLivro.setText("Disponivel: " + livros[posicao][8]);
        dt_reserva.setText("Reserva:  \n" + data[0]);
        dt_devolucao.setText("Devolução: " + data[1]);

        Button btn_devolver = findViewById(R.id.btn_devolver);
        Button btn_renovar = findViewById(R.id.btn_renovar);

        Date data1 = new Date();
        Date a = new Date();

        data1.setDate(a.getDate() + 7);
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd-MM-yyyy");


        final String dt_reserva1 = dataFormatada.format(a);
        final String dt_devolucao1 = dataFormatada.format(data1);

        btn_renovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd.renovarLivro(sessao, id, dt_reserva1, dt_devolucao1);
                dt_reserva.setText("Reserva: \n" + dt_reserva1);
                dt_devolucao.setText("Devolução: " + dt_devolucao1);
                Toast.makeText(getApplicationContext(), "Reserva Renovada: "+ dt_reserva1, Toast.LENGTH_LONG).show();
            }
        });

        btn_devolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bd.cancelarReserva(sessao, id);
                finish();
                chamarTela();

                Toast.makeText(getApplicationContext(), "Livro Devolvido", Toast.LENGTH_LONG).show();
            }

            private void chamarTela(){
                startActivity(new Intent(VerLivroUsuario.this, TelaUsuario.class)); //abre nova tela
            }
        });

        ImageButton home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                chamarTela();

            }

            private void chamarTela(){
                startActivity(new Intent(VerLivroUsuario.this, TelaUsuario.class)); //abre nova tela
            }
        });

        boolean fundo_black = sessao.getFundo_black();

        LinearLayout l = findViewById(R.id.back);
        if(fundo_black){
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
        }else{
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo));
        }
    }
}
