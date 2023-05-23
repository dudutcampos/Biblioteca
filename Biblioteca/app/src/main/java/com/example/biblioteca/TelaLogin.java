package com.example.biblioteca;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TelaLogin extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.usuario_login);

        Sessao sessao = new Sessao();

        boolean fundo_black = sessao.getFundo_black();

        LinearLayout l = findViewById(R.id.back);
        if(fundo_black){
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo_2));
        }else{
            l.setBackground(getResources().getDrawable(R.drawable.background_fundo));
        }

        if(sessao.getCpf() != null){

            sessao.setCpf(null);
            sessao.setSenha(null);
        }

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(logar()){
                    finish();
                    chamarTela();
                }
            }

            private void chamarTela() {
                startActivity(new Intent(getApplicationContext(), TelaUsuario.class)); //abre nova tela
            }
        });

    }

    //checando se o usuario esta no bd cahmando a função logar
    private boolean logar(){
        EditText edcpf = findViewById(R.id.edcpfLogin);
        EditText edsenha = findViewById(R.id.edSenhaLogin);
        boolean ex = true;
        if (edcpf.getText().toString().equals("")){
            edcpf.setError("Campo Obrigatorio");
            ex = false;
        }
        if (edsenha.getText().toString().equals("")){
            edsenha.setError("Campo Obrigatorio");
            ex = false;
        }

        if (ex == true){
            Usuario usuario = new Usuario();
            usuario.setCpf(edcpf.getText().toString());
            usuario.setSenha(edsenha.getText().toString());

            BD bd = new BD(this);
            boolean u = bd.logar(usuario);

            if(u){
                Toast.makeText(getApplicationContext(), "Usuario logado com sucesso", Toast.LENGTH_LONG).show();

                Usuario usu = bd.dadosUsuario(usuario);
                Sessao sessao = new Sessao();

                sessao.setCpf(usu.getCpf());
                sessao.setNome(usu.getNome());
                sessao.setEmail(usu.getEmail());
                sessao.setSenha(usu.getSenha());

                return true;
            }
            else{
                Toast.makeText(getApplicationContext(), "Usuario ou Senha Incorretos", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;
    }
}
