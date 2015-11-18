package br.com.gedai.bibliotecagedai.adapter;

import java.util.List;



        import android.content.Context;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import br.com.gedai.bibliotecagedai.model.Livro;

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

        Livro livro = livros.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(br.com.gedai.bibliotecagedai.R.layout.lista_livros, null);

        TextView txttitulo = (TextView) view.findViewById(br.com.gedai.bibliotecagedai.R.id.txtTituloLivro);
        txttitulo.setText(livro.getTitulo());

        ImageView imagemLivro = (ImageView) view.findViewById(br.com.gedai.bibliotecagedai.R.id.imagemLivro);
        imagemLivro.setImageBitmap(BitmapFactory.decodeFile(livro
                .getPathImagem()));

        return view;
    }

}

