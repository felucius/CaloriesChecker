package maximedelange.calorieschecker.Screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;

public class BreakfastScreen extends AppCompatActivity {

    // Fields
    private ProductController productController;
    // GUI Components
    private TextView lblProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();
        getBreakfastProducts();
    }

    public void getBreakfastProducts(){
        lblProducts = (TextView)findViewById(R.id.lblProductsShow);
        for(Product product : productController.getProducts()){
            if(product.getCategoryType() == CategoryType.Breakfast){
                lblProducts.append(product.toString());
            }
        }
    }
}
