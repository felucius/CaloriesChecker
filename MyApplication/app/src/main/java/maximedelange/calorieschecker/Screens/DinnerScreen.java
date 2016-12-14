package maximedelange.calorieschecker.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class DinnerScreen extends AppCompatActivity {

    // Fields
    private ProductController productController;
    // GUI Components
    private TextView lblProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();
        getDinnerProducts();
    }

    public void getDinnerProducts(){
        lblProducts = (TextView)findViewById(R.id.lblProductsShow);
        for(Product product : productController.getProducts()){
            if(product.getCategoryType() == CategoryType.Dinner){
                lblProducts.append(product.toString());
            }
        }
    }
}
