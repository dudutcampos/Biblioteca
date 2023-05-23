package com.example.biblioteca;

public class Livro {
    private int id;
    private String titulo;//ok
    private String edicao;//ok
    private String autor;//ok
    private String editora;//ok
    private int ano_public;//ok
    private int n_pag;//ok
    private int exemplares;//ok
    private int disponivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAno_public() {
        return ano_public;
    }

    public void setAno_public(int ano_public) {
        this.ano_public = ano_public;
    }

    public int getN_pag() {
        return n_pag;
    }

    public void setN_pag(int n_pag) {
        this.n_pag = n_pag;
    }

    public int getExemplares() {
        return exemplares;
    }

    public void setExemplares(int exemplares) {
        this.exemplares = exemplares;
    }

    public int getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return getTitulo();
    }
}
