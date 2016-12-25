package maximedelange.calorieschecker.Screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class CategoryScreen extends AppCompatActivity implements Serializable {

    // Fields
    private Bitmap bitmap;
    private Bitmap resizedbitmap;
    private String calorieInformation;
    private String productInformation;
    private ProductController productController;
    @SuppressWarnings("unchecked")
    private ArrayList<Product> products;

    // GUI Components
    private ImageButton btnCategory;
    private ImageButton breakfastImage;
    private ImageButton lunchImage;
    private ImageButton dinnerImage;
    private ImageButton caloriesListImage;
    private ImageButton productImage;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();
        goToBreakfast();
        goToLunch();
        goToDinner();
        goToProductList();
        goToCaloriesList();
        changeStatusBar(0);
        getCalorieInformation();
        getProductInformation();
    }

    public void goToBreakfast(){
        breakfastImage = (ImageButton)findViewById(R.id.btnBreakfast);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.breakfast);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        breakfastImage.setImageBitmap(resizedbitmap);

        btnCategory = (ImageButton)findViewById(R.id.btnBreakfast);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(v.getContext(), BreakfastScreen.class);
                calorieInformation = getIntent.getStringExtra("totalCalories");
                intent.putExtra("totalCalories", calorieInformation);
                intent.putExtra("totalProducts", products);
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

        btnCategory = (ImageButton)findViewById(R.id.btnLunch);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(v.getContext(), LunchScreen.class);
                calorieInformation = getIntent.getStringExtra("totalCalories");
                intent.putExtra("totalCalories", calorieInformation);
                intent.putExtra("totalProducts", products);
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

        btnCategory = (ImageButton)findViewById(R.id.btnDinner);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(v.getContext(), DinnerScreen.class);
                calorieInformation = getIntent.getStringExtra("totalCalories");
                intent.putExtra("totalCalories", calorieInformation);
                intent.putExtra("totalProducts", products);
                startActivity(intent);
            }
        });
    }

    public void goToCaloriesList(){
        caloriesListImage = (ImageButton)findViewById(R.id.btnCaloriesList);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.calender);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        caloriesListImage.setImageBitmap(resizedbitmap);

        caloriesListImage = (ImageButton)findViewById(R.id.btnCaloriesList);
        caloriesListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(v.getContext(), CaloriesListScreen.class);
                calorieInformation = getIntent.getStringExtra("totalCalories");
                intent.putExtra("totalCalories", calorieInformation);
                startActivity(intent);
            }
        });
    }

    public void goToProductList(){
        productImage = (ImageButton)findViewById(R.id.btnAddProduct);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        productImage.setImageBitmap(resizedbitmap);

        productImage = (ImageButton)findViewById(R.id.btnAddProduct);
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(v.getContext(), ProductScreen.class);
                calorieInformation = getIntent.getStringExtra("totalCalories");
                intent.putExtra("totalProducts", products);
                intent.putExtra("totalCalories", calorieInformation);
                startActivity(intent);
            }
        });
    }

    public void changeStatusBar(int calories){
        actionBar = getSupportActionBar();
        actionBar.setTitle("Totaal calorieën " + calories);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getCalorieInformation(){
        Intent intent = getIntent();
        calorieInformation = intent.getStringExtra("totalCalories");

        if(calorieInformation != null){
            actionBar.setTitle("Totaal calorieën " + calorieInformation);
        }
    }

    public void getProductInformation(){
        Intent intent = getIntent();
        //productInformation = intent.getStringExtra("totalProducts");

        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");

        if(products != null){
            productController.setProducts(products);
            System.out.println("PRODUCT INFORMATION: " + products.size());
        }
    }

    @Override
    public void onBackPressed() {
        // Disables the button back press
    }
}
