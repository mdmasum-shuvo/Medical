package com.app.shova.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.shova.medical.R;
import com.app.shova.medical.data.sqlite.FavoriteDoctorDbController;
import com.app.shova.medical.filterData.DoctorFilter;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Doctor;
import com.bumptech.glide.Glide;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.CustomViewHolder> implements Filterable,INameableAdapter {
    private Context context;
    public ArrayList<Doctor> doctorList,filterList;
    private DoctorFilter doctorListFilter;
    public static OnItemClickListener mListener;
    private FavoriteDoctorDbController dbController;

    public DoctorListAdapter(Context context, ArrayList<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
        this.filterList = doctorList;
        dbController=new FavoriteDoctorDbController(context);
    }

    @Override
    public Character getCharacterForElement(int element) {
        Character c = doctorList.get(element).getdName().charAt(0);
        if(Character.isDigit(c)) {
            c = '#';
        }
        return c;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<Doctor> doctorList;
        private TextView txtName,txtDegic;
        private  ImageView imgFavorite,imgUnFavorite,imgProfile,imgPhone;


        public CustomViewHolder(View itemView, Context context, ArrayList<Doctor> doctorList) {
            super(itemView);
            this.context = context;
            this.doctorList = doctorList;

            txtName = itemView.findViewById(R.id.textView_doctor_name);
            txtDegic= itemView.findViewById(R.id.textView_doctor_desig);
            imgProfile=itemView.findViewById(R.id.doctor_profile_img_list);
            imgFavorite =  itemView.findViewById(R.id.favorite_icon_doctor);
            imgUnFavorite =  itemView.findViewById(R.id.unfavorite_icon_doctor);
            imgPhone=itemView.findViewById(R.id.doctor_phone_call);
            txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                }
            });
            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                    imgFavorite.setVisibility(View.GONE);
                    imgUnFavorite.setVisibility(View.VISIBLE);
                }
            });

            imgUnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view,getLayoutPosition());
                    imgUnFavorite.setVisibility(View.GONE);
                    imgFavorite.setVisibility(View.VISIBLE);
                }
            });

            imgPhone.setOnClickListener(new View.OnClickListener() {
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
        View view = inflater.inflate(R.layout.item_doctor, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view, context, doctorList);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        holder.txtName.setText(doctorList.get(position).getdName());
        holder.txtDegic.setText("/"+doctorList.get(position).getdSpecialist()+"/");
        //Loading image from Glide library.
        Glide.with(context).load(doctorList.get(position).getImgUrl()).into(holder.imgProfile);
        for (int i=0;i<dbController.getAllData().size();i++){
            if (doctorList.get(position).getdName().equalsIgnoreCase(dbController.getAllData().get(i).getdName())){
                holder.imgFavorite.setVisibility(View.VISIBLE);
                holder.imgUnFavorite.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    @Override
    public Filter getFilter() {
        if (doctorListFilter==null){
            doctorListFilter=new DoctorFilter(this,filterList);
        }

        return doctorListFilter;
    }


    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

}
