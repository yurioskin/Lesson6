package com.example.oskin.lesson6;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SecondFragment extends Fragment {

    private Button mButton;
    private IActivityCallback<String> mCallback;
    private IFragmentCallback<String> mFirstFragmentCallback;

    private boolean hasChild = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (IActivityCallback)context;
    }


    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        mButton = view.findViewById(R.id.button_second_fragment);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initListeners();
    }

    private void initListeners() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdFragment f = (ThirdFragment) getChildFragmentManager()
                        .findFragmentById(R.id.child_fragment);
                if (f == null){
                    addInnerFragment();
                }
                    setDataInFragment();
            }
        });
    }

    private void setDataInFragment(){
        String data = mCallback.getDataCallback();
        mFirstFragmentCallback.setDataCallback(getString(R.string.data_from_first_fragment) + data);
    }

    private void addInnerFragment(){
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
                .add(R.id.child_fragment,ThirdFragment.newInstance())
                .commitNow();
        mFirstFragmentCallback = (IFragmentCallback<String>) manager.findFragmentById(R.id.child_fragment);
        hasChild = true;
    }

    public void onBackPressed(){
        if (!isHasChild())
            return;

        FragmentManager manager = getChildFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.child_fragment);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitNow();
        manager.popBackStack();

    }

    public boolean isHasChild() {
        return hasChild;
    }
}
