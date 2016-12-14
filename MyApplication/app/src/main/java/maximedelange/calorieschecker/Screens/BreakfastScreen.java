package maximedelange.calorieschecker.Screens;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;

public class BreakfastScreen extends AppCompatActivity {

    // Fields
    private ProductController productController;
    private TableLayout tableLayout;
    private TableRow tableRow;
    // GUI Components
    private TextView textArray;
    private ImageView image;
    private Bitmap bitmap;
    private Bitmap resizedbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();

        getTableLayoutBreakfastProducts();
    }

    public void getTableLayoutBreakfastProducts() {
        tableLayout = (TableLayout)findViewById(R.id.tableLayoutBreakfast);

        // Creating a table row for each product in productController.
        for (Product product : productController.getProducts()) {
            if(product.getCategoryType() == CategoryType.Breakfast){

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

                tableLayout.addView(tableRow, new TableLayout.LayoutParams(200, 200));
            }
        }
    }
}
