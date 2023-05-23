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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class VerLivro extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.ver_livro);

        Intent intent = getIntent();
        final int posicao = (int) intent.getSerializableExtra("elemento");

        final BD bd = new BD(this);

        final String[][] livros = bd.buscarLivro();

        TextView tituloLivro = findViewById(R.id.tituloLivro);
        TextView autorLivro = findViewById(R.id.autorLivro);
        TextView editoraLivro = findViewById(R.id.editoraLivro);
        TextView edicaoLivro = findViewById(R.id.edicaoLivro);
        TextView ano_pubLivro = findViewById(R.id.ano_pubLivro);
        TextView n_pagLivro = findViewById(R.id.n_pagLivro);
        TextView disponivelLivro = findViewById(R.id.disponivelLivro);

        final int _idlivro = Integer.parseInt(livros[posicao][0]);

        tituloLivro.setText(livros[posicao][1]);
        autorLivro.setText("Autor: " + livros[posicao][3]);
        editoraLivro.setText("Editora: " + livros[posicao][4]);
        edicaoLivro.setText("Edição: " + livros[posicao][2]);
        ano_pubLivro.setText("Publicação: " + livros[posicao][5]);
        n_pagLivro.setText("Paginas: " + livros[posicao][6] + "  ");
        disponivelLivro.setText("Disponivel: " + livros[posicao][8]);

        Date a = new Date();

        Date data = new Date();
        data.setDate(a.getDate() + 7);
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd-MM-yyyy");


        final String dt_reserva = dataFormatada.format(a);
        final String dt_devolucao = dataFormatada.format(data);



        final Button reserva = findViewById(R.id.btn_reservar);

        final Sessao sessao = new Sessao();
        String cpf = sessao.getCpf();
        String senha = sessao.getSenha();


        int disponivel = bd.verificarDisponivel(_idlivro);

        if(cpf == null && senha == null){
            reserva.setVisibility(View.INVISIBLE);

        }

        if (disponivel >= 1){

            String[][] itensLista;
            itensLista = bd.livrosPorUsuario(sessao);
            AdaptadorLista A = new AdaptadorLista(itensLista, this);


            if(A.getCount() >= 3) {
                reserva.setText("Limite de Reservas Alcançado");
                reserva.setEnabled(false);
            }

            if(cpf == null && senha == null){
                reserva.setVisibility(View.INVISIBLE);

            }else {

                if(bd.proucurarReserva(sessao, _idlivro)){
                    reserva.setText("Reservado");
                    reserva.setEnabled(false);
                }

                reserva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(bd.reservarLivro(sessao, _idlivro, dt_reserva, dt_devolucao)) {
                            reserva.setText("Reservado");
                            reserva.setEnabled(false);

                            Toast.makeText(getApplicationContext(), "Reservado", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }else{
            reserva.setText("Exemplar indisponivel");
            reserva.setEnabled(false);
        }

        ImageButton home = findViewById(R.id.home);


        if(sessao.getCpf() == null){
            home.setVisibility(View.INVISIBLE);
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                chamarTela();

            }

            private void chamarTela(){
                startActivity(new Intent(VerLivro.this, TelaUsuario.class)); //abre nova tela
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
