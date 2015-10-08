package com.example.sandorferreira.supermercado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;


/**
 * Created by sandorferreira on 30/09/15.
 */
public class ItemAdapter extends BaseAdapter implements Filterable{



    LayoutInflater layoutInflater;
    ArrayList<Item> itens;
    ArrayList<Item> itensComprados;
    ArrayList<Item> orig;

    public ItemAdapter(Context context, ArrayList<Item> itens){
        super();
        layoutInflater = LayoutInflater.from(context);
        this.itens = itens;
    }


    public ArrayList<Item> getItensComprados(){
        return itensComprados;
    }


    public android.widget.Filter getFilter(){
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Item> results = new ArrayList<Item>();
                if(orig==null){
                    orig = itens;}
                if(constraint != null){
                    if(orig!= null && orig.size() > 0){
                        for(final Item item : orig){
                            if(item.getNomeProduto().toLowerCase().contains(constraint.toString())){
                                results.add(item);
                            }
                        }
                        oReturn.values = results;
                    }
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itens = (ArrayList<Item>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        if((v==null) || v.getTag() == null){
            v = layoutInflater.inflate(R.layout.item_lista_produtos, null);
            holder = new ViewHolder();
            holder.nomeProduto = (TextView) v.findViewById(R.id.tvNameProduct);
            v.setTag(holder);
            } else{
            holder = (ViewHolder) v.getTag();
        }
        holder.nomeProduto.setText(itens.get(position).getNomeProduto());
        v.setTag(holder);
        return v;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView nomeProduto;
    }
}
