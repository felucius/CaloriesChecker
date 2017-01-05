package maximedelange.calorieschecker.Controllers;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import maximedelange.calorieschecker.Database.Database;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;

/**
 * Created by M on 12/14/2016.
 */

public class ProductController {
    // Fields
    private ArrayList<Product> products = null;
    private static ArrayList<Product> staticProducts = null;
    private int amount = 0;

    // Constructor
    public ProductController() {}

    // Methods
    public void addProduct(Product product){
        this.products.add(product);
    }

    public static void setStaticProducts(ArrayList<Product> products){
        staticProducts = products;
    }

    public static ArrayList<Product> getStaticProducts(){
        return staticProducts;
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }

    public void setProducts(ArrayList<Product> products){
        this.products = products;
    }

}
