package com.app.shova.medical.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Medicine;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;


public class MedicineHistoryAdapter extends RecyclerView.Adapter<MedicineHistoryAdapter.CustomViewHolder> implements INameableAdapter {

    private Context context;
    private ArrayList<Medicine> medicineList;
    // Listener
    private OnItemClickListener mListener;

    public MedicineHistoryAdapter(Context context, ArrayList<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;

    }

    @Override
    public Character getCharacterForElement(int element) {
        Character c = medicineList.get(element).getmName().charAt(0);
        if (Character.isDigit(c)) {
            c = '#';
        }
        return c;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ArrayList<Medicine> medicineList;
        TextView txtMedcineName, txtMedicineGenric, txtMedicineType;

        public CustomViewHolder(View itemView, Context context, ArrayList<Medicine> medicineList) {
            super(itemView);
            this.context = context;
            this.medicineList = medicineList;

            txtMedcineName = (TextView) itemView.findViewById(R.id.textView_im_medicine_name);
            txtMedicineGenric = (TextView) itemView.findViewById(R.id.textView_im_medicine_genric);
            txtMedicineType = (TextView) itemView.findViewById(R.id.textView_im_medicine_type);

            txtMedcineName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view, getLayoutPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view, getLayoutPosition());
                }
            });

        }

    }

    @Override
    public MedicineHistoryAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_history_medicine, null);
        MedicineHistoryAdapter.CustomViewHolder viewHolder = new MedicineHistoryAdapter.CustomViewHolder(view, context, medicineList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MedicineHistoryAdapter.CustomViewHolder holder, final int position) {
        holder.txtMedcineName.setText(medicineList.get(position).getmName());
        holder.txtMedicineGenric.setText(medicineList.get(position).getmGenric());
        holder.txtMedicineType.setText("/"+medicineList.get(position).getmType()+"/");

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}

