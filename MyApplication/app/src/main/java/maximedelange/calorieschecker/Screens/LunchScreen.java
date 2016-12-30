package maximedelange.calorieschecker.Screens;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Product> products = null;
    private Context context = null;
    private Toast toast = null;
    private String information = null;

    // GUI Components
    private TextView textArray;
    private TextView textCaloriesArray;
    private TextView textProductArray;
    private Button dismisspopup;
    private Button removeproduct;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");
        if(products != null){
            calorieCounter = new CalorieCounter();
            changeStatusBar(0);
            getTotalCalories();
            getTotalProducts();
            getTableLayoutLunchProducts();
        }else{
            productController = new ProductController();
            calorieCounter = new CalorieCounter();
            changeStatusBar(0);
            getTotalCalories();
            getTotalProducts();
            getTableLayoutLunchProducts();
        }
    }

    public void getTableLayoutLunchProducts() {
        tableLayout = (TableLayout)findViewById(R.id.tableLayoutLunch);

        // Creating a table row for each product in productController.
        for (final Product product : productController.getStaticProducts()) {
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

                bitmap = BitmapFactory.decodeResource(getResources(), product.getImage());
                int width=400;
                int height=400;
                resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                image.setImageBitmap(resizedbitmap);
                image.setBackgroundDrawable(getResources().getDrawable(R.drawable.productimages));
                tableRow.addView(image);

                textArray.setText(product.getName());
                textCaloriesArray.setText(" Cal: " + String.valueOf(product.getCalories()));
                textProductArray.setText(" Prod: " + String.valueOf(product.getProductType()));
                textArray.setTextSize(18);
                textArray.setTypeface(null, Typeface.BOLD);
                textArray.setTextColor(Color.BLACK);
                textArray.setPadding(0, 150, 0, 100);
                tableRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.productimages));
                tableRow.addView(textArray);
                tableRow.addView(textCaloriesArray);
                tableRow.addView(textProductArray);

                tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final View background = v;
                        background.setBackgroundColor(Color.RED);

                        final Dialog dialog = new Dialog(LunchScreen.this);
                        dialog.setContentView(R.layout.popup);
                        dialog.show();

                        dismisspopup = (Button)dialog.findViewById(R.id.dismissPopup);
                        dismisspopup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                background.setBackgroundDrawable(getResources().getDrawable(R.drawable.productimages));
                            }
                        });

                        removeproduct = (Button)dialog.findViewById(R.id.removeProduct);
                        removeproduct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), LunchScreen.class);
                                dialog.dismiss();
                                products = productController.getStaticProducts();
                                products.remove(product);
                                productController.setStaticProducts(products);
                                productController.getStaticProducts();

                                intent.putExtra("totalProducts", products);
                                intent.putExtra("totalCalories", String.valueOf(calorieCounter.getCountcalories()));
                                overridePendingTransition( 0, 0);
                                startActivity(intent);
                                overridePendingTransition( 0, 0);
                                showToastMessage("Product has been removed");
                            }
                        });

                        return false;
                    }
                });

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calorieCounter.addCalories(product.getCalories());
                        changeStatusBar(calorieCounter.getCountcalories());
                    }
                });

                tableLayout.addView(tableRow, new TableLayout.LayoutParams(200, 200));
            }
        }
    }

    public void changeStatusBar(int calories){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Total calories: " + calories);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getTotalCalories(){
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
            productController.setStaticProducts(products);
        }else{
            productController.setStaticProducts(productController.getProducts());
        }
    }

    public void showToastMessage(String message){
        context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        toast = Toast.makeText(context, text, duration);
        toast.show();
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
