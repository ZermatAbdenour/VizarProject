package com.example.vizar;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vizar.Model.UpdatePasswordDto;
import com.example.vizar.Model.User;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link passwordedit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class passwordedit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private APILink apiLink;

    public passwordedit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment passwordedit.
     */
    // TODO: Rename and change types and number of parameters
    public static passwordedit newInstance(String param1, String param2) {
        passwordedit fragment = new passwordedit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Inisialize Api link
        apiLink = RetrofitClient.getInstance().create(APILink.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_passwordedit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText PasswordInputText = (TextInputEditText) view.findViewById(R.id.Newpassword);
        PasswordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckSimilarity();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextInputEditText RepeatPasswordInputText = (TextInputEditText) view.findViewById(R.id.RepeatPassword);
        RepeatPasswordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckSimilarity();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button SaveChanges = view.findViewById(R.id.PasswordSaveChanges);
        SaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = Paper.book().read("User",null);

                TextInputEditText OldPasswordInputText = (TextInputEditText) getView().findViewById(R.id.Oldpassword);
                UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(OldPasswordInputText.getText().toString(),PasswordInputText.getText().toString());

                Call<User> UpdatePassword = apiLink.UpdatePassword(user.id,updatePasswordDto);

                UpdatePassword.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Paper.book().write("User",response.body());

                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void CheckSimilarity(){
        TextInputEditText PasswordInputText = (TextInputEditText) getView().findViewById(R.id.Newpassword);
        TextInputEditText RepeatPasswordInputText = (TextInputEditText) getView().findViewById(R.id.RepeatPassword);
        TextInputLayout RepeatPasswordLayout = (TextInputLayout)getView().findViewById(R.id.RepeatPasswordLayout);
        if(!PasswordInputText.getText().toString().matches(RepeatPasswordInputText.getText().toString())){
            RepeatPasswordLayout.setErrorEnabled(true);
            RepeatPasswordLayout.setError("Not match");
        }
        else
            RepeatPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 0f);
        animation.setDuration(500);
        animation.start();
    }
}