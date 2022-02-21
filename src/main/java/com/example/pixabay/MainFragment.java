package com.example.pixabay;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class MainFragment extends Fragment {
    private onFragmentSelected listener;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.main_fragment,container,false);

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentSelected){
            listener=(onFragmentSelected)context;
        }else {
            throw new ClassCastException(context.toString()+"must implement listener");
        }
    }
    public interface onFragmentSelected{
    public void onButtonSelected();
    }
}