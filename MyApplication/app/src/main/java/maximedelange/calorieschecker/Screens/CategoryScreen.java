package maximedelange.calorieschecker.Screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import maximedelange.calorieschecker.R;

public class CategoryScreen extends AppCompatActivity {

    // Fields
    private Bitmap bitmap;
    private Bitmap resizedbitmap;

    // GUI Components
    private TextView lblBreakfast;
    private TextView lblLunch;
    private TextView lblDinner;
    private ImageButton btnCategory;
    private ImageButton breakfastImage;
    private ImageButton lunchImage;
    private ImageButton dinnerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goToBreakfast();
        goToLunch();
        goToDinner();
    }

    public void goToBreakfast(){
        breakfastImage = (ImageButton)findViewById(R.id.btnBreakfast);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.breakfast);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        breakfastImage.setImageBitmap(resizedbitmap);

        lblBreakfast = (TextView)findViewById(R.id.lblBreakfastShow);
        lblBreakfast.setText("Breakfast");
        lblBreakfast.setTextSize(30);
        btnCategory = (ImageButton)findViewById(R.id.btnBreakfast);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BreakfastScreen.class);
                startActivity(intent);
            }
        });
    }

    public void goToLunch(){
        lunchImage = (ImageButton)findViewById(R.id.btnLunch);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lunch);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        lunchImage.setImageBitmap(resizedbitmap);

        lblLunch = (TextView)findViewById(R.id.lblLunchShow);
        lblLunch.setText("Lunch");
        lblLunch.setTextSize(30);
        btnCategory = (ImageButton)findViewById(R.id.btnLunch);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LunchScreen.class);
                startActivity(intent);
            }
        });
    }

    public void goToDinner(){
        dinnerImage = (ImageButton)findViewById(R.id.btnDinner);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dinner);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        dinnerImage.setImageBitmap(resizedbitmap);

        lblDinner = (TextView)findViewById(R.id.lblDinnerShow);
        lblDinner.setText("Dinner");
        lblDinner.setTextSize(30);
        btnCategory = (ImageButton)findViewById(R.id.btnDinner);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DinnerScreen.class);
                startActivity(intent);
            }
        });
    }
}
