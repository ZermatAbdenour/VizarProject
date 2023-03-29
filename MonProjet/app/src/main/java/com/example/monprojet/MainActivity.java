package com.example.monprojet;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.skyhope.showmoretextview.ShowMoreTextView;


public class MainActivity extends AppCompatActivity {
    public ShowMoreTextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShowMoreTextView textView = findViewById(R.id.text_view_show_more);
        textView.setShowingLine(4);
        textView.addShowMoreText("Read more ");
        textView.addShowLessText("Less");
        textView.setShowMoreColor(R.color.brown);
        textView.setShowLessTextColor(R.color.brown);
    }


}