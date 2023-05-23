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

public class TelaCadastro extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.usuario_cadastro);

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

        Button btnCadastro = findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cadastro()) {
                    finish();
                    chamarTela();

                }
            }

            private void chamarTela() {
                startActivity(new Intent(getApplicationContext(), TelaUsuario.class)); //abre nova tela
            }
        });
    }

    //Inserindo usuario no bd com a função inserirUsuario
    public Boolean cadastro(){

        EditText matricula = findViewById(R.id.edMatricula);
        EditText cpf = findViewById(R.id.edCpf);
        EditText nome = findViewById(R.id.edNome);
        EditText email = findViewById(R.id.edEmail);
        EditText telefone = findViewById(R.id.edTelefone);
        EditText senha = findViewById(R.id.edSenha);
        EditText senha2 = findViewById(R.id.edSenha2);

        boolean ex = true;

        if (matricula.getText().toString().equals("")){
            matricula.setError("Campo Obrigatorio");
            ex = false;
        }
        if (cpf.getText().toString().equals("")){
            cpf.setError("Campo Obrigatorio");
            ex = false;
        }
        if (nome.getText().toString().equals("")){
            nome.setError("Campo Obrigatorio");
            ex = false;
        }
        if (email.getText().toString().equals("")){
            email.setError("Campo Obrigatorio");
            ex = false;
        }
        if (telefone.getText().toString().equals("")){
            telefone.setError("Campo Obrigatorio");
            ex = false;
        }
        if (senha.getText().toString().equals("")){
            senha.setError("Campo Obrigatorio");
            ex = false;
        }
        if (senha2.getText().toString().equals("")){
            senha2.setError("Campo Obrigatorio");
            ex = false;
        }

        if(senha.getText().toString().equals(senha2.getText().toString())){
            if (ex) {
                Usuario usuario = new Usuario();
                usuario.setCpf(cpf.getText().toString());
                usuario.setMatricula(matricula.getText().toString());
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setTelefone(telefone.getText().toString());
                usuario.setSenha(senha.getText().toString());

                BD bd = new BD(this);

                boolean u = bd.inserirUsuario(usuario);

                if(u == true){
                    Toast.makeText(getApplicationContext(), "Usuario Cadstrado com Sucesso", Toast.LENGTH_LONG).show();

                    Sessao sessao = new Sessao();

                    sessao.setCpf(cpf.getText().toString());
                    sessao.setSenha(senha.getText().toString());

                    sessao.setNome(nome.getText().toString());
                    sessao.setEmail(email.getText().toString());

                    return true;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Erro no cadastro de Usuario", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "Senhas Diferentes", Toast.LENGTH_LONG).show();
        }

        return false;
    }
}