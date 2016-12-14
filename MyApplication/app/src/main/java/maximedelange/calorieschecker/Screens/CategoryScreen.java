package maximedelange.calorieschecker.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class CategoryScreen extends AppCompatActivity {

    // GUI Components
    private TextView lblBreakfast;
    private TextView lblLunch;
    private TextView lblDinner;
    private ImageButton btnCategory;

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
        lblBreakfast = (TextView)findViewById(R.id.lblBreakfastShow);
        lblBreakfast.setText("Breakfast");
        lblBreakfast.setTextSize(24);
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
        lblLunch = (TextView)findViewById(R.id.lblLunchShow);
        lblLunch.setText("Lunch");
        lblLunch.setTextSize(24);
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
        lblDinner = (TextView)findViewById(R.id.lblDinnerShow);
        lblDinner.setText("Dinner");
        lblDinner.setTextSize(24);
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
