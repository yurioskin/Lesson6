package com.example.oskin.lesson6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class FirstFragment extends Fragment implements IFragmentCallback<String>{


    private EditText mEditText;

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        mEditText = view.findViewById(R.id.edit_text_first_fragment);
        return view;
    }

    private void setData(String data) {
        mEditText.setText(data);
    }

    private String getData() {
        return mEditText.getText().toString();
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
