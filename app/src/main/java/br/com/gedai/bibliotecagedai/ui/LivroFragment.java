package br.com.gedai.bibliotecagedai.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import br.com.gedai.bibliotecagedai.dao.BibliotecaDAO;
import br.com.gedai.bibliotecagedai.R;
import br.com.gedai.bibliotecagedai.model.Livro;

public class LivroFragment extends Fragment {

    final static String ARG_LIVRO_ID = "livro_id";

    private long livro_id = -1;

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
    private View view;

    OnLivroListener mCallback;

    public interface OnLivroListener {

        public void onLivroSalvarSelected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = new BibliotecaDAO(this.getContext());
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            prepararEdicao(args.getLong(ARG_LIVRO_ID));
        } else if (livro_id != -1) {
            // Set article based on saved instance state defined during onCreateView
            prepararEdicao(livro_id);
        }
    }

    private void prepararEdicao(long livro_id) {

        Livro livro = dao.buscarLivroPorId(livro_id);

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

        this.livro_id = livro_id;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnLivroListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLivroListener");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
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
    public void onDestroy() {
        dao.close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            livro_id = savedInstanceState.getLong(ARG_LIVRO_ID);
        }

        this.view = inflater.inflate(R.layout.fragment_livro, container, false);
        imagemLivro = (ImageView) this.view.findViewById(R.id.imagem_livro);
        txtTitulo = (EditText) this.view.findViewById(R.id.txtTitulo);
        txtAutor = (EditText) this.view.findViewById(R.id.txtAutor);
        txtResumo = (EditText) this.view.findViewById(R.id.txtResumo);
        txtClassificacao = (EditText) this.view.findViewById(R.id.txtClassificacao);
        txtCutter = (EditText) this.view.findViewById(R.id.txtCutter);
        txtObservacao = (EditText) this.view.findViewById(R.id.txtObservacao);
        avaliacaoLivro = (RatingBar) this.view.findViewById(R.id.avaliacaoLivro);
        btnInserirImagem = (Button) this.view.findViewById(R.id.btnInserirImagem);
        btnAdicionar = (Button) this.view.findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

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

                if(livro_id == -1){
                    resultado = dao.inserir(livro);
                }else{
                    resultado = dao.atualizar(livro, livro_id);
                }

                if(resultado != -1 ){
                    Toast.makeText(LivroFragment.this.getContext(), getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();

                    // Notify the parent activity of selected item
                    mCallback.onLivroSalvarSelected();
                }else{
                    Toast.makeText(LivroFragment.this.getContext(), getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnInserirImagem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Selecione uma imagem"), 1);
            }

        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putLong(ARG_LIVRO_ID, livro_id);
    }
}
