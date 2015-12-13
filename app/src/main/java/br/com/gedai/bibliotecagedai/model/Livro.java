package br.com.gedai.bibliotecagedai.model;

import java.io.Serializable;

/**
 * Created by GTI-366739 on 04/11/2015.
 */
public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String titulo;
    private String autor;
    private String resumo;
    private String classificacao;
    private String cutter;
    private String observacao;
    private String pathImagem;
    private double avaliacao;

    public Livro(){}

    public Livro(Long id, String titulo, String autor, String resumo,
                 String classificacao, String cutter, String observacao,
                 String pathImagem, double avaliacao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.resumo = resumo;
        this.classificacao = classificacao;
        this.cutter = cutter;
        this.observacao = observacao;
        this.pathImagem = pathImagem;
        this.avaliacao = avaliacao;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getCutter() {
        return cutter;
    }

    public void setCutter(String cutter) {
        this.cutter = cutter;
    }

    public String getPathImagem() {
        return pathImagem;
    }

    public void setPathImagem(String pathImagem) {
        this.pathImagem = pathImagem;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

}
