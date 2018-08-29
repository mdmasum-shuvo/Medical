package com.app.shova.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Alarm;
import com.app.shova.medical.model.Doctor;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.CustomViewHolder> {
    private Context context;
    public ArrayList<Alarm> alarmNumberList;
    public static OnItemClickListener mListener;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmNumberList) {
        this.context = context;
        this.alarmNumberList = alarmNumberList;

    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<Alarm> alarmNumberList;
        private TextView txtAlarmNumber, textTime, textAmPm;
        private Button btnSetAlarm;

        public CustomViewHolder(View itemView, Context context, ArrayList<Alarm> alarmNumberList) {
            super(itemView);
            this.context = context;
            this.alarmNumberList = alarmNumberList;

            txtAlarmNumber = itemView.findViewById(R.id.txt_alarm_number);
            textTime = itemView.findViewById(R.id.timeText);
            textAmPm = itemView.findViewById(R.id.amText);
            btnSetAlarm = itemView.findViewById(R.id.set_alarm_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view, getLayoutPosition());
                }
            });

            textTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view, getLayoutPosition());
                }
            });


            btnSetAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemListener(view, getLayoutPosition());
                }
            });

        }

    }


    @Override
    public AlarmAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_set_alarm, null);
        AlarmAdapter.CustomViewHolder viewHolder = new AlarmAdapter.CustomViewHolder(view, context, alarmNumberList);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(AlarmAdapter.CustomViewHolder holder, final int position) {
        holder.txtAlarmNumber.setText("alarm " + alarmNumberList.get(position).getAlarmNumber());
        holder.textTime.setText(alarmNumberList.get(position).getHour()+":"+alarmNumberList.get(position).getMinute());
        holder.textAmPm.setText(alarmNumberList.get(position).getFormat());

    }


    @Override
    public int getItemCount() {
        return alarmNumberList.size();
    }


    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


}
