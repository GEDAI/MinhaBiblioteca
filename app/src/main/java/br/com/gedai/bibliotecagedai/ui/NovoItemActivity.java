package br.com.gedai.bibliotecagedai.ui;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
import android.app.Activity;
import android.content.ContentValues;
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



import br.com.gedai.bibliotecagedai.service.Banco;
import br.com.gedai.bibliotecagedai.service.BancoService;

import br.com.gedai.bibliotecagedai.model.Livro;

public class NovoItemActivity extends Activity {

    private ImageView imagemLivro;
    private Button btnAdicionar;
    private Button btnInserirImagem;
    private EditText txtTitulo;
    private EditText txtClassificacao;
    private EditText txtCutter;
    private EditText txtObservacao;
    private EditText txtAutor;
    private EditText txtResumo;
    private RatingBar avalicoesLivro;
    private String pathImagem;
    private BancoService bancoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(br.com.gedai.bibliotecagedai.R.layout.adicionar_item);

        imagemLivro = (ImageView) findViewById(br.com.gedai.bibliotecagedai.R.id.imagem_livro);
        btnAdicionar = (Button) findViewById(br.com.gedai.bibliotecagedai.R.id.btnAdicionar);
        txtTitulo = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtTitulo);
        txtAutor = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtAutor);
        txtClassificacao = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtClassificacao);
        txtCutter = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtCutter);
        txtAutor = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtObservacao);
        txtResumo = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtResumo);
        txtObservacao = (EditText) findViewById(br.com.gedai.bibliotecagedai.R.id.txtObservacao);
        avalicoesLivro = (RatingBar) findViewById(br.com.gedai.bibliotecagedai.R.id.avaliacaoLivro);
        btnInserirImagem = (Button) findViewById(br.com.gedai.bibliotecagedai.R.id.btnInserirImagem);

        bancoService = new BancoService();

        btnAdicionar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Livro livro = new Livro();
                livro.setTitulo(txtTitulo.getText().toString());
                livro.setAutor(txtAutor.getText().toString());
                livro.setClassificacao(txtClassificacao.getText().toString());
                livro.setCutter(txtCutter.getText().toString());
                livro.setObservacao(txtObservacao.getText().toString());
                livro.setResumo(txtResumo.getText().toString());
                livro.setAvaliacao(avalicoesLivro.getRating());
                livro.setPathImagem(pathImagem);


                bancoService.salvarLivro(livro, getApplicationContext());

				try {

					Banco banco = new Banco(getApplicationContext(),
							"banco", null, 1);

					ContentValues contentValues = new ContentValues();
					contentValues.put("titulo", livro.getTitulo());
					contentValues.put("autor", livro.getAutor());
                    contentValues.put("classificacao", livro.getClassificacao());
                    contentValues.put("cutter", livro.getCutter());
                    contentValues.put("observacao", livro.getObservacao());
					contentValues.put("resumo", livro.getResumo());
					contentValues.put("avaliacao", livro.getAvaliacao());
					contentValues.put("path_imagem", livro.getPathImagem());
					banco.getWritableDatabase().insert("livro", null,
							contentValues);

					Toast toast = Toast.makeText(getApplicationContext(),
							"Livro cadastrado com sucesso!!", Toast.LENGTH_SHORT);
					toast.show();

				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Erro ao salvar informações no banco!", Toast.LENGTH_SHORT);
					toast.show();
				}

                txtTitulo.setText("");
                txtAutor.setText("");
                txtResumo.setText("");
                txtClassificacao.setText("");
                txtCutter.setText("");
                txtObservacao.setText("");
                avalicoesLivro.setRating(1);
                pathImagem = "";
                imagemLivro.setBackgroundResource(br.com.gedai.bibliotecagedai.R.drawable.ic_launcher);
                imagemLivro.setImageBitmap(null);

            }

        });

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

}
