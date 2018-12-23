package com.example.oskin.lesson6;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SecondFragment extends Fragment {

    private Button mButton;
    IActivityCallback mCallback;

    private boolean hasChild = false;


    public SecondFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                Fragment f = getChildFragmentManager().findFragmentById(R.id.child_fragment);
                if (!(f instanceof ThirdFragment)){
                    addInnerFragment();
                }
                    setDataInFragment();
            }
        });
    }

    private void setDataInFragment(){
        String data = mCallback.getDataFromFirstFragment().toString();
        FragmentManager manager = getChildFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.child_fragment);
        ((ThirdFragment) fragment).setData("Data from first fragment: " + data);
        mButton.setText(data);
    }

    private void addInnerFragment(){
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
                .add(R.id.child_fragment,ThirdFragment.newInstance())
                .commitNow();
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
