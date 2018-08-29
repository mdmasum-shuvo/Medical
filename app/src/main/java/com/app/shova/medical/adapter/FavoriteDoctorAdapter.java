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
import com.app.shova.medical.model.Doctor;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;


public class FavoriteDoctorAdapter extends RecyclerView.Adapter<FavoriteDoctorAdapter.CustomViewHolder> implements INameableAdapter {

    private Context context;
    private ArrayList<Doctor> doctorList;
    // Listener
    private OnItemClickListener mListener;

    public FavoriteDoctorAdapter(Context context, ArrayList<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;

    }

    @Override
    public Character getCharacterForElement(int element) {
        Character c = doctorList.get(element).getdName().charAt(0);
        if (Character.isDigit(c)) {
            c = '#';
        }
        return c;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ArrayList<Doctor> doctorList;
        TextView txtDoctorName, txtDoctorSpecialist;
        ImageView imgUnFav;

        public CustomViewHolder(View itemView, Context context, ArrayList<Doctor> doctorList) {
            super(itemView);
            this.context = context;
            this.doctorList = doctorList;

            txtDoctorName = (TextView) itemView.findViewById(R.id.textView_favourite);
            txtDoctorSpecialist = (TextView) itemView.findViewById(R.id.textView_another_word_favorite);
            imgUnFav = (ImageView) itemView.findViewById(R.id.icon_unfav_doctor);


            txtDoctorName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view, getLayoutPosition());
                }
            });

            imgUnFav.setOnClickListener(new View.OnClickListener() {
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
    public FavoriteDoctorAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_favorite_doctor, null);
        FavoriteDoctorAdapter.CustomViewHolder viewHolder = new FavoriteDoctorAdapter.CustomViewHolder(view, context, doctorList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FavoriteDoctorAdapter.CustomViewHolder holder, final int position) {
        holder.txtDoctorName.setText(doctorList.get(position).getdName());
        holder.txtDoctorSpecialist.setText(doctorList.get(position).getdSpecialist());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}

