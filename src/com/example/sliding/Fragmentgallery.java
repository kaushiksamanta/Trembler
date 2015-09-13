package com.example.sliding;
import com.example.sliding.R;
import com.gallery.adapter.Galleryadapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

public class Fragmentgallery extends Fragment {
	ImageView selectedImage; 
    private Integer[] mImageIds = {
    		R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6,
            R.drawable.i7,
            R.drawable.i8,
            R.drawable.i9,
            R.drawable.i10,
            R.drawable.i11,
            R.drawable.i12,
            R.drawable.i13,
            R.drawable.i14
       };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
       /* Gallery gallery = (Gallery)rootView.findViewById(R.id.gallery1);
        selectedImage=(ImageView)rootView.findViewById(R.id.imageView1);
        gallery.setSpacing(1);
        //Galleryadapter n=new Galleryadapter(this);
        //gallery.setAdapter(n);

         //  for Gallery
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(MainActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
                // show the selected Image
                selectedImage.setImageResource(mImageIds[position]);
            }
        });*/
        return rootView;
    }
}
