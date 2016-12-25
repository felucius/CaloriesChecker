package maximedelange.calorieschecker.Screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class LunchScreen extends AppCompatActivity implements Serializable{

    // Fields
    private ProductController productController = null;
    private TableLayout tableLayout = null;
    private TableRow tableRow = null;
    private Bitmap bitmap = null;
    private Bitmap resizedbitmap = null;
    private CalorieCounter calorieCounter = null;
    private boolean isClicked = false;
    private ArrayList<Product> products;

    // GUI Components
    private TextView textArray;
    private TextView textCaloriesArray;
    private TextView textProductArray;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();
        calorieCounter = new CalorieCounter();
        changeStatusBar(0);
        getTotalCalories();
        getTotalProducts();
        getTableLayoutLunchProducts();
    }

    public void getTableLayoutLunchProducts() {
        tableLayout = (TableLayout)findViewById(R.id.tableLayoutLunch);

        // Creating a table row for each product in productController.
        for (final Product product : productController.getProducts()) {
            if(product.getCategoryType() == CategoryType.Lunch){

                // Create tablerows
                tableRow = new TableRow(this);
                tableRow.setId(product.getID() + 1);
                tableRow.setBackgroundColor(Color.WHITE);
                tableRow.setLayoutParams(new Toolbar.LayoutParams(300, 300));

                // Create content for tablerows
                textArray = new TextView(this);
                textCaloriesArray = new TextView(this);
                textProductArray = new TextView(this);

                // Add image for each row
                image = new ImageView(this);
                //image.setPadding(100, 100, 100, 100);

                bitmap = BitmapFactory.decodeResource(getResources(), product.getImage());
                int width=400;
                int height=400;
                resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                image.setImageBitmap(resizedbitmap);
                tableRow.addView(image);

                textArray.setText(product.getName());
                textCaloriesArray.setText(" Cal: " + String.valueOf(product.getCalories()));
                textProductArray.setText(" Prod: " + String.valueOf(product.getProductType()));

                textArray.setTextSize(18);
                textArray.setTypeface(null, Typeface.BOLD);
                textArray.setTextColor(Color.BLACK);
                textArray.setPadding(0, 150, 0, 100);
                tableRow.addView(textArray);
                tableRow.addView(textCaloriesArray);
                tableRow.addView(textProductArray);

                tableRow.setOnClickListener(new View.OnClickListener() {
                    int colorGreen = getResources().getColor(android.R.color.holo_green_light);
                    int colorWhite = getResources().getColor(android.R.color.background_light);
                    @Override
                    public void onClick(View v) {
                        if(!isClicked){
                            v.setBackgroundColor(colorGreen);
                            isClicked = true;

                            calorieCounter.addCalories(product.getCalories());
                            changeStatusBar(calorieCounter.getCountcalories());
                        }
                        else{
                            v.setBackgroundColor(colorWhite);
                            isClicked = false;
                        }
                    }
                });

                tableLayout.addView(tableRow, new TableLayout.LayoutParams(200, 200));
            }
        }
    }

    public void changeStatusBar(int calories){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Totaal calorieën " + calories);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getTotalCalories(){
        String information = null;
        Intent intent = getIntent();
        information = intent.getStringExtra("totalCalories");
        if(information != null){
            System.out.println("INFORMATION RETRIEVED" + information);
            calorieCounter.addCalories(Integer.valueOf(information));
            changeStatusBar(Integer.valueOf(information));
        }
    }

    public void getTotalProducts(){
        Intent intent = getIntent();
        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");
        if(products != null){
            productController.setProducts(products);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.backbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent = new Intent(this.getApplicationContext(), CategoryScreen.class);
                intent.putExtra("totalCalories", String.valueOf(calorieCounter.getCountcalories()));
                intent.putExtra("totalProducts", products);
                startActivity(intent);
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
