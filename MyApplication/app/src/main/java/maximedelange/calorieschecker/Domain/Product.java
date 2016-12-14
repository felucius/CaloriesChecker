package maximedelange.calorieschecker.Domain;

/**
 * Created by M on 12/14/2016.
 */

public class Product {
    // Fields
    private String name;
    private int calories;
    private ProductType productType;
    private CategoryType categoryType;

    // Constructor
    public Product(String name, int calories, ProductType productType, CategoryType categoryType){
        this.name = name;
        this.calories = calories;
        this.productType = productType;
        this.categoryType = categoryType;
    }

    // Methods
    public String getName(){
        return this.name;
    }

    public int getCalories(){
        return this.calories;
    }

    public ProductType getProductType(){
        return this.productType;
    }

    public CategoryType getCategoryType(){
        return this.categoryType;
    }

    @Override
    public String toString(){
        return "Name: " + name + " Calories: " + calories;
    }
}
