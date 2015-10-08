package com.example.sandorferreira.supermercado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by sandorferreira on 02/10/15.
 */
public class ItemListadoAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    LayoutInflater layoutInflater;
    ArrayList<Item> itens;
    ArrayList<Item> itensCompArados;
    ArrayList<Item> orig;
    private TreeSet<Integer> mSectionHeader = new TreeSet<>();


    public ItemListadoAdapter(Context context, ArrayList<Item> itens){
        super();
        layoutInflater = LayoutInflater.from(context);
        this.itens = itens;
    }

    @Override
    public int getViewTypeCount() {
        return itens.size();
    }


    public void addItem(Item item){
        itens.add(item);
    }

    public void addSectionHeader(Item item){
        itens.add(item);
        mSectionHeader.add(itens.size()-1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mSectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
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
        ViewHolder holder = null;
        View v = convertView;
        int type = itens.get(position).getIsSeparator();
        if((v==null) || v.getTag() == null){
            holder = new ViewHolder();
            switch (type) {
                case 0:
                    v = layoutInflater.inflate(R.layout.item_lista_comprado, null);
                    //holder = new ViewHolder();
                    holder.nomeProduto = (TextView) v.findViewById(R.id.tvNomeComprado);
                    holder.checkBox = (CheckBox) v.findViewById(R.id.checkBoxItem);
                    holder.nomeProduto.setText(itens.get(position).getNomeProduto());
                    break;
                default:
                    v = layoutInflater.inflate(R.layout.separator,null);
                    break;
            }
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        //holder = new ViewHolder();

        v.setTag(holder);
        return v;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView nomeProduto;
        CheckBox checkBox;
    }

}
