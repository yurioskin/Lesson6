package com.example.oskin.lesson6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ThirdFragment extends Fragment implements IFragmentCallback<String>{

    private static final String EXTRA_STRING = "extra_string";
    private TextView mTextViewInfo;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_STRING, mTextViewInfo.getText().toString());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        mTextViewInfo = view.findViewById(R.id.edit_text_third_fragment);

        if (savedInstanceState != null){
            mTextViewInfo.setText(savedInstanceState.getString(EXTRA_STRING));
        }

        return view;
    }

    private void setData(String data){
        mTextViewInfo.setText(data);
    }

    private String getData(){
        return mTextViewInfo.getText().toString();
    }

    @Override
    public void setDataCallback(String data) {
        setData(data);
    }

    @Override
    public String getDataCallback() {
        return getData();
    }
}

