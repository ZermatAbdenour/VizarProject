package com.example.vizar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Model.product;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.BaseGridConcatAdapter;
import com.example.vizar.Remote.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private APILink apiLink;

    private List<String> autocompletelist = new ArrayList<>();
    private List<product> resultList = new ArrayList<>();
    private int Offset;
    private int ProductsCountPerCall=10;
    private Adapter resulteadapter;
    private ConcatAdapter concatAdapter;
    private int LastOffset;
    private boolean FirstCall;
    private boolean isLoading;
    TextInputEditText searchtext;
    private ImageButton backbutton;
    private View FiltersButton;
    private View Search_Nav;


    TextInputLayout textInputLayout;
    private RecyclerView searchrecycler;
    private View searchview;
    private View FiltersBottomSheetView;
    private String currentcat = "All categories";
    private int[] filterstate={1,0,0,0,0,0,0};
    int[] catid = {R.id.Allcategories,R.id.Wardrobes,   R.id.Sofas, R.id.Dressers,R.id.Armchairs, R.id.Dining_Tables,R.id.TV_Stands};

    private TextInputEditText Minprice,Maxprice;
    private RangeSlider Pricerange;
    private float[] pricerange ={0,10000};


    private boolean allproductsloaded;
    private footeradapter footer;

    public searchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         apiLink = RetrofitClient.getInstance().create(APILink.class);
         autocompletelist.clear();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Set ButtomNav
        Home.ButtomNavSearch(this);

        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {




searchview = view;
        textInputLayout = view.findViewById(R.id.input);
        searchtext = view.findViewById(R.id.search_bar);
        Search_Nav = view.findViewById(R.id.linearLayout2);
        backbutton = view.findViewById(R.id.Backarrow);
         FiltersButton =  view.findViewById(R.id.FilterButton);
         searchrecycler = view.findViewById(R.id.search_recyclerview);
        searchrecycler.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        autocompleteadapter autocompleteadapter = new autocompleteadapter(autocompletelist,searchtext,searchFragment.this);
        searchrecycler.setAdapter(autocompleteadapter);


        resulteadapter = new Adapter(resultList,R.layout.product,false,false,getActivity());

        footer = new footeradapter(R.layout.footer);
        concatAdapter = new ConcatAdapter(new BaseGridConcatAdapter(getContext(),resulteadapter,2,"Results"),footer);

        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                        searchtext.setText("");
                        autocompletelist.clear();
                        autocompleteadapter.notifyDataSetChanged();

            }
        });

        String cat = Paper.book().read("category");
        if(cat != null)
        if(cat.length()>0)
        {
            filterstate[0]=0;
            currentcat = cat;
            FirstCall = false;
            Offset = 0;
            resultList.clear();
            searchrecycler.setAdapter(concatAdapter);
            GetFirstProducts(searchtext.getText().toString(),currentcat);
            Paper.book().write("category","");
            switch (currentcat){
                case "Wardrobes": filterstate[1]=1;      break;
                case "Armchairs":filterstate[4]=1;       break;
                case "Dining Table":filterstate[5]=1;        break;
                case "Dressers":filterstate[3]=1;        break;
                case "Sofas":filterstate[2]=1;       break;
                case "TV Stands":filterstate[6]=1;        break;
            }

        }else {

        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                            searchtext.clearFocus();
                if(searchtext.getText()!=null) {
                    Call<List<product>> autocomplete = apiLink.autocomplete(searchtext.getText().toString());
                    autocomplete.enqueue(new Callback<List<product>>() {
                        @Override
                        public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                            autocompletelist.clear();
                            if(response.body()!=null){
                                for(int i = 0;i<response.body().size();i++)
                                {
                                    autocompletelist.add(response.body().get(i).name);



                                }
                                autocompleteadapter.notifyDataSetChanged();
                            }}

                        @Override
                        public void onFailure(Call<List<product>> call, Throwable t) {

                        }
                    });
                }else {

                    autocompletelist.clear();
                    autocompleteadapter.notifyDataSetChanged();
                }

            }
        });

        searchtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus)
                {

                    backbutton.setVisibility(View.VISIBLE);
                    FiltersButton.setVisibility(View.GONE);


                    //animation navbar

                    ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 500f);
                    animation.setDuration(500);
                    animation.start();

                    searchrecycler.setAdapter(autocompleteadapter);
                    FirstCall = false;
                    Offset = 0;

                }else
                {
                    FiltersButton.setVisibility(View.VISIBLE);
                    backbutton.setVisibility(View.GONE);
                    //animation navbar
                    ObjectAnimator animation = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.Navbar), "translationY", 0f);
                    animation.setDuration(500);
                    animation.start();
                }
            }
        });

        if(searchtext.isFocused())
        {


        }
        else
        {


        }

        //    FILTER

       FiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog FiltersBottomSheet = new BottomSheetDialog(getContext(),R.style.BottomSheetDialogTheme);
                float[] pricebefore = {pricerange[0],pricerange[1]};
                FiltersBottomSheetView = getLayoutInflater().inflate(R.layout.search_bottom_sheet, null,false);
                Pricerange = FiltersBottomSheetView.findViewById(R.id.Pricerange);
                Maxprice = FiltersBottomSheetView.findViewById(R.id.Maxprice);
                Minprice = FiltersBottomSheetView.findViewById(R.id.MinPrice);
                Pricerange.setLabelFormatter(new LabelFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        NumberFormat format = NumberFormat.getCurrencyInstance();
                        format.setMaximumFractionDigits(0);
                        format.setCurrency(Currency.getInstance("USD"));
                        return format.format((double) value);
                    }
                });


                // set price state

                Pricerange.setValues(pricerange[0],pricerange[1]);
                String pricerangemax= String.valueOf(pricerange[1]);
                Maxprice.setText( pricerangemax);
                String pricerangemin=  String.valueOf(pricerange[0]);
                Minprice.setText( pricerangemin);


                        //set filterstat
                for (int i=0 ;i<=6;i++) {

                    CheckBox c = FiltersBottomSheetView.findViewById(catid[i]);
                    if (filterstate[i] == 1) {
                        c.setActivated(true);
                        c.setChecked(true);

                    }

                }



                for(int i=0;i<=6;i++)
                {
                    CheckBox c = FiltersBottomSheetView.findViewById(catid[i]);
                    int finalI = i;
                    c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        if(b)
                                        {
                                            filterstate[finalI] =1;
                                            c.setActivated(true);
                                        }
                                        else
                                         {
                                            c.setActivated(false);
                                             filterstate[finalI] = 0;
                                         }

                                        currentcat = categorie();
                                        Search();

                        }
                    });
                }
                currentcat =  categorie();
               //Price

                Maxprice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if(Float.parseFloat(Maxprice.getText().toString()) >10000)
                            {
                                pricerange[1] = 10000;


                            }
                            else if(Float.parseFloat(Maxprice.getText().toString())<0)
                            {
                                pricerange[1]=pricerange[0];

                            }else if (Float.parseFloat(Maxprice.getText().toString())<Float.parseFloat(Minprice.getText().toString())) {

                                pricerange[1] = Float.parseFloat(Maxprice.getText().toString());
                                pricerange[0]  = pricerange[1];

                            }else {
                                pricerange[1] = Float.parseFloat(Maxprice.getText().toString());
                            }
                            Maxprice.setText(String.valueOf(pricerange[1]));
                            Pricerange.setValues(pricerange[0],pricerange[1]);

                            return true;
                        }
                        return false;
                    }
                });
               Minprice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                   @Override
                   public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                       if (actionId == EditorInfo.IME_ACTION_DONE) {
                                    if(Float.parseFloat(Minprice.getText().toString()) >10000)
                                    {
                                        pricerange[0] = pricerange[1];


                                    }

                                    else if(Float.parseFloat(Minprice.getText().toString())<0)
                                    {
                                        pricerange[0]=0;

                                    } else if (Float.parseFloat(Minprice.getText().toString())>Float.parseFloat(Maxprice.getText().toString())) {

                                        pricerange[0] = Float.parseFloat(Minprice.getText().toString());
                                        pricerange[1]  = pricerange[0];

                                    } else {
                                        pricerange[0] = Float.parseFloat(Minprice.getText().toString());

                                    }
                           Minprice.setText(String.valueOf(pricerange[0]));
                           Pricerange.setValues(pricerange[0],pricerange[1]);


                           return true;
                       }
                       return false;
                   }
               });

                Pricerange.addOnChangeListener(new RangeSlider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                        pricerange[1] =Pricerange.getValues().get(1);
                        pricerange[0] =Pricerange.getValues().get(0);
                            Maxprice.setText(String.valueOf(pricerange[1]) );
                            Minprice.setText(String.valueOf(pricerange[0]));


                    }
                });




                FiltersBottomSheetView.findViewById(R.id.ExitFiltersBottomSheet).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(pricebefore[0]!=pricerange[0]||pricebefore[1]!=pricerange[1])
                        {
                        Search();

                        }
                        FiltersBottomSheet.dismiss();
                    }
                });
                FiltersBottomSheet.setContentView(FiltersBottomSheetView);
                FiltersBottomSheet.show();
            }
        });

        //Search


        searchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   // performSearch();
                        Search();
                    /*InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    searchtext.clearFocus();
                    resultList.clear();
                    searchrecycler.setAdapter(concatAdapter);
                    GetFirstProducts(searchtext.getText().toString(),"All categories");*/

                    return true;
                }
                return false;
            }
        });



        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(searchtext.getText().toString());
                if(searchtext.getText().toString()!="") {
                    Call<List<product>> autocomplete = apiLink.autocomplete(searchtext.getText().toString());
                autocomplete.enqueue(new Callback<List<product>>() {
                    @Override
                    public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                            autocompletelist.clear();
                        if(response.body()!=null){
                        for(int i = 0;i<response.body().size();i++)
                        {
                            autocompletelist.add(response.body().get(i).name);



                        }
                            autocompleteadapter.notifyDataSetChanged();
                    }}

                    @Override
                    public void onFailure(Call<List<product>> call, Throwable t) {

                    }
                });
            }else {

                    autocompletelist.clear();
                    autocompleteadapter.notifyDataSetChanged();
                }
                }
        });

        searchrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)&& !isLoading && FirstCall && Offset!=LastOffset&&searchrecycler.getAdapter()==concatAdapter&&!allproductsloaded) {
                    isLoading =true;


                    //concatAdapter.notifyDataSetChanged();
                    resulteadapter.notifyItemRangeInserted(Offset,ProductsCountPerCall);

                    System.out.println(concatAdapter.getItemCount());
                    LastOffset = Offset;
                    GetProducts(searchtext.getText().toString(),currentcat);

                }
                else
                    isLoading = false;

            }
        });

    }
    Call<List<product>> fsearch;
    public void GetFirstProducts(String text,String catigories){
        allproductsloaded=false;
        System.out.println(text);
       fsearch = apiLink.Search(text,Offset,ProductsCountPerCall,catigories,pricerange[0],pricerange[1]);
       if(fsearch.isExecuted())
        fsearch.cancel();

        fsearch.enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                if(response.body() != null){
                    resultList.addAll(response.body());
                    resulteadapter.notifyDataSetChanged();
                    Offset=response.body().size();
                    LastOffset=response.body().size();
                    FirstCall = true;
                    GetProducts(text,catigories);
                }
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {

            }
        });
    }
    Call<List<product>> search;
    public void GetProducts(String text,String catigories){
         search = apiLink.Search(text,Offset,ProductsCountPerCall,catigories,pricerange[0],pricerange[1]);
if(search.isExecuted())
        search.cancel();
        search.enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                if(response.body().size()>0){
                    resultList.addAll(response.body());
                    Offset+=response.body().size();

                }else {
                    allproductsloaded=true;
                    System.out.println("response size : "+response.body().size()+"       655555555555555555555555555555555555555555555555555555555555555555");
                    concatAdapter.removeAdapter(footer);
                    footer = new footeradapter(R.layout.savedfooter);
                    concatAdapter.addAdapter(footer);
              //      concatAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {

            }
        });
    }
    public void Search(){



        FirstCall = false;
        Offset = 0;

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0);
       concatAdapter.removeAdapter(footer);
        footer = new footeradapter(R.layout.footer);
        concatAdapter.addAdapter(footer);
        searchtext.clearFocus();
        resultList.clear();
        searchrecycler.setAdapter(concatAdapter);
        GetFirstProducts(searchtext.getText().toString(),currentcat);
    }



        public String categorie() {

            String s = "";


            for (int i : catid) {

                CheckBox c = FiltersBottomSheetView.findViewById(i);
                if (c.isActivated()) {
                    if (s == "")
                        s = c.getText().toString();
                    else
                        s += " | " + c.getText().toString();
                }


            }
            System.out.println(s +"            "+ "15121hgfdghfddfgdfgdfgdfgdfgfdgdfgfdgdfgdfgdfgdfgdfgdfgfdgdfgdfgdfgfdgdfgdfgdg");
            System.out.println(R.id.Dining_Tables + "15121hgfdghfddfgdfgdfgdfgdfgfdgdfgfdgdfgdfgdfgdfgdfgdfgfdgdfgdfgdfgfdgdfgdfgdg");
            System.out.println(R.id.Dressers + "15121hgfdghfddfgdfgdfgdfgdfgfdgdfgfdgdfgdfgdfgdfgdfgdfgfdgdfgdfgdfgfdgdfgdfgdg");
            System.out.println(R.id.Sofas + "15121hgfdghfddfgdfgdfgdfgdfgfdgdfgfdgdfgdfgdfgdfgdfgdfgfdgdfgdfgdfgfdgdfgdfgdg");
            System.out.println(R.id.Wardrobes + "15121hgfdghfddfgdfgdfgdfgdfgfdgdfgfdgdfgdfgdfgdfgdfgdfgfdgdfgdfgdfgfdgdfgdfgdg");

   return s;
        }



}
