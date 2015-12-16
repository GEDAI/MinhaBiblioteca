package br.com.gedai.bibliotecagedai.adapter;

import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.gedai.bibliotecagedai.ImagemHelper;
import br.com.gedai.bibliotecagedai.model.Livro;
import br.com.gedai.bibliotecagedai.R;

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livros;

    public LivroAdapter(Context context, List<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ImagemHelper imagemHelper = new ImagemHelper();

        Livro livro = livros.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_colecao, null);

        TextView txttitulo = (TextView) view.findViewById(R.id.txtTituloLivro);
        txttitulo.setText(livro.getTitulo());

        ImageView imagemLivro = (ImageView) view.findViewById(R.id.imagemLivro);

        imagemLivro.setImageBitmap(
                imagemHelper.decodeSampledBitmapFromResource(livro.getPathImagem(), 60, 42));

        return view;
    }



}

