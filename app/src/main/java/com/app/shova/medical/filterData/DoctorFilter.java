package com.app.shova.medical.filterData;

import android.widget.Filter;

import com.app.shova.medical.adapter.DoctorListAdapter;
import com.app.shova.medical.model.Doctor;

import java.util.ArrayList;

/**
 * Created by Masum on 3/20/2018.
 */

public class DoctorFilter extends Filter  {
    DoctorListAdapter wordListAdapter;
    ArrayList<Doctor> doctorList;

    public DoctorFilter(DoctorListAdapter wordListAdapter, ArrayList<Doctor> doctorList){
        this.wordListAdapter=wordListAdapter;
        this.doctorList=doctorList;

    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Doctor> FilterWord=new ArrayList<>();

            for (int i=0;i<doctorList.size();i++)
            {
                //CHECK
                if(doctorList.get(i).getdName().toUpperCase().contains(constraint) || doctorList.get(i).getdSpecialist().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    FilterWord.add(doctorList.get(i));
                }
            }

            results.count=FilterWord.size();
            results.values=FilterWord;
        }else
        {
            results.count=doctorList.size();
            results.values=doctorList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        wordListAdapter.doctorList= (ArrayList<Doctor>) results.values;

        //REFRESH
        wordListAdapter.notifyDataSetChanged();
    }


}
