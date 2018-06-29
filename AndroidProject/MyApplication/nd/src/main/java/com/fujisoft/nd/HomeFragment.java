package com.fujisoft.nd;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import lohas.ddp.DoubleDatePicker;

public class HomeFragment extends Fragment{
    DoubleDatePicker ddp;
    Button sBtn,eBtn; 
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Bundle bundle = getArguments();
        ddp = view.findViewById(R.id.ddp);
        sBtn = view.findViewById(R.id.sBtn);
        eBtn = view.findViewById(R.id.eBtn);
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), ddp.getStartDate(), Toast.LENGTH_SHORT).show();
            }
        });
        eBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), ddp.getEndDate(), Toast.LENGTH_SHORT).show();
            }
        });
        
        String agrs1 = bundle.getString("agrs1");
        TextView tv = (TextView)view.findViewById(R.id.container);
        tv.setText(agrs1);
        return view;
    }
}
