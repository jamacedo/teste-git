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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by sandorferreira on 02/10/15.
 */
public class ItemListadoAdapter extends BaseAdapter{

    private static final int TYPE_ITEM = 0;

    LayoutInflater layoutInflater;
    ArrayList<Item> itens;

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
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        int type = itens.get(position).getIsSeparator();
        boolean isSelected = itens.get(position).isSelected();
        if((v==null) || v.getTag() == null){
            holder = new ViewHolder();
            if(type==TYPE_ITEM){
                /*
                TYPE_ITEM é o tipo que não será inflado como um separador e sim como um produto
                pertencente ao supermercado. Seu else definirá o layout a ser inflado caso seja
                um separador
                */

                v = layoutInflater.inflate(R.layout.item_lista_comprado, null);
                holder.nomeProduto = (TextView) v.findViewById(R.id.tvNomeComprado);
                holder.checkBox = (CheckBox) v.findViewById(R.id.checkBoxItem);
                holder.nomeProduto.setText(itens.get(position).getNomeProduto());
                if(isSelected){
                    /*
                    isSelected parâmetro do tipo Boolean da classe Item. True se está comprado e false
                    se ainda nao foi comprado. Dependendo do isSelected, o layout irá mudar (texto marcado
                    e checkbox selecionado).
                     */
                    holder.checkBox.setChecked(true);
                    holder.nomeProduto.setTextColor(Color.GRAY);
                    holder.nomeProduto.setPaintFlags(holder.nomeProduto.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.checkBox.setChecked(false);
                }
                /*
                FORA DO IF ACIMA.
                Esteja o item selecionado ou não, o usuário tem que ter a opção de mudar seu estado, logo
                o checkbox recebe os parâmetros do item e checa qual seu estado. Se o checkbox estiver checkado
                ele automaticamente muda o estado de isSelected para comprado(true). O CheckBox conta com
                uma função definida no proprio layout (onClick no XML) que atualiza o adapter (funcao está em
                MainActivity)
                 */
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Item item = itens.get(position);
                        if(isChecked) {
                            item.setSelected(true);
                            itens.remove(position);
                            itens.add(item);
                        }else{
                            item.setSelected(false);
                            itens.remove(position);
                            itens.add(getPositionSeparador(),item);
                        }
                    }
                });
            }else{
                v = layoutInflater.inflate(R.layout.separator,null);
            }
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        v.setTag(holder);
        return v;
    }

    /*
    Pega a posiçao do separador para adicionar após ele
     */

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

}
