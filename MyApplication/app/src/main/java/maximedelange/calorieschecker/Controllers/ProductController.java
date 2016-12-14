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

    public ArrayList<Product> createProductData(){
        this.products = new ArrayList<>();

        products.add(new Product("Bruin bolletje", 100, ProductType.Bread, CategoryType.Breakfast, R.mipmap.bun));
        products.add(new Product("Wit bolletje", 120, ProductType.Bread, CategoryType.Breakfast, R.mipmap.bun));
        products.add(new Product("Bruin snee brood", 100, ProductType.Bread, CategoryType.Breakfast, R.mipmap.bread));
        products.add(new Product("Wit snee brood", 100, ProductType.Bread, CategoryType.Breakfast, R.mipmap.bread));
        products.add(new Product("Bier donker", 150, ProductType.Alcohol, CategoryType.Dinner, R.mipmap.beerdark));
        products.add(new Product("Bier zoet", 120, ProductType.Alcohol, CategoryType.Dinner, R.mipmap.beerlight));
        products.add(new Product("Glas melk", 50, ProductType.Dairy, CategoryType.Lunch, R.mipmap.milk));
        products.add(new Product("Kom yoghurt", 70, ProductType.Dairy, CategoryType.Dinner, R.mipmap.yogurt));
        products.add(new Product("Toetje", 120, ProductType.Dairy, CategoryType.Dinner, R.mipmap.dessert));
        products.add(new Product("Pindakaas", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.peanutbutter));
        products.add(new Product("Chocolade pasta", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.chocolatespread));
        products.add(new Product("Kaas", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.cheese));
        products.add(new Product("Ham", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.ham));
        products.add(new Product("Kipfilet", 100, ProductType.SandwichFilling, CategoryType.Breakfast, R.mipmap.chicken));
        products.add(new Product("Aardappel", 400, ProductType.Potatoes, CategoryType.Dinner, R.mipmap.potato));
        products.add(new Product("Hamburger", 100, ProductType.Meat, CategoryType.Dinner, R.mipmap.hamburger));
        products.add(new Product("Spagetti", 100, ProductType.Pasta, CategoryType.Dinner, R.mipmap.spagetti));
        products.add(new Product("Courgette", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.cucumber));
        products.add(new Product("Diepvries groente", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.vegetable));
        products.add(new Product("Tomaten", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.tomato));
        products.add(new Product("Sla", 100, ProductType.Vegetables, CategoryType.Dinner, R.mipmap.lettuce));
        products.add(new Product("Appel", 100, ProductType.Fruit, CategoryType.Lunch, R.mipmap.apple));
        products.add(new Product("Banaan", 100, ProductType.Fruit, CategoryType.Lunch, R.mipmap.banana));
        products.add(new Product("Mandarijn", 100, ProductType.Fruit, CategoryType.Lunch, R.mipmap.orange));
        products.add(new Product("Kipfilet blok/heel", 100, ProductType.Chicken, CategoryType.Dinner, R.mipmap.chicken));

        return products;
    }
}
