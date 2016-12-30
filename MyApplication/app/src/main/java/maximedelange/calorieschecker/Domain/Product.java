package maximedelange.calorieschecker.Domain;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by M on 12/14/2016.
 */

public class Product implements Serializable{
    // Fields
    private int count = 0;
    private int ID = 0;
    private String name;
    private int calories;
    private ProductType productType;
    private CategoryType categoryType;
    private int image;

    // Constructor
    public Product(int ID, String name, int calories, ProductType productType, CategoryType categoryType, int image){
        this.ID = ID;
        this.name = name;
        this.calories = calories;
        this.productType = productType;
        this.categoryType = categoryType;
        this.image = image;
    }

    public Product(){}

    // Methods
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setCalories(int calories){
        this.calories = calories;
    }
    public int getCalories(){
        return this.calories;
    }

    public void setProductType(ProductType productType){
        this.productType = productType;
    }
    public ProductType getProductType(){
        return this.productType;
    }

    public void setCategoryType(CategoryType categoryType){
        this.categoryType = categoryType;
    }

    public CategoryType getCategoryType(){
        return this.categoryType;
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setImage(int image){
        this.image = image;
    }
    public int getImage(){
        return this.image;
    }

    @Override
    public String toString(){
        return "Name: " + name + " Calories: " + calories + " Product: " + productType;
    }
}
