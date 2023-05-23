package com.example.biblioteca;

public class Sessao {
    private static String cpf;
    private static String senha;
    private static String nome;
    private static String email;
    private static boolean fundo_black;

    public static String getCpf() {
        return cpf;
    }

    public static void setCpf(String cpf) {
        Sessao.cpf = cpf;
    }

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        Sessao.senha = senha;
    }

    public static String getNome() {
        return nome;
    }

    public static void setNome(String nome) {
        Sessao.nome = nome;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Sessao.email = email;
    }

    public static boolean getFundo_black() {
        return fundo_black;
    }

    public static void setFundo_black(boolean fundo_black) {
        Sessao.fundo_black = fundo_black;
    }
}
