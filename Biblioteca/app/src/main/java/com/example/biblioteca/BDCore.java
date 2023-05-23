package com.example.biblioteca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper{
    private static final String NOME_BD = "biblioteca";
    private static final int VERSAO_BD = 25;

    public BDCore(Context context){
        super(context, NOME_BD, null, VERSAO_BD);

    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table usuario(cpf integer not null  primary key, matricula integer not null unique, nome text not null, email text not null,  telefone integer not null, senha text not null)");
        bd.execSQL("create table livro(_idlivro integer primary key autoincrement, titulo text not null , edicao text, autor text, editora text, ano_public integer, n_pag integer, exemplares integer, disponivel integer)");
        bd.execSQL("create table reserva(_idreserva integer primary key autoincrement, cpf integer NOT NULL CONSTRAINT cpf REFERENCES usuario (cpf), _idlivro integer NOT NULL CONSTRAINT _idlivro references livro(_idlivro), dt_reserva text, dt_devolucao text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("drop table reserva");
        bd.execSQL("drop table usuario");
        bd.execSQL("drop table livro");
        onCreate(bd);
    }
}
