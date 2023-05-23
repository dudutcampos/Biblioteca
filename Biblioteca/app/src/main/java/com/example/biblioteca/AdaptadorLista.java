package com.example.biblioteca;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorLista extends BaseAdapter {
    private String[][] itensLista;
    private Activity act;

    public AdaptadorLista(String[][] itensLista, Activity act) {
        this.itensLista = itensLista;
        this.act = act;
    }

    @Override
    // Retorna o tamanho da lista
    public int getCount() {
        return itensLista.length;
    }

    @Override
    //retorna um item específico da lista
    public Object getItem(int position) {
        return itensLista[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item, parent, false);

        TextView item = view.findViewById(R.id.idItem);
        item.setText(itensLista[position][1]);

        TextView item2 = view.findViewById(R.id.list_disponivel);
        item2.setText("Disponivel: " + itensLista[position][8]);

        ImageView item3 = view.findViewById(R.id.imageView_mais);
//        item3.setImageDrawable(R.drawable.mais_btn);

        return view;
    }
}
