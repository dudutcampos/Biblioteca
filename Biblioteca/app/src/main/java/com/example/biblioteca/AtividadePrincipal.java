package com.example.biblioteca;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AtividadePrincipal extends AppCompatActivity {

    private Button btn_login,btn_fazer_cadastro, btn_listar_livros;

    @Override
    public void onBackPressed() {
        // não chame o super desse método
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.atividade_principal);

        verificarLivrosBD();
        final Sessao sessao = new Sessao();
        btn_login = (Button)findViewById(R.id.btn_login );
        btn_fazer_cadastro = (Button)findViewById(R.id.btn_cadastro);
        btn_listar_livros = (Button)findViewById(R.id.btn_Listar_livros);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarTela();


            }
            private void chamarTela(){
                startActivity(new Intent(AtividadePrincipal.this, TelaLogin.class)); //abre nova tela
            }
        });

        btn_fazer_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarTela();


            }

            private void chamarTela(){
                startActivity(new Intent(AtividadePrincipal.this, TelaCadastro.class)); //abre nova tela
            }
        });

        btn_listar_livros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sessao sessao = new Sessao();

                if(sessao.getCpf() != null){

                    sessao.setCpf(null);
                    sessao.setSenha(null);
                }

                chamarTela();
            }

            private void chamarTela(){
                startActivity(new Intent(AtividadePrincipal.this, ListarLivros.class)); //abre nova tela
            }
        });



        Switch sw_dark = findViewById(R.id.sw_dark);
        sw_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout l = findViewById(R.id.back);
                if(isChecked){
                    sessao.setFundo_black(true);
                    l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
                }else{
                    sessao.setFundo_black(false);
                    l.setBackground(getResources().getDrawable(R.drawable.background_fundo));
                }
            }
        });

        if(sessao.getFundo_black()){
            LinearLayout l = findViewById(R.id.back);
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
            sw_dark.setChecked(true);
        }

    }

    private List<Livro> livro = new ArrayList<>();

    private void lerArquivosCSV(){
        InputStream is = getResources().openRawResource(R.raw.livro);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            //retirando a primeira linha
            reader.readLine();

            //recriar o arquivo livros.csv com outro separador
            while((line = reader.readLine()) != null){
                Log.d("MyActivity", "Line: "  + line);
                //dividindo a linha ';'
                String[] tokens = line.split("§");

                // lendo o arquivo csv
                Livro semple = new Livro();

                if(tokens.length >= 3 && tokens[1].length() > 0) {
                    semple.setTitulo(tokens[1]);
                }else{
                    semple.setTitulo(tokens[1]);
                }

                if(tokens.length >= 3 && tokens[2].length() > 0) {
                    semple.setEdicao(tokens[2]);
                }else {
                    semple.setEdicao("N/A");
                }

                if(tokens.length >= 3 && tokens[3].length() > 0) {
                    semple.setAutor(tokens[3]);
                }else {
                    semple.setAutor("N/A");
                }

                if(tokens.length >= 3 && tokens[4].length() > 0) {
                    semple.setEditora(tokens[4]);
                }else {
                    semple.setEditora("N/A");
                }

                if(tokens.length >= 3 && tokens[5].length() > 0) {
                    semple.setAno_public(Integer.parseInt(tokens[5]));
                }else {
                    semple.setAno_public(0);
                }

                if(tokens.length >= 3 && tokens[7].length() > 0) {
                    semple.setN_pag(Integer.parseInt(tokens[7]));
                }else {
                    semple.setN_pag(0);
                }

                if(tokens.length >= 3 && tokens[7].length() > 0) {
                    semple.setExemplares(Integer.parseInt(tokens[9]));
                }else {
                    semple.setExemplares(0);
                }

                int disponivel = Integer.parseInt(tokens[9]) -1;

                semple.setDisponivel(disponivel);

                livro.add(semple);

                Log.d("MyActivity", "just created: " + semple);
            }

        }catch (IOException e) {
            Log.wtf("MyActivity", "Error reding data file on line" + line, e);
            e.printStackTrace();
        }

    }

    private void verificarLivrosBD(){
        BD bd = new BD(this);

        String[][] itensLista;
        itensLista = bd.buscarLivro();
        AdaptadorLista A = new AdaptadorLista(itensLista, this);


        //TODO ler arquivo OK
        if(A.getCount() == 0) {
            lerArquivosCSV();//funcao para ler o arquivo csv

            for (int i = 0; i <= 265; i++) {
                bd.inserirLivro(livro.get(i));
                i++;
            }
        }
    }
}
