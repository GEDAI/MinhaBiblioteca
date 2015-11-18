package br.com.gedai.bibliotecagedai.ui;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import br.com.gedai.bibliotecagedai.model.Livro;

public class DetalheItemActivity extends Activity{

    private ImageView imagemLivro;
    private TextView txtTitulo;
    private TextView txtAutor;
    private TextView txtResumo;
    private RatingBar avalicoesLivro;
    private Livro livro;

    @SuppressLint("UseValueOf")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(br.com.gedai.bibliotecagedai.R.layout.detalhes_item);
        livro = (Livro) getIntent().getSerializableExtra("livro");

        imagemLivro = (ImageView) findViewById(br.com.gedai.bibliotecagedai.R.id.imagem_livro);
        txtTitulo = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtTitulo);
        txtAutor = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtAutor);
        txtResumo = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtResumo);
        avalicoesLivro = (RatingBar) findViewById(br.com.gedai.bibliotecagedai.R.id.avaliacaoLivro);

        imagemLivro.setImageBitmap(BitmapFactory.decodeFile(livro.getPathImagem()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtResumo.setText(livro.getResumo());
        avalicoesLivro.setRating(new Float(livro.getAvaliacao()));
    }

}
