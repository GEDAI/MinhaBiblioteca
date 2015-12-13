package br.com.gedai.bibliotecagedai.ui;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import br.com.gedai.bibliotecagedai.R;
import br.com.gedai.bibliotecagedai.dao.BibliotecaDAO;
import br.com.gedai.bibliotecagedai.model.Livro;

public class DetalheItemActivity extends Activity implements DialogInterface.OnClickListener{

    private ImageView imagemLivro;
    private TextView txtTitulo;
    private TextView txtAutor;
    private TextView txtResumo;
    private TextView txtClassificacao;
    private TextView txtCutter;
    private TextView txtObservacao;
    private RatingBar avalicoesLivro;
    private Long id;
    private Livro livro;
    private AlertDialog dialogConfirmacao;
    private BibliotecaDAO dao;

    @SuppressLint("UseValueOf")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(br.com.gedai.bibliotecagedai.R.layout.detalhes_item);
        livro = (Livro) getIntent().getSerializableExtra("livro");

        dao = new BibliotecaDAO(this);

        imagemLivro = (ImageView) findViewById(br.com.gedai.bibliotecagedai.R.id.imagem_livro);
        txtTitulo = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtTitulo);
        txtAutor = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtAutor);
        txtResumo = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtResumo);
        txtClassificacao = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtClassificacao);
        txtCutter = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtCutter);
        txtObservacao = (TextView) findViewById(br.com.gedai.bibliotecagedai.R.id.txtObservacao);
        avalicoesLivro = (RatingBar) findViewById(br.com.gedai.bibliotecagedai.R.id.avaliacaoLivro);

        imagemLivro.setImageBitmap(BitmapFactory.decodeFile(livro.getPathImagem()));
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtResumo.setText(livro.getResumo());
        txtClassificacao.setText(livro.getClassificacao());
        txtCutter.setText(livro.getCutter());
        txtObservacao.setText(livro.getObservacao());
        avalicoesLivro.setRating(new Float(livro.getAvaliacao()));

        this.id = livro.getId();

        dialogConfirmacao = criarDialogConfirmacao();
    }

    public void removerLivro(View view) {
        dialogConfirmacao.show();
    }

    public void editarLivro(View view){
        Intent intent = new Intent(this, NovoItemActivity.class);
        intent.putExtra("livro_selecionado", id);
        startActivity(intent);
    }

    private AlertDialog criarDialogConfirmacao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao_livro);
        builder.setPositiveButton(getString(R.string.sim), this);
        builder.setNegativeButton(getString(R.string.nao), this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {

        switch (item) {
            case DialogInterface.BUTTON_POSITIVE: //exclusao
                dao.removerLivro(id);
                Toast.makeText(this, getString(br.com.gedai.bibliotecagedai.R.string.registro_removido), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ColecaoActivity.class);
                startActivity(intent);

                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        dao.close();
        super.onDestroy();
    }

}
