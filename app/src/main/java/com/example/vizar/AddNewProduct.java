package com.example.vizar;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vizar.Model.User;
import com.example.vizar.Model.productDto;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

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
 * Use the {@link AddNewProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewProduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView pickimageplus;
    private TextView pickimagetext;
    private ImageView thepickedimage;

    private TextView modelpickertext;

    private TextView modelnametext;

    private ImageView         pickmodelplus;

    public AddNewProduct() {
        // Required empty public constructor
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewProduct.
     */
    // TODO: Rename and change types and number of parameters
    APILink apiLink;

    String[] items ={"wardrobes","Sofas","Dressers","Armchairs","Dining table","TV Stands"};

    ArrayAdapter<String> adapterItems;


    //References
    private TextInputEditText NameInputField;
    private TextInputEditText PriceInputField;
    private TextInputEditText DescriptionInputField;
    private AutoCompleteTextView CategorieDropDown;
    private TextInputEditText WebLinkInputField;
    private TextInputEditText WidthInputField;
    private TextInputEditText HeightInputField;
    private TextInputEditText DepthInputField;
    private TextInputEditText WeightInputField;
    private TextInputEditText VolumeInputField;
    private Uri ImageUri;
    private Uri ModelUri;

    private String ImageID;
    private String ModelID;
    private String ModelExtension;

    public static AddNewProduct newInstance(String param1, String param2) {
        AddNewProduct fragment = new AddNewProduct();
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

        apiLink = RetrofitClient.getInstance().create(APILink.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //pickers references
        pickimagetext =view.findViewById(R.id.pickimagetext);
        thepickedimage =view.findViewById(R.id.imagepicked);
        pickimageplus =view.findViewById(R.id.pickfileplus);
        modelpickertext = view.findViewById(R.id.modelpickertext);
        modelnametext = view.findViewById(R.id.pickedmodelname);
        pickmodelplus = view.findViewById(R.id.modelpickplus);
        //Start Setting References
        NameInputField = view.findViewById(R.id.NameInputField);
        PriceInputField = view.findViewById(R.id.PriceInputField);
        DescriptionInputField = view.findViewById(R.id.DescriptionInputField);
        //Category DropDown
        CategorieDropDown=view.findViewById(R.id.CategorieDropDown);
        adapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item,items);
        CategorieDropDown.setAdapter(adapterItems);
        WebLinkInputField = view.findViewById(R.id.WebSiteInputField);
        WidthInputField = view.findViewById(R.id.WidthInputField);
        HeightInputField = view.findViewById(R.id.HeightInputField);
        DepthInputField = view.findViewById(R.id.DepthInputField);
        WeightInputField = view.findViewById(R.id.WeightInputField);
        VolumeInputField = view.findViewById(R.id.VolumeInputField);
        //End References


        CategorieDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String item = parent.getItemAtPosition(i).toString();
                Toast.makeText(getContext(),"Item"+item,Toast.LENGTH_SHORT).show();
            }
        });

        //Return Button Listener
        ImageButton ReturnToSellerHome = (ImageButton) view.findViewById(R.id.ReturnToSellerHome);
        ReturnToSellerHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        //Photo Picker ActivityResultLauncher
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        ImageUri = uri;

                        File image = new File(FileHelper.getRealPathFromURI(getContext(), ImageUri));
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                        //bitmap = Bitmap.createScaledBitmap(bitmap,5,5,true);
                        thepickedimage.setImageBitmap(bitmap);
                        thepickedimage.setVisibility(View.VISIBLE);
                        pickimageplus.setVisibility(View.INVISIBLE);
                        pickimagetext.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                        thepickedimage.setVisibility(View.INVISIBLE);
                        pickimageplus.setVisibility(View.VISIBLE);
                        pickimagetext.setVisibility(View.VISIBLE);
                    }
                });

        //File Picker ActivityResultLauncher
        ActivityResultLauncher<Intent> pickModel = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),uri->{
                    if (uri.getData() != null) {
                        Log.d("filePicker", "Selected URI: " + uri);
                        ModelUri =uri.getData().getData();
                        modelpickertext.setText("Selected Model");

                        int cut = ModelUri.getPath().lastIndexOf('/');
                        String result = null;
                        if (cut != -1) {
                            result = ModelUri.getPath().substring(cut + 1);
                        }
                        modelnametext.setText(result);
                        modelnametext.setVisibility(View.VISIBLE);
                        pickmodelplus.setVisibility(View.INVISIBLE);


                    } else {
                        Log.d("filePicker", "No file selected");
                        pickmodelplus.setVisibility(View.VISIBLE);
                        modelpickertext.setText("Upload 3D Model");
                        modelnametext.setVisibility(View.INVISIBLE);
                    }
                });



        //Photo Selector Listener

        RelativeLayout SelectImage = (RelativeLayout) view.findViewById(R.id.SelectProductImage);

        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });


        //Model Selector Listener

        RelativeLayout SelectModel = (RelativeLayout) view.findViewById(R.id.SelectProductModel);

        SelectModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/octet-stream");
                pickModel.launch(intent);
            }
        });

        //On Create New Product Button Clicked

        Button CreateNewProductButton = (Button) view.findViewById(R.id.CreateNewProductButton);

        CreateNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(ValidateInputs());
                if(ValidateInputs()){
                    //Upload Image and Save ImageID
                    UploadProduct();
                    ShowSnakeBar("Publishing your product ...");
                }
                else ShowSnakeBar("some informations are missing");
            }
        });
    }

    /*
    private void UploadImage(){
        File imagefile = new File(FileHelper.getRealPathFromURI(getContext(),ImageUri));

        File CompressedImageFile;
        try {
            CompressedImageFile = File.createTempFile("TempCompressedImage", ".png",getActivity().getApplicationContext().getCacheDir());
            CompressedImageFile = new Compressor(getContext()).setMaxHeight(400).setMaxWidth(500).compressToFile(imagefile);

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


        Call<GUIDDto> UploadImage = apiLink.uploadimage(body);
        File finalCompressedImageFile = CompressedImageFile;
        UploadImage.enqueue(new Callback<GUIDDto>() {
            @Override
            public void onResponse(Call<GUIDDto> call, Response<GUIDDto> response) {
                ImageID = response.body().id;
                System.out.println(response.body().id);

                //Clean Up
                finalCompressedImageFile.delete();


                //Upload Model After image Uploaded
                UploadModel();

            }

            @Override
            public void onFailure(Call<GUIDDto> call, Throwable t) {
                System.out.println(t);
            }
        });


    }
    private void UploadModel(){
        //Upload Model and Save ModelID
        String ModelPath = FileHelper.getRealPathFromURI(getContext(),ModelUri);
        File modelfile = new File(ModelPath);
        ModelExtension = GetFileExtension(ModelPath);

        RequestBody requestModelFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(ModelUri)),
                        modelfile
                );
        MultipartBody.Part Modelbody =
                MultipartBody.Part.createFormData("Model", modelfile.getName(), requestModelFile);


        Call<GUIDDto> UploadFile = apiLink.uploadmodel(Modelbody);

        UploadFile.enqueue(new Callback<GUIDDto>() {
            @Override
            public void onResponse(Call<GUIDDto> call, Response<GUIDDto> response) {
                ModelID = response.body().id;
                System.out.println("Model ID : " + response.body().id);

                //Create new Product Dto and Upload it to Database
                CreateProduct();
            }

            @Override
            public void onFailure(Call<GUIDDto> call, Throwable t) {
                System.out.println("ls;;oasdipoasdiaopsdoaidoasid "+t);
            }
        });
    }

    private void CreateProduct(){
        User SellerAccount = Paper.book().read("User");
        productDto productdto = new productDto(
                NameInputField.getText().toString(),
                Float.valueOf(PriceInputField.getText().toString()),
                DescriptionInputField.getText().toString(),
                CategorieDropDown.getText().toString(),
                SellerAccount.id,
                SellerAccount.userName,
                WebLinkInputField.getText().toString(),
                Float.valueOf(WidthInputField.getText().toString()),
                Float.valueOf(HeightInputField.getText().toString()),
                Float.valueOf(DepthInputField.getText().toString()),
                Float.valueOf(WeightInputField.getText().toString()),
                Float.valueOf(VolumeInputField.getText().toString()),
                ImageID,
                ModelID,
                ModelExtension
                );

        Call<product> UploadProduct = apiLink.uploadproduct(productdto);

        UploadProduct.enqueue(new Callback<product>() {
            @Override
            public void onResponse(Call<product> call, Response<product> response) {
                System.out.println(response.body().id);
                ShowSnakeBar("Your product has been created !");
            }

            @Override
            public void onFailure(Call<product> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

     */
    private void UploadProduct(){
        //image bodyPart
        File imagefile = new File(FileHelper.getRealPathFromURI(getContext(),ImageUri));

        File CompressedImageFile;
        try {
            CompressedImageFile = File.createTempFile("TempCompressedImage", ".png",getActivity().getApplicationContext().getCacheDir());
            CompressedImageFile = new Compressor(getContext()).setMaxHeight(400).setMaxWidth(500).compressToFile(imagefile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(ImageUri)),
                        CompressedImageFile
                );
        MultipartBody.Part imagebody =
                MultipartBody.Part.createFormData("ImageFile", CompressedImageFile.getName(), requestFile);

        //model Body part

        //Upload Model and Save ModelID
        String ModelPath = FileHelper.getRealPathFromURI(getContext(),ModelUri);
        File modelfile = new File(ModelPath);
        ModelExtension = GetFileExtension(ModelPath);

        RequestBody requestModelFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(ModelUri)),
                        modelfile
                );
        MultipartBody.Part modelbody =
                MultipartBody.Part.createFormData("ModelFile", modelfile.getName(), requestModelFile);



        User SellerAccount = Paper.book().read("User");
        productDto productdto = new productDto(
                NameInputField.getText().toString(),
                Float.valueOf(PriceInputField.getText().toString()),
                DescriptionInputField.getText().toString(),
                CategorieDropDown.getText().toString(),
                SellerAccount.id,
                SellerAccount.userName,
                WebLinkInputField.getText().toString(),
                Float.valueOf(WidthInputField.getText().toString()),
                Float.valueOf(HeightInputField.getText().toString()),
                Float.valueOf(DepthInputField.getText().toString()),
                Float.valueOf(WeightInputField.getText().toString()),
                Float.valueOf(VolumeInputField.getText().toString()),
                ModelExtension
        );

        RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), productdto.Name);
        RequestBody Price = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productdto.Price));
        RequestBody Description = RequestBody.create(MediaType.parse("text/plain"), productdto.Description);
        RequestBody Categorie = RequestBody.create(MediaType.parse("text/plain"), productdto.Categorie);
        RequestBody SellerID = RequestBody.create(MediaType.parse("text/plain"), productdto.SellerID);
        RequestBody SellerName = RequestBody.create(MediaType.parse("text/plain"), productdto.SellerName);
        RequestBody WebLink = RequestBody.create(MediaType.parse("text/plain"), productdto.WebLink);
        RequestBody Width = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productdto.Width));
        RequestBody Height = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productdto.Height));
        RequestBody Depth = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productdto.Depth));
        RequestBody Weight = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productdto.Weight));
        RequestBody Volume = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productdto.Volume));
        RequestBody ModelExtension = RequestBody.create(MediaType.parse("text/plain"), productdto.ModelExtension);


        Call<Void> UploadProduct = apiLink.uploadProduct(
                imagebody,
                modelbody,
                Name,
                Price,
                Description,
                Categorie,
                SellerID,
                SellerName,
                WebLink,
                Width,
                Height,
                Depth,
                Weight,
                Volume,
                ModelExtension);
        UploadProduct.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println(response.message());
                    ShowSnakeBar("Your product has been uploaded");


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t);
            }
        });
    }


    public boolean ValidateInputs(){
        if(isEmpty(NameInputField))
            return false;
        if(isEmpty(PriceInputField))
            return false;
        if(isEmpty(DescriptionInputField))
            return false;
        if(CategorieDropDown.getText().toString().trim().length() == 0)
            return false;
        if(isEmpty(WebLinkInputField) || !(WebLinkInputField.getText().toString().startsWith("https://") || WebLinkInputField.getText().toString().startsWith("http://")))
            return false;
        if(isEmpty(PriceInputField))
            return false;
        if(isEmpty(WidthInputField))
            return false;
        if(isEmpty(HeightInputField))
            return false;
        if(isEmpty(DepthInputField))
            return false;
        if(isEmpty(WeightInputField))
            return false;
        if(isEmpty(VolumeInputField))
            return false;
        if(ImageUri == null)
            return false;
        if(ModelUri == null)
            return false;

        return  true;

    }

    private boolean isEmpty(TextInputEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void ShowSnakeBar(String Text){
        Snackbar snackbar = Snackbar
                .make((CoordinatorLayout)getActivity().findViewById(R.id.HomeSnackBar), Text, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.BLACK);
        snackbar.show();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        //Exit Animation
        ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 0f);
        animation.setDuration(500);
        animation.start();
    }

    public String GetFileExtension(String filePath){
        int strLength = filePath.lastIndexOf(".");
        if(strLength > 0)
            return filePath.substring(strLength).toLowerCase();
        return null;
    }
}