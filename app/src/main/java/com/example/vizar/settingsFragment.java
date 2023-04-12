package com.example.vizar;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Switch user
        SwitchCompat UserModeSwitch =  (SwitchCompat)view.findViewById(R.id.UserMode);
        UserModeSwitch.setChecked((boolean)Paper.book().read("IsSeller",false));
        UserModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Paper.book().write("IsSeller",b);
            }
        });

        //Edit Password

        Button EditPasswordButton = (Button) view.findViewById(R.id.EditPasswordButton);
        EditPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slideup,R.anim.slidedown
//R.anim.fade_in,R.anim.fade_out
                            )
                            .replace(R.id.fragmentContainerView3,passwordedit.class,null)

                            .addToBackStack(null)
                            .commit();


                    //animation navbar

                ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 500f);
                animation.setDuration(500);
                animation.start();

            }
        });

        //Edit Password

        Button EditProfileButton = (Button) view.findViewById(R.id.EditProfileButton);
        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                R.anim.slideup,R.anim.slidedown
//R.anim.fade_in,R.anim.fade_out
                        )
                        .replace(R.id.fragmentContainerView3,profileedit.class,null)

                        .addToBackStack(null)
                        .commit();


                //animation navbar

                ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 500f);
                animation.setDuration(500);
                animation.start();

            }

        });
        //Log out

        Button LogoutButton = (Button) view.findViewById(R.id.LogoutButton);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("Authentified",false);
                Intent i = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(i);
            }
        });
    }


}