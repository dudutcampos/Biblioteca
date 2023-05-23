package com.example.biblioteca;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TelaUsuario extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        // não chame o super desse método
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.tela_usuario);

        TextView nomeUsuario = findViewById(R.id.nomeUsuario);

        final Sessao sessao = new Sessao();

        boolean fundo_black = sessao.getFundo_black();

        LinearLayout l = findViewById(R.id.back);
        if(fundo_black){
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
        }else{
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo));
        }

        nomeUsuario.setText(sessao.getNome());

        TextView email = findViewById(R.id.emailUsuario);

        email.setText(sessao.getEmail());

        BD bd = new BD(this);

        Button btn_listar_livros = findViewById(R.id.btn_Listar_livros);

        btn_listar_livros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarTela();
            }

            private void chamarTela(){
                startActivity(new Intent(getApplicationContext(), ListarLivros.class)); //abre nova tela
            }
        });

        Button btn_livros_reservados = findViewById(R.id.btn_livros_reservados);

        btn_livros_reservados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarTela();
            }

            private void chamarTela(){
                startActivity(new Intent(getApplicationContext(), LivrosUsuario.class)); //abre nova tela
            }
        });


        Button sair = findViewById(R.id.btn_sair);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessao.setCpf(null);
                sessao.setSenha(null);

                finish();

                chamarTela();
            }

            private void chamarTela(){
                startActivity(new Intent(getApplicationContext(), AtividadePrincipal.class)); //abre nova tela
            }
        });


    }
}
