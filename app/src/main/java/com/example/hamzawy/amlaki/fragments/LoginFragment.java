package com.example.hamzawy.amlaki.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.Utils.CustomSharedPreferences;
import com.example.hamzawy.amlaki.activities.CoreActivity;
import com.example.hamzawy.amlaki.controllers.UserLogin;
import com.example.hamzawy.amlaki.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private EditText username,password;
    private Button login;
    private void initView(View view) {
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.login);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
//                openCoreActivity();
                sendLoginRequest();
                break;
        }
    }

    private void sendLoginRequest() {
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        UserLogin userLogin = new UserLogin(user , LoginFragment.this , getActivity());
        userLogin.execute();

    }

    private void openCoreActivity() {
        Intent intent = new Intent(getActivity(), CoreActivity.class);
        startActivity(intent);
    }

    public void loginSuccess(User user) {
        CustomSharedPreferences.putUserData("USER",user);
        openCoreActivity();
    }
}
