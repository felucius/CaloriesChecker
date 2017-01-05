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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Database.Database;
import maximedelange.calorieschecker.Domain.DayOfTheWeek;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class CategoryScreen extends AppCompatActivity implements Serializable {

    // Fields
    private String calorieInformation;
    private ProductController productController;
    private Database database;
    @SuppressWarnings("unchecked")
    private ArrayList<Product> products;
    private int totalCalories;
    private String day;
    private int currentDay;

    // GUI Components
    private Button breakfastImage;
    private Button lunchImage;
    private Button dinnerImage;
    private Button caloriesListImage;
    private Button productImage;
    private Button dismisspopup;
    private ActionBar actionBar;

    // Time
    Date date = new Date();
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new Database(this, null, null, 1);

        calendar = GregorianCalendar.getInstance();
        System.out.println("CURRENT MONTH: " + date.getMonth());
        calendar.setTime(date);

        currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        day = database.getDateTime();
        totalCalories = database.readCaloriesFromDatabase(day, currentDay);

        productController = new ProductController();
        goToBreakfast();
        goToLunch();
        goToDinner();
        goToProductList();
        goToCaloriesList();
        changeStatusBar(totalCalories);
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
