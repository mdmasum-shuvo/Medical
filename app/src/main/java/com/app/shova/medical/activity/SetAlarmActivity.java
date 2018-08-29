package com.app.shova.medical.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.AlarmAdapter;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.preference.AppPreference;
import com.app.shova.medical.data.preference.PrefKey;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Alarm;
import com.app.shova.medical.receiver.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SetAlarmActivity extends BaseActivity {
    private Activity mActivity;
    private Context mContext;
    private TextView textTime,textAmPm;
    private LinearLayout layout;
    private Calendar currenTime;
    private Calendar calendar;
    private int hour,current_minute;
    private String format;
    private long alarmTimeInMillis=0;
    private AlarmManager alarmManager;

    private RecyclerView recyclerViewAlarm;
    private ArrayList<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initToolbar();
        setToolbarTitle("Set Alarm");
        initListener();
        initFunctionality();


    }

    private void initVariable() {
        mActivity=SetAlarmActivity.this;
        mContext=getApplicationContext();
        alarmManager= (AlarmManager) mActivity.getSystemService(Context.ALARM_SERVICE);
        alarmList =new ArrayList<>();
        alarmAdapter=new AlarmAdapter(mContext, alarmList);

    }

    private void initView() {
        setContentView(R.layout.activity_set_alarm);
        layout=findViewById(R.id.time_layout);

        recyclerViewAlarm =findViewById(R.id.recycle_alarm_time);
        recyclerViewAlarm.setHasFixedSize(true);
        recyclerViewAlarm.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerViewAlarm.setAdapter(alarmAdapter);


    }

    private void initListener(){

        alarmAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                TimePickerDialog timePicker;
                switch (view.getId()){

                    case R.id.set_alarm_button:
                        int countAlarm=AppPreference.getInstance(mContext).getInteger(PrefKey.PREF_ALARM_REQUESTKEY);
                        AppPreference.getInstance(mContext).setInteger(PrefKey.PREF_ALARM_REQUESTKEY,countAlarm+1);
                        AlarmManager manager= (AlarmManager)mActivity.getSystemService(Context.ALARM_SERVICE);
                        Intent intent=new Intent(mActivity,AlarmReceiver.class);
                        set_Calender_alarm(alarmList.get(position).getHour(),alarmList.get(position).getMinute());
                        manager.setRepeating(AlarmManager.RTC_WAKEUP,alarmTimeInMillis,24*60*60*1000, PendingIntent.getBroadcast(mActivity,countAlarm,intent,PendingIntent.FLAG_UPDATE_CURRENT));
                        Toast.makeText(mContext, "Alarm set"+alarmTimeInMillis, Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.timeText:
                        timePicker=new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                int currentHour=setCurrentHour(hourOfDay);
                                //alarm_time_setup(currentHour,minute);
                                Alarm alarm=new Alarm(position+1,currentHour,minute,format);
                                alarmList.set(position,alarm);
                                alarmAdapter.notifyDataSetChanged();
                                //Log.e("hour of day", String.valueOf(hourOfDay));
                                set_Calender_alarm(hourOfDay,minute);


                            }
                        },hour,current_minute,false);
                        timePicker.show();
                        break;

                    default:
                         timePicker=new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                int currentHour=setCurrentHour(hourOfDay);
                                //alarm_time_setup(currentHour,minute);
                                Alarm alarm=new Alarm(position+1,currentHour,minute,format);
                                alarmList.set(position,alarm);
                                alarmAdapter.notifyDataSetChanged();
                                //Log.e("hour of day", String.valueOf(hourOfDay));
                                set_Calender_alarm(hourOfDay,minute);


                            }
                        },hour,current_minute,false);
                        timePicker.show();
                        break;

                }
            }
        });
    }

    private void initFunctionality() {
        set_current_time();
        int currentHour=setCurrentHour(hour);
        Bundle bundle=getIntent().getExtras();
        String data=bundle.getString(AppConstants.KEY_PASS_INTENT);
        int timeCount= Integer.parseInt(data);
        for (int i=1;i<=timeCount;i++){
            Alarm alarm=new Alarm(i,currentHour,current_minute,format);
            alarmList.add(alarm);
        }
        alarmAdapter.notifyDataSetChanged();

    }



    private void set_current_time() {

        currenTime= Calendar.getInstance();
        hour=currenTime.get(Calendar.HOUR_OF_DAY);
        current_minute=currenTime.get(Calendar.MINUTE);
    }

    private int setCurrentHour(int hour) {

        if (hour==0){
            hour+=12;
            format="AM";
        }
        else if (hour==12){
            format="PM";
        }
        else if (hour>12){
            hour-=12;
            format="PM";
        }
        else {
            format="AM";
        }

        return hour;
    }





    private void set_Calender_alarm(int hourOfDay, int minute) {
        calendar=Calendar.getInstance();
        //calendar.set(Calendar.DAY_OF_WEEK,3);
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,10);
        alarmTimeInMillis=calendar.getTimeInMillis();
        //Log.e("day of wek", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
    }






    public void alarm_time_setup(int set_hour,int set_minute){
        set_current_time();
        int hour_interval=0;
        int minute_interval=0;
        if (hour<12 && format=="PM"){
            set_hour=set_hour+12;
            hour_interval=hour-set_hour;
        }
        else if (hour>=12 && format=="AM"){
            //set_hour=set_hour+12;
            hour_interval=(hour-set_hour)+12;
        }
        else if (hour==0){
            hour_interval=hour-set_hour;
        }
        else {
            hour_interval=hour-set_hour;
        }


        if (set_minute==0){
            minute_interval=60-current_minute;
            hour_interval=hour_interval+1;
        }
        else {
            minute_interval=set_minute-current_minute;
        }

        long hourInMilis=hour_interval*3600000;
        long minuteInMilis=minute_interval*60000;
        if (hourInMilis<0){
            hourInMilis=hourInMilis*(-1);
        }
        if (minuteInMilis<0){
            minuteInMilis=minuteInMilis*(-1);
        }
        Long timeInMillis=hourInMilis+minuteInMilis;
        alarmTimeInMillis=new GregorianCalendar().getTimeInMillis()+timeInMillis;




        //else if (hour>12 && format=="AM")

        Log.e("current Hour", String.valueOf(hour));
        Log.e("new Hour", String.valueOf(set_hour));
        Log.e("new minute", String.valueOf(set_minute));
        Log.e("new hour_interval", String.valueOf(hour_interval));
        Log.e("new minute_interval", String.valueOf(minute_interval));
        Log.e("hourInMilis", String.valueOf(hourInMilis));
        Log.e("minuteInMilis", String.valueOf(minuteInMilis));
        Log.e("alarmTimeInMillis", String.valueOf(alarmTimeInMillis));
        Log.e("_________________", String.valueOf(0));

    }





}
