package com.app.shova.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Prescription;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.CustomViewHolder>{
    private Context context;
    public ArrayList<Prescription> prescriptionList;

    public static OnItemClickListener mListener;

    public PrescriptionAdapter(Context context, ArrayList<Prescription> prescriptionList) {
        this.context = context;
        this.prescriptionList = prescriptionList;

    }



    class CustomViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<Prescription> prescriptionList;
        private TextView txtMedcineName,txtTimes;




        public CustomViewHolder(View itemView, Context context, ArrayList<Prescription> prescriptionList) {
            super(itemView);
            this.context = context;
            this.prescriptionList = prescriptionList;

            txtMedcineName = itemView.findViewById(R.id.textView_medicine_name_prescript);
            txtTimes= itemView.findViewById(R.id.textView_presciption_times);



        }

    }


    @Override
    public PrescriptionAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_precription, null);
        PrescriptionAdapter.CustomViewHolder viewHolder = new PrescriptionAdapter.CustomViewHolder(view, context, prescriptionList);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(PrescriptionAdapter.CustomViewHolder holder, final int position) {

        holder.txtMedcineName.setText(prescriptionList.get(position).getmName());
        holder.txtTimes.setText(prescriptionList.get(position).getTime()+" ");

        //Loading image from Glide library.

    }


    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }



    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}
