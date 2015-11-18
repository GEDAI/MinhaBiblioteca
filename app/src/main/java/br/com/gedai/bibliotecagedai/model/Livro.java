package br.com.gedai.bibliotecagedai.model;

import java.io.Serializable;

/**
 * Created by GTI-366739 on 04/11/2015.
 */
public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private String autor;
    private String resumo;
    private double avaliacao;
    private String pathImagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getPathImagem() {
        return pathImagem;
    }

    public void setPathImagem(String pathImagem) {
        this.pathImagem = pathImagem;
    }


}
