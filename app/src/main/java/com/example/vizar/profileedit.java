package com.example.vizar;

import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.vizar.Model.GUIDDto;
import com.example.vizar.Model.UpdateProfileDto;
import com.example.vizar.Model.User;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import io.paperdb.Paper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileedit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileedit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    APILink apiLink;

    TextInputEditText UserName;
    TextInputLayout UserNameLayout;
    TextInputEditText FullName;
    TextInputLayout FullNameLayout;
    TextInputEditText Mobile;
    TextInputLayout MobileLayout;
    TextInputEditText Adresse;
    TextInputLayout AdresseLayout;

    public profileedit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileedit.
     */
    // TODO: Rename and change types and number of parameters
    public static profileedit newInstance(String param1, String param2) {
        profileedit fragment = new profileedit();
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
        return inflater.inflate(R.layout.fragment_profileedit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get references
        UserName =  getView().findViewById(R.id.ProfileUserName);
        UserNameLayout =  getView().findViewById(R.id.ProfileUserNameLayout);
        FullName =  getView().findViewById(R.id.ProfileFullName);
        FullNameLayout =  getView().findViewById(R.id.ProfileFullNameLayout);
        Mobile =  getView().findViewById(R.id.ProfileMobile);
        MobileLayout =  getView().findViewById(R.id.ProfileMobileLayout);
        Adresse =  getView().findViewById(R.id.ProfileAdresse);
        AdresseLayout =  getView().findViewById(R.id.ProfileAdresseLayout);

        //Set Old Values
        User user = Paper.book().read("User");
        UserName.setText(user.userName);


        //Photo Picker ActivityResultLauncher
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        ImageUri = uri;
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        //Photo Picker Listener
        ImageView ProfileImage = view.findViewById(R.id.ProfileUserImage);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        //Save Changes Button click listener

        Button SaveChanges = view.findViewById(R.id.ProfileSaveChanges);
        SaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImageUri != null)
                    UploadImage();
                if(CheckInputs())
                    UpdateProfile();
            }
        });

        //Inputs Errors Listeners
        CheckInputsErrors();
    }

    private Uri ImageUri;
    private void UploadImage(){
        File imagefile = new File(FileHelper.getRealPathFromURI(getContext(),ImageUri));
        User UserAccount = Paper.book().read("User");
        File CompressedImageFile;
        try {
            CompressedImageFile = File.createTempFile("TempCompressedImage", ".png",getContext().getCacheDir());
            CompressedImageFile = new Compressor(getContext()).setMaxHeight(300).setMaxWidth(300).compressToFile(imagefile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(ImageUri)),
                        CompressedImageFile
                );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("Image", CompressedImageFile.getName(), requestFile);


        Call<User> SetUserImage = apiLink.setProfileImage(UserAccount.id,body);
        File finalCompressedImageFile = CompressedImageFile;

        SetUserImage.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Paper.book().write("User",response.body());
                System.out.println("ImageUploaded");
                //ClearCompressedImage
                finalCompressedImageFile.delete();

                //update the UserTOPImage
                ImageView UserImage = getActivity().findViewById(R.id.UserTopImage);

                Glide.with(getActivity()).load("http://abdenourzermat-001-site1.htempurl.com/images/" + response.body().imageID).transition(DrawableTransitionOptions.withCrossFade()).into(UserImage);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void UpdateProfile(){

        UpdateProfileDto UpdateDto = new UpdateProfileDto(UserName.getText().toString(),FullName.getText().toString(),Mobile.getText().toString(),Adresse.getText().toString());
        User UserAccount = Paper.book().read("User");
        Call<User> UpdateProfile = apiLink.UpdateProfile(UserAccount.id,UpdateDto);
        UpdateProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Paper.book().write("User",response.body());
                System.out.println("Profile Updated");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public void CheckInputsErrors(){


        UserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(UserName.getText().toString().length()<8){
                    UserNameLayout.setErrorEnabled(true);
                    UserNameLayout.setError("Enter 8 character at minimum");
                }
                else {
                    UserNameLayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        FullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(FullName.getText().toString().length()> 0 && FullName.getText().toString().length()<8){
                    FullNameLayout.setErrorEnabled(true);
                    FullNameLayout.setError("Enter 8 character at minimum");
                }
                else {
                    FullNameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Mobile.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        Mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Mobile.getText().toString().length()>0 && Mobile.getText().toString().length()<8){
                    MobileLayout.setErrorEnabled(true);
                    MobileLayout.setError("Enter 8 character at minimum");
                }
                else {
                    MobileLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Adresse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Adresse.getText().toString().length()>0 && Adresse.getText().toString().length()<8){
                    AdresseLayout.setErrorEnabled(true);
                    AdresseLayout.setError("Enter 8 character at minimum");
                }
                else {
                    AdresseLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean CheckInputs(){
        boolean valide = true;

        if(UserName.getText().toString().length()<8){
            valide = false;
        }
        if(FullName.getText().toString().length() > 0 && FullName.getText().toString().length()<8){
            valide = false;
        }
        if(Mobile.getText().toString().length()>0 && Mobile.getText().toString().length()<8){
            valide = false;
        }
        if(Mobile.getText().toString().length()>0 && Adresse.getText().toString().length()<8){
            valide = false;
        }

        return valide;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Exit Animation
        ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 0f);
        animation.setDuration(500);
        animation.start();
    }

}