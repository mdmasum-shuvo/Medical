package com.app.shova.medical.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.shova.medical.R;

/**
 * Created by Masum on 3/8/2018.
 */

public class FastKitFragment extends Fragment {

    private View v=null;
    private TextView tvTitle1,description1;
    private TextView tvTitle2,description2;
    private TextView tvTitle3,description3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fast_kit_layout, container, false);
        tvTitle1=v.findViewById(R.id.title1);
        tvTitle2=v.findViewById(R.id.title2);
        tvTitle3=v.findViewById(R.id.title3);
        description1=v.findViewById(R.id.description1);
        description2=v.findViewById(R.id.description2);
        description3=v.findViewById(R.id.description3);
        String[] title=getResources().getStringArray(R.array.title);
        tvTitle1.setText(title[0]);
        tvTitle2.setText(title[1]);
        tvTitle3.setText(title[2]);

        String[] description=getResources().getStringArray(R.array.description);
        description1.setText(description[0]);
        description2.setText(description[1]);
        description3.setText(description[2]);
        return v;
    }


}
