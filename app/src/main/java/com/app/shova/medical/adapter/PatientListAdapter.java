package com.app.shova.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.filterData.DoctorFilter;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Appointment;
import com.app.shova.medical.model.Doctor;
import com.bumptech.glide.Glide;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;

public class PatientListAdapter  extends RecyclerView.Adapter<PatientListAdapter.CustomViewHolder> implements INameableAdapter {
    private Context context;
    public ArrayList<Appointment> patientList;

    public static OnItemClickListener mListener;

    public PatientListAdapter(Context context, ArrayList<Appointment> patientList) {
        this.context = context;
        this.patientList = patientList;

    }

    @Override
    public Character getCharacterForElement(int element) {
        Character c = patientList.get(element).getdName().charAt(0);
        if(Character.isDigit(c)) {
            c = '#';
        }
        return c;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<Appointment> patientList;
        private TextView txtName,txtSyndrom;
        private Button btnPrescrip;



        public CustomViewHolder(View itemView, Context context, ArrayList<Appointment> patientList) {
            super(itemView);
            this.context = context;
            this.patientList = patientList;

            txtName = itemView.findViewById(R.id.textView_paitent_name);
            txtSyndrom= itemView.findViewById(R.id.textView_patient_syndrom);
            btnPrescrip=itemView.findViewById(R.id.btn_prescrip);
            txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                }
            });

            btnPrescrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                }
            });

        }

    }


    @Override
    public PatientListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_patient, null);
        PatientListAdapter.CustomViewHolder viewHolder = new PatientListAdapter.CustomViewHolder(view, context, patientList);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(PatientListAdapter.CustomViewHolder holder, final int position) {

        holder.txtName.setText(patientList.get(position).getuName());
        holder.txtSyndrom.setText("/"+patientList.get(position).getSyndrome()+"/");

        //Loading image from Glide library.

    }


    @Override
    public int getItemCount() {
        return patientList.size();
    }



    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}
