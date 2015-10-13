package com.example.sandorferreira.supermercado;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
public class ItemListadoAdapter extends BaseAdapter{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    LayoutInflater layoutInflater;
    ArrayList<Item> itens;
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

    @Override
    public int getItemViewType(int position) {
        return mSectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
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

    //ViewHolder holder = null;
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        int type = itens.get(position).getIsSeparator();
        boolean isSelected = itens.get(position).isSelected();
        if((v==null) || v.getTag() == null){
            holder = new ViewHolder();
            switch (type) {
                case 0:
                    v = layoutInflater.inflate(R.layout.item_lista_comprado, null);
                    holder.nomeProduto = (TextView) v.findViewById(R.id.tvNomeComprado);
                    holder.checkBox = (CheckBox) v.findViewById(R.id.checkBoxItem);
                    holder.nomeProduto.setText(itens.get(position).getNomeProduto());
                    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                Item item = itens.get(position);
                                item.setSelected(true);
                                // colocar notifyDataSetChanged aqui <<<<
                            }
                        }
                    });
                    if(isSelected) {
                        Item item = itens.get(position);
                        //holder.checkBox.setChecked(true);
                        holder.nomeProduto.setTextColor(Color.GRAY);
                        holder.nomeProduto.setPaintFlags(holder.nomeProduto.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        itens.remove(position);
                        itens.add(item);
                        //notifyDataSetChanged();
                    }
                    break;
                default:
                    v = layoutInflater.inflate(R.layout.separator,null);
                    break;
            }
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        v.setTag(holder);
        return v;
    }

    public int getPositionSeparador(){
        int i;
        for(i=0;i<itens.size();i++){
            if(itens.get(i).getIsSeparator()==1){
                return i;
            }
        }
        return i;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView nomeProduto;
        CheckBox checkBox;
    }


    public void refresh(ArrayList<Item> itens){
        this.itens = itens;
        notifyDataSetChanged();
    }

}
