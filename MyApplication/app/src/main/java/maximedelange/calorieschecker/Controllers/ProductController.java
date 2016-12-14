package maximedelange.calorieschecker.Controllers;

import java.util.ArrayList;

import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;

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
        products.add(new Product("Bruin bolletje", 100, ProductType.Bread, CategoryType.Breakfast));
        products.add(new Product("Wit bolletje", 120, ProductType.Bread, CategoryType.Breakfast));
        products.add(new Product("Bruin snee brood", 100, ProductType.Bread, CategoryType.Breakfast));
        products.add(new Product("Wit snee brood", 100, ProductType.Bread, CategoryType.Breakfast));
        products.add(new Product("Bier donker", 150, ProductType.Alcohol, CategoryType.Dinner));
        products.add(new Product("Bier zoet", 120, ProductType.Alcohol, CategoryType.Dinner));
        products.add(new Product("Glas melk", 50, ProductType.Dairy, CategoryType.Lunch));
        products.add(new Product("Kom yoghurt", 70, ProductType.Dairy, CategoryType.Dinner));
        products.add(new Product("Toetje", 120, ProductType.Dairy, CategoryType.Dinner));
        products.add(new Product("Pindakaas", 100, ProductType.SandwichFilling, CategoryType.Breakfast));
        products.add(new Product("Chocolade pasta", 100, ProductType.SandwichFilling, CategoryType.Breakfast));
        products.add(new Product("Kaas", 100, ProductType.SandwichFilling, CategoryType.Breakfast));
        products.add(new Product("Ham", 100, ProductType.SandwichFilling, CategoryType.Breakfast));
        products.add(new Product("Kipfilet", 100, ProductType.SandwichFilling, CategoryType.Breakfast));
        products.add(new Product("Aardappel", 400, ProductType.Potatoes, CategoryType.Dinner));
        products.add(new Product("Hamburger vlees", 100, ProductType.Meat, CategoryType.Dinner));
        products.add(new Product("Spagetti", 100, ProductType.Pasta, CategoryType.Dinner));
        products.add(new Product("Courgette", 100, ProductType.Vegetables, CategoryType.Dinner));
        products.add(new Product("Diepvries groente", 100, ProductType.Vegetables, CategoryType.Dinner));
        products.add(new Product("Tomaten", 100, ProductType.Vegetables, CategoryType.Dinner));
        products.add(new Product("Sla", 100, ProductType.Vegetables, CategoryType.Dinner));

        return products;
    }
}
