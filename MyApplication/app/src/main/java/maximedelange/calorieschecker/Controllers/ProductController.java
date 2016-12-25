package maximedelange.calorieschecker.Controllers;

import java.util.ArrayList;

import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;

/**
 * Created by M on 12/14/2016.
 */

public class ProductController {
    // Fields
    private ArrayList<Product> products;
    private int amount = 0;

    // Constructor
    public ProductController(){
        createProductData();
    }

    // Methods
    public void addProduct(Product product){
        this.products.add(product);
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }

    public void setProducts(ArrayList<Product> products){
        this.products = products;
    }

    public ArrayList<Product> createProductData(){
        this.products = new ArrayList<>();

        products.add(new Product("Bun", 120, ProductType.Bread, CategoryType.Breakfast, R.mipmap.bun));
        products.add(new Product("Slice bread", 100, ProductType.Bread, CategoryType.Breakfast, R.mipmap.bread));
        products.add(new Product("Beer", 150, ProductType.Alcohol, CategoryType.Dinner, R.mipmap.beerdark));
        products.add(new Product("Milk", 50, ProductType.Dairy, CategoryType.Lunch, R.mipmap.milk));
        products.add(new Product("Yogurt", 70, ProductType.Dairy, CategoryType.Dinner, R.mipmap.yogurt));
        products.add(new Product("Dessert", 120, ProductType.Dairy, CategoryType.Dinner, R.mipmap.dessert));
        products.add(new Product("P.butter", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.peanutbutter));
        products.add(new Product("C.paste", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.chocolatespread));
        products.add(new Product("Cheese", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.cheese));
        products.add(new Product("Ham", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.ham));
        products.add(new Product("C.breast", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.chicken));
        products.add(new Product("Potato", 400, ProductType.Potatoes, CategoryType.Dinner, R.mipmap.potato));
        products.add(new Product("Hamburger", 100, ProductType.Meat, CategoryType.Dinner, R.mipmap.hamburger));
        products.add(new Product("Spaghetti", 100, ProductType.Pasta, CategoryType.Dinner, R.mipmap.spagetti));
        products.add(new Product("Zucchini", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.cucumber));
        products.add(new Product("Vegetable", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.vegetable));
        products.add(new Product("Tomato", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.tomato));
        products.add(new Product("Lettuce", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.lettuce));
        products.add(new Product("Apple", 100, ProductType.Fruit, CategoryType.Lunch, R.mipmap.apple));
        products.add(new Product("Banana", 100, ProductType.Fruit, CategoryType.Lunch, R.mipmap.banana));
        products.add(new Product("Orange", 100, ProductType.Fruit, CategoryType.Lunch, R.mipmap.orange));
        products.add(new Product("Chicken", 100, ProductType.Chicken, CategoryType.Dinner, R.mipmap.chicken));

        return products;
    }
}
