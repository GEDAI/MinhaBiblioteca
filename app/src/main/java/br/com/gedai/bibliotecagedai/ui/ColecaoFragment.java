package br.com.gedai.bibliotecagedai.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.com.gedai.bibliotecagedai.adapter.LivroAdapter;
import br.com.gedai.bibliotecagedai.dao.BibliotecaDAO;
import br.com.gedai.bibliotecagedai.model.Livro;
import br.com.gedai.bibliotecagedai.R;

public class ColecaoFragment extends ListFragment {

    private List<Livro> livros;
    private BibliotecaDAO dao;

    OnColecaoSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnColecaoSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onLivroSelected(int position, long livro_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.lista_livros, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.dao = new BibliotecaDAO(this.getContext());

        this.livros = this.dao.listarLivros();

        setListAdapter(new LivroAdapter(this.getContext(), this.livros));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnColecaoSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnColecaoSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Livro livro = livros.get(position);

        // Notify the parent activity of selected item
        mCallback.onLivroSelected(position, livro.getId());

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);

    }
}
