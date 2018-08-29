package com.app.shova.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Medicine;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;

/**
 * Created by Masum on 4/16/2018.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.CustomViewHolder> implements INameableAdapter {
    private Context context;
    public ArrayList<Medicine> dataList,filterList;
    public static OnItemClickListener mListener;
    public MedicineAdapter(Context context, ArrayList<Medicine> medicineList) {
        this.context = context;
        this.dataList = medicineList;
        this.filterList = medicineList;
    }

    @Override
    public Character getCharacterForElement(int element) {
        Character c = dataList.get(element).getmName().charAt(0);
        if(Character.isDigit(c)) {
            c = '#';
        }
        return c;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ArrayList<Medicine> wordList;
        TextView txtmName, txtmGenric, txtmCompany;
        ImageView imgPronounc;
        String mName =null;
        String mGenric =null;

        public CustomViewHolder(View itemView, Context context, ArrayList<Medicine> medicineList) {
            super(itemView);
            this.context = context;
            this.wordList = medicineList;
            txtmName = (TextView) itemView.findViewById(R.id.textView_medicine_name_item);
            txtmGenric = (TextView) itemView.findViewById(R.id.textView_genric_name_item);
            txtmCompany = (TextView) itemView.findViewById(R.id.textView_company_item);
            imgPronounc=itemView.findViewById(R.id.imageView_pronounce);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                }
            });
            imgPronounc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                }
            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_medicinelist, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view, context, dataList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        holder.mName = dataList.get(position).getmName();
        holder.mGenric = dataList.get(position).getmGenric();
        holder.txtmName.setText(holder.mName);
        holder.txtmGenric.setText("/"+holder.mGenric +"/");
        holder.txtmCompany.setText(dataList.get(position).getmCompany());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    public void setFilter(ArrayList<Medicine> newDataList) {
        dataList =new ArrayList<>();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}
