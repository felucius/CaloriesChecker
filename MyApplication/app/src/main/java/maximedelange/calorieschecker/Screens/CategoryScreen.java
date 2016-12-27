package maximedelange.calorieschecker.Screens;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private Button btnCategory;
    private Button breakfastImage;
    private Button lunchImage;
    private Button dinnerImage;
    private Button caloriesListImage;
    private Button productImage;
    private Button dismisspopup;
    private Button removeproduct;
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
        breakfastImage = (Button)findViewById(R.id.btnBreakfast);
        breakfastImage.setOnClickListener(new View.OnClickListener() {
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
        lunchImage = (Button)findViewById(R.id.btnLunch);
        lunchImage.setOnClickListener(new View.OnClickListener() {
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
        dinnerImage = (Button)findViewById(R.id.btnDinner);
        dinnerImage.setOnClickListener(new View.OnClickListener() {
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
        caloriesListImage = (Button)findViewById(R.id.btnCaloriesList);
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
        productImage = (Button)findViewById(R.id.btnAddProduct);
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
        actionBar.setTitle("Total calories: " + calories);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getCalorieInformation(){
        Intent intent = getIntent();
        calorieInformation = intent.getStringExtra("totalCalories");

        if(calorieInformation != null){
            actionBar.setTitle("Total calories: " + calorieInformation);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.credits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_credits:

                final Dialog dialog = new Dialog(CategoryScreen.this);
                dialog.setContentView(R.layout.popupcredits);
                dialog.show();

                dismisspopup = (Button)dialog.findViewById(R.id.dismissPopup);
                dismisspopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Disables the button back press
    }
}
