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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class LunchScreen extends AppCompatActivity {

    // Fields
    private ProductController productController = null;
    private TableLayout tableLayout = null;
    private TableRow tableRow = null;
    private Bitmap bitmap = null;
    private Bitmap resizedbitmap = null;
    private CalorieCounter calorieCounter = null;
    private boolean isClicked = false;

    // GUI Components
    private TextView textArray;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();
        calorieCounter = new CalorieCounter();
        getTableLayoutLunchProducts();
        changeStatusBar(0);
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
                textArray.setTextSize(22);
                textArray.setTypeface(null, Typeface.BOLD);
                textArray.setTextColor(Color.BLACK);
                textArray.setPadding(0, 150, 0, 100);
                tableRow.addView(textArray);

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
