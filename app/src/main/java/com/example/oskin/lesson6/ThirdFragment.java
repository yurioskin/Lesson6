package com.example.oskin.lesson6;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThirdFragment extends Fragment {

    private TextView mTextViewInfo;

    private String saveData;

    public ThirdFragment() {
    }

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        mTextViewInfo = view.findViewById(R.id.edit_text_third_fragment);
        if (saveData != null)
            setData(saveData);
        return view;
    }

    public void setData(String data){
        saveData = data;
        mTextViewInfo.setText(data);
    }

}

