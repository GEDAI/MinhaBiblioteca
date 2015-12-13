package br.com.gedai.bibliotecagedai.ui;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;


import br.com.gedai.bibliotecagedai.R;
import br.com.gedai.bibliotecagedai.dao.BibliotecaDAO;

import br.com.gedai.bibliotecagedai.model.Livro;

public class NovoItemActivity extends Activity {

    private ImageView imagemLivro;
    private Button btnInserirImagem;
    private Button btnAdicionar;
    private EditText txtTitulo;
    private EditText txtAutor;
    private EditText txtResumo;
    private EditText txtClassificacao;
    private EditText txtCutter;
    private EditText txtObservacao;
    private RatingBar avaliacaoLivro;
    private String pathImagem;
    private BibliotecaDAO dao;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(br.com.gedai.bibliotecagedai.R.layout.adicionar_item);

        imagemLivro = (ImageView) findViewById(br.com.gedai.bibliotecagedai.R.id.imagem_livro);
        txtTitulo = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtTitulo);
        txtAutor = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtAutor);
        txtResumo = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtResumo);
        txtClassificacao = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtClassificacao);
        txtCutter = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtCutter);
        txtObservacao = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtObservacao);
        avaliacaoLivro = (RatingBar) findViewById(br.com.gedai.bibliotecagedai.R.id.avaliacaoLivro);
        btnInserirImagem = (Button) findViewById(br.com.gedai.bibliotecagedai.R.id.btnInserirImagem);
        btnAdicionar = (Button) findViewById(br.com.gedai.bibliotecagedai.R.id.btnAdicionar);

        btnInserirImagem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Selecione uma imagem"), 1);
            }

        });

        dao = new BibliotecaDAO(this);

        id = getIntent().getLongExtra("livro_selecionado", -1);

        if (id != -1) {
            prepararEdicao();
        }
    }

    private void prepararEdicao() {

        Livro livro = dao.buscarLivroPorId(id);

        imagemLivro.setImageBitmap(BitmapFactory.decodeFile(livro.getPathImagem()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtResumo.setText(livro.getResumo());
        txtClassificacao.setText(livro.getClassificacao());
        txtCutter.setText(livro.getCutter());
        txtObservacao.setText(livro.getObservacao());
        pathImagem = livro.getPathImagem();
        avaliacaoLivro.setRating((float) livro.getAvaliacao());
        btnAdicionar.setText(R.string.editar);
    }

    public void salvarLivro(View view){
        Livro livro = new Livro();

        livro.setTitulo(txtTitulo.getText().toString());
        livro.setAutor(txtAutor.getText().toString());
        livro.setResumo(txtResumo.getText().toString());
        livro.setClassificacao(txtClassificacao.getText().toString());
        livro.setCutter(txtCutter.getText().toString());
        livro.setObservacao(txtObservacao.getText().toString());
        livro.setPathImagem(pathImagem);
        livro.setAvaliacao(avaliacaoLivro.getRating());

        long resultado;

        if(id == -1){
            resultado = dao.inserir(livro);
        }else{
            resultado = dao.atualizar(livro, id);
        }

        if(resultado != -1 ){
            Toast.makeText(this, getString(br.com.gedai.bibliotecagedai.R.string.registro_salvo), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ColecaoActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, getString(br.com.gedai.bibliotecagedai.R.string.erro_salvar), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imagemLivro.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathImagem = picturePath;
        }
    }

    @Override
    protected void onDestroy() {
        dao.close();
        super.onDestroy();
    }

}
