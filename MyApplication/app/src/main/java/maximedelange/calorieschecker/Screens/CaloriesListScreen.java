package maximedelange.calorieschecker.Screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Database.Database;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class CaloriesListScreen extends AppCompatActivity {

    private ProductController productController = null;
    private ArrayList<Product> products = null;
    private ArrayList<Product> combinedProducts = null;
    private ArrayList<Product> databaseProducts = null;
    private TableLayout tableLayout = null;
    private GridLayout gridLayout = null;
    private TableRow tableRow = null;
    private Bitmap bitmap = null;
    private Bitmap resizedbitmap = null;
    private CalorieCounter calorieCounter = null;
    private Context context = null;
    private Toast toast = null;
    private String information = null;
    private Database database = null;

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
        setContentView(R.layout.activity_calories_list_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        combinedProducts = new ArrayList<>();
        databaseProducts = new ArrayList<>();
        database = new Database(this, null, null, 1);
        Intent intent = getIntent();
        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");
        if(products != null){
            calorieCounter = new CalorieCounter();
            changeStatusBar(0);
            getTotalCalories();
            getTotalProducts();
        }else{
            productController = new ProductController();
            calorieCounter = new CalorieCounter();
            changeStatusBar(0);
            getTotalCalories();
            getTotalProducts();
        }
    }

    public void changeStatusBar(int calories){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Total calories this week: " + calories);
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

            databaseProducts = database.readProductFromDatabase();
            combinedProducts.addAll(products);
            productController.setStaticProducts(combinedProducts);

        }else{
            databaseProducts = database.readProductFromDatabase();
            combinedProducts.addAll(databaseProducts);
            productController.setStaticProducts(combinedProducts);
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
