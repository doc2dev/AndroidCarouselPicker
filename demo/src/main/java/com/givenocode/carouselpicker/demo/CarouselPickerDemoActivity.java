package com.givenocode.carouselpicker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.givenocode.carouselpicker.CarouselPickerFragment;
import com.givenocode.carouselpicker.demo.adapter.ImageAdapter;
import com.givenocode.carouselpicker.demo.adapter.TextAdapter;


public class CarouselPickerDemoActivity extends AppCompatActivity {

    CarouselPickerFragment fullWidthImage;
    CarouselPickerFragment halfWidthImage;
    CarouselPickerFragment fulWidthText;

    TextView tinyText;
    TextView littleText;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel_picker_demo);

        text = (TextView) findViewById(R.id.text);
        littleText = (TextView) findViewById(R.id.little_text);
        tinyText = (TextView) findViewById(R.id.tiny_text);

        initCarousels();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.full_width_image, fullWidthImage)
                .add(R.id.half_width_image, halfWidthImage)
                .add(R.id.full_width_text, fulWidthText)
                .commit();
    }

    private void initCarousels() {
        // Ful width image
        fullWidthImage = new CarouselPickerFragment();
        fullWidthImage.setAdapter(new ImageAdapter(this));

        // Half width image
        halfWidthImage = new CarouselPickerFragment();
        halfWidthImage.setAdapter(new ImageAdapter(this));
        halfWidthImage.setItemSpacing((int) getResources().getDimension(R.dimen.item_spacing));

        // Ful width text
        fulWidthText = new CarouselPickerFragment();
        fulWidthText.setAdapter(new TextAdapter(this));
        fulWidthText.setListener(new CarouselPickerFragment.Listener() {
            @Override
            public void onItemPreSelected(long itemId) {
                String t = getResources().getStringArray(R.array.string_items)[(int) itemId];
                littleText.setText(t);

            }

            @Override
            public void onItemSelected(long itemId) {
                String t = getResources().getStringArray(R.array.string_items)[(int) itemId];
                text.setText(t);
            }

            @Override
            public void onScrollOver(long itemId) {
                String t = getResources().getStringArray(R.array.string_items)[(int) itemId];
                tinyText.setText(t);

            }
        });
    }
}


