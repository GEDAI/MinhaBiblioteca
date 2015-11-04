package br.com.gedai.bibliotecagedai;

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


}
