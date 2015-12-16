package br.com.gedai.bibliotecagedai.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.gedai.bibliotecagedai.ImagemHelper;
import br.com.gedai.bibliotecagedai.R;
import br.com.gedai.bibliotecagedai.dao.BibliotecaDAO;
import br.com.gedai.bibliotecagedai.model.Livro;

public class DetalheItemFragment extends Fragment implements DialogInterface.OnClickListener {

    final static String ARG_POSITION = "position";
    final static String ARG_LIVRO_ID = "livro_id";

    private int mCurrentPosition = -1;
    private long livro_id = -1;

    private ImageView imagemLivro;
    private TextView txtTitulo;
    private TextView txtAutor;
    private TextView txtResumo;
    private TextView txtClassificacao;
    private TextView txtCutter;
    private TextView txtObservacao;
    private RatingBar avalicoesLivro;
    private AlertDialog dialogConfirmacao;
    private BibliotecaDAO dao;
    private Button btnEditarLivro;
    private Button btnRemoverLivro;

    OnDetalheItemListener acaoCallback;

    public interface OnDetalheItemListener {

        public void onLivroEditarSelected(long livro_id);

        public void onLivroRemoverSelected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = new BibliotecaDAO(this.getContext());

        dialogConfirmacao = criarDialogConfirmacao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            livro_id = savedInstanceState.getLong(ARG_LIVRO_ID);
        }

        View view = inflater.inflate(R.layout.fragment_detalhe_item, container, false);

        imagemLivro = (ImageView) view.findViewById(R.id.imagem_livro);
        txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
        txtAutor = (TextView) view.findViewById(R.id.txtAutor);
        txtResumo = (TextView) view.findViewById(R.id.txtResumo);
        txtClassificacao = (TextView) view.findViewById(R.id.txtClassificacao);
        txtCutter = (TextView) view.findViewById(R.id.txtCutter);
        txtObservacao = (TextView) view.findViewById(R.id.txtObservacao);
        avalicoesLivro = (RatingBar) view.findViewById(R.id.avaliacaoLivro);
        btnEditarLivro = (Button) view.findViewById(R.id.editarLivro);
        btnRemoverLivro = (Button) view.findViewById(R.id.removerLivro);

        btnRemoverLivro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogConfirmacao.show();
            }

        });

        btnEditarLivro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Notify the parent activity of selected item
                acaoCallback.onLivroEditarSelected(livro_id);
            }

        });

        // Inflate the layout for this fragment
        return view;
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
            atualizaLivroView(args.getInt(ARG_POSITION), args.getLong(ARG_LIVRO_ID));
        } else if (mCurrentPosition != -1 && livro_id != -1) {
            // Set article based on saved instance state defined during onCreateView
            atualizaLivroView(mCurrentPosition, livro_id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            acaoCallback = (OnDetalheItemListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnColecaoSelectedListener");
        }
    }

    public void atualizaLivroView(int position, long livro_id) {

        final Livro livro = this.dao.buscarLivroPorId(livro_id);

        final ImagemHelper imagemHelper = new ImagemHelper();

        imagemLivro.setImageBitmap(
                imagemHelper.decodeSampledBitmapFromResource(livro.getPathImagem(), 100, 100));

        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtResumo.setText(livro.getResumo());
        txtClassificacao.setText(livro.getClassificacao());
        txtCutter.setText(livro.getCutter());
        txtObservacao.setText(livro.getObservacao());
        avalicoesLivro.setRating(new Float(livro.getAvaliacao()));

        mCurrentPosition = position;
        this.livro_id = livro_id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putLong(ARG_LIVRO_ID, livro_id);
    }

    private AlertDialog criarDialogConfirmacao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(R.string.confirmacao_exclusao_livro);
        builder.setPositiveButton(getString(R.string.sim), this);
        builder.setNegativeButton(getString(R.string.nao), this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {

        switch (item) {
            case DialogInterface.BUTTON_POSITIVE: //exclusao
                dao.removerLivro(livro_id);
                Toast.makeText(this.getContext(), getString(R.string.registro_removido), Toast.LENGTH_SHORT).show();

                acaoCallback.onLivroRemoverSelected();

                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        dao.close();
        super.onDestroy();
    }
}
