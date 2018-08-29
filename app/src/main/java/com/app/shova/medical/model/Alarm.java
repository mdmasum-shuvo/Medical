package com.app.shova.medical.model;

public class Alarm {
    private int alarmNumber,hour,minute;
    private String format;

    public Alarm(int alarmNumber, int hour, int minute, String format) {
        this.alarmNumber = alarmNumber;
        this.hour = hour;
        this.minute = minute;
        this.format = format;
    }


    public int getAlarmNumber() {
        return alarmNumber;
    }

    public void setAlarmNumber(int alarmNumber) {
        this.alarmNumber = alarmNumber;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
