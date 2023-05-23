package com.example.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BD{
    private SQLiteDatabase bd;

    public BD(Context context){
        BDCore auxBD = new BDCore(context);
        bd = auxBD.getWritableDatabase();
    }

    public boolean inserirUsuario(Usuario usuario){

        if(verificarUsuario(usuario)) {
            Log.d("inserirUsuario", "Erro ao inserir o usuario inserir o usuario");
            return false;
        }else {

            ContentValues valores = new ContentValues();

            valores.put("cpf", usuario.getCpf());
            valores.put("matricula", usuario.getMatricula());
            valores.put("nome", usuario.getNome());
            valores.put("email", usuario.getEmail());
            valores.put("telefone", usuario.getTelefone());
            valores.put("senha", usuario.getSenha());
            bd.insert("usuario", null, valores);

            Log.d("inserirUsuario", "acabei de inserir o usuario");
            return true;
        }
    }

    public boolean logar(Usuario usuario){

        String[] colunas = new String[]{"cpf", "matricula", "nome", "email", "telefone", "senha"};

        Cursor cursor = bd.query("usuario", colunas, "cpf = ? and senha = ?", new String[]{"" + usuario.getCpf(), usuario.getSenha()}, null, null, null);

        if (cursor.getCount() >= 1){

            cursor.moveToFirst();
            usuario.setCpf(String.valueOf(Integer.parseInt(String.valueOf(cursor.getShort(0)))));
            usuario.setMatricula(String.valueOf(Integer.parseInt(String.valueOf(cursor.getShort(1)))));
            usuario.setNome(cursor.getString(2));
            usuario.setEmail(cursor.getString(3));
            usuario.setTelefone(String.valueOf(Integer.parseInt(String.valueOf(cursor.getShort(4)))));
            usuario.setSenha(cursor.getString(5));
            return true;
        }
        else{

            return false;
        }
    }

    public boolean verificarUsuario(Usuario usuario){

        String[] colunas = new String[]{"cpf"};

        Cursor cursor = bd.query("usuario", colunas, "cpf = ?", new String[]{"" + usuario.getCpf()}, null, null, null);

        if (cursor.getCount() > 0){

            return true;
        }
        else{
            return false;
        }
    }

    public Usuario dadosUsuario(Usuario usuario){

        String[] colunas = new String[]{"cpf", "matricula", "nome", "email", "telefone", "senha"};

        Cursor cursor = bd.query("usuario", colunas, "cpf = ? and senha = ?", new String[]{"" + usuario.getCpf(), usuario.getSenha()}, null, null, null);

        if (cursor.getCount() >= 1){

            cursor.moveToFirst();
            usuario.setCpf(String.valueOf(Integer.parseInt(String.valueOf(cursor.getShort(0)))));
            usuario.setMatricula(String.valueOf(Integer.parseInt(String.valueOf(cursor.getShort(1)))));
            usuario.setNome(cursor.getString(2));
            usuario.setEmail(cursor.getString(3));
            usuario.setTelefone(String.valueOf(Integer.parseInt(String.valueOf(cursor.getShort(4)))));
            usuario.setSenha(cursor.getString(5));
            return usuario;
        }
        else{

            return usuario;
        }
    }

    public void inserirLivro(Livro livro){
        ContentValues valores = new ContentValues();

        valores.put("titulo", livro.getTitulo());
        valores.put("edicao", livro.getEdicao());
        valores.put("autor", livro.getAutor());
        valores.put("editora", livro.getEditora());
        valores.put("ano_public", livro.getAno_public());
        valores.put("n_pag", livro.getN_pag());
        valores.put("exemplares", livro.getExemplares());
        valores.put("disponivel", livro.getDisponivel());

        bd.insert("livro", null, valores);
    }

    public String[][] buscarLivro(){

        String[] colunas = new String[]{"_idlivro", "titulo", "edicao", "autor", "editora", "ano_public", "n_pag", "exemplares", "disponivel"};
        Cursor cursor = bd.query("livro", colunas, null, null,  null, null, "_idlivro ASC");
        String[][] livros = new String[cursor.getCount()][9];

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            int i = 0;
            do{
                livros[i][0] = String.valueOf(cursor.getShort(0));
                livros[i][1] = cursor.getString(1);
                livros[i][2] = cursor.getString(2);
                livros[i][3] = cursor.getString(3);
                livros[i][4] = cursor.getString(4);
                livros[i][5] = String.valueOf(cursor.getShort(5));
                livros[i][6] = String.valueOf(cursor.getShort(6));
                livros[i][7] = String.valueOf(cursor.getShort(7));
                livros[i][8] = String.valueOf(cursor.getShort(8));

                i++;

            }while (cursor.moveToNext());
        }

        return livros;
    }
    //TODO tudo errado

    public String[][] livrosPorUsuario(Sessao sessao) {
        Cursor cursor = bd.rawQuery("select * from livro L inner join reserva R using(_idlivro) inner join usuario U using(cpf) where R._idlivro = L._idlivro and cpf = "+ sessao.getCpf()+ " order by _idreserva asc", null);

        String[][] livros = new String[cursor.getCount()][9];

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            int i = 0;
            do{
                livros[i][0] = String.valueOf(cursor.getShort(0));
                livros[i][1] = cursor.getString(1);
                livros[i][2] = cursor.getString(2);
                livros[i][3] = cursor.getString(3);
                livros[i][4] = cursor.getString(4);
                livros[i][5] = String.valueOf(cursor.getShort(5));
                livros[i][6] = String.valueOf(cursor.getShort(6));
                livros[i][7] = String.valueOf(cursor.getShort(7));
                livros[i][8] = String.valueOf(cursor.getShort(8));

                i++;

            }while (cursor.moveToNext());
        }
        return livros;
    }

    public String[] dt_livros(Sessao sessao, int id){
        Cursor cursor = bd.rawQuery("select dt_reserva, dt_devolucao from livro L inner join reserva R using(_idlivro) inner join usuario U using(cpf) where R._idlivro = L._idlivro and cpf = "+ sessao.getCpf()+ " and _idlivro = " + id, null);

        String[] data = new String[2];

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            data[0] = cursor.getString(0);
            data[1] = cursor.getString(1);
        }
        return data;
    }

    public boolean reservarLivro(Sessao sessao, int _idlivro, String dt_reserva, String dt_devolucao){

        Cursor cursor = bd.rawQuery("insert into reserva(_idlivro, cpf, dt_reserva, dt_devolucao) values(?, ?, ?, ?)", new String[]{"" + _idlivro, sessao.getCpf(), dt_reserva, dt_devolucao});

        int n = verificarDisponivel(_idlivro);
        n = n - 1;

        ContentValues valores = new ContentValues();
        valores.put("disponivel", n);

        bd.update("livro", valores, "_idlivro = " + _idlivro, null);
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return true;
        }
    }

    public boolean renovarLivro(Sessao sessao, int _idlivro, String dt_reserva, String dt_devolucao){

        Cursor cursor = bd.rawQuery("update reserva set dt_reserva = ?, dt_devolucao = ? where cpf = ? and _idlivro = ?", new String[]{"" + dt_reserva, dt_devolucao, sessao.getCpf(), String.valueOf(_idlivro)});

        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return true;
        }
    }

    public int verificarDisponivel(int _idlivro){

        Cursor cursor = bd.rawQuery("select * from livro where _idlivro = " + _idlivro, null);

        cursor.moveToFirst();

        int disponivel = cursor.getShort(8);

        return disponivel;
    }

    public boolean proucurarReserva(Sessao sessao, int _idlivro){

        Cursor cursor = bd.rawQuery("select _idlivro from reserva where _idlivro = ? and cpf = ?", new String[]{"" + _idlivro, sessao.getCpf()});

        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    public void cancelarReserva(Sessao sessao, int id){

        Cursor cursor = bd.rawQuery("delete from reserva where _idlivro = ? and cpf = ?", new String[]{"" + id, sessao.getCpf()});


        int n = verificarDisponivel(id);
        n = n + 1;

        ContentValues valores = new ContentValues();
        valores.put("disponivel", n);

        bd.update("livro", valores, "_idlivro = " + id, null);

        if (cursor.getCount() > 0){

        }
        //bd.delete("reserva", null, null);

    }


}