package maximedelange.calorieschecker.Screens;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.Adapter;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.Category;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;

public class ProductScreen extends AppCompatActivity implements Serializable{

    // Field
    private ProductController productController = null;
    private CalorieCounter calorieCounter = null;
    private String productInformation = null;
    private ArrayList<Product> products;
    private int imageHolder = 0;
    private CategoryType categoryHolder = null;
    private ProductType productHolder = null;
    private int newProdutImage = 0;
    int tempValue;
    int productTempValue; // klopt niet
    private String product;
    private int hoi;

    // GUI Components
    private Button btnImages;
    private Button btnAddNewProduct;
    private Spinner dropDownCategories;
    private Spinner dropDownProducts;
    private Spinner dropDownImages;
    private EditText productName;
    private EditText productCalories;
    private String[] foods = {"apple", "baguette", "banana", "beerdark", "beerlight", "bread", "bun",
            "cheese", "chicken", "chocolatespread", "croissant", "cucumber", "dessert",
            "ham", "hamburger", "lettuce", "milk", "orange", "pancakes", "peanutbutter", "pizza", "potato",
            "snacks", "spagetti", "steak", "tomato", "vegetable", "wheat", "yogurt"};
    private int images[] = {R.mipmap.apple, R.mipmap.baguette, R.mipmap.banana, R.mipmap.beerdark,
            R.mipmap.beerlight, R.mipmap.bread, R.mipmap.bun, R.mipmap.cheese, R.mipmap.chicken,
            R.mipmap.chocolatespread, R.mipmap.croissant, R.mipmap.cucumber, R.mipmap.dessert,
            R.mipmap.ham, R.mipmap.hamburger, R.mipmap.lettuce, R.mipmap.milk, R.mipmap.orange,
            R.mipmap.pancakes, R.mipmap.peanutbutter, R.mipmap.pizza, R.mipmap.potato, R.mipmap.snacks,
            R.mipmap.spagetti, R.mipmap.steak, R.mipmap.tomato, R.mipmap.vegetable, R.mipmap.wheat, R.mipmap.yogurt};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productController = new ProductController();
        calorieCounter = new CalorieCounter();
        changeStatusBar(productController.getProducts().size());
        getTotalCalories();
        getTotalProducts();
        initializeDropDownCategoryMenu();
        initializeDropDownCategoryProductMenu();
        initializeDropDownImages();
        addNewProduct();

        getSelectedItem();
        getSelectedCategory();
        getSelectedProductType();

    }

    public void changeStatusBar(int amountOfProducts){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Amount of products: " + amountOfProducts);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getTotalCalories(){
        String information = null;
        Intent intent = getIntent();
        information = intent.getStringExtra("totalCalories");
        if(information != null){
            calorieCounter.addCalories(Integer.valueOf(information));
            changeStatusBar(Integer.valueOf(information));
        }
    }

    public void getTotalProducts(){
        Intent intent = getIntent();
        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");
        if(products != null){
            changeStatusBar(Integer.valueOf(products.size()));
        }else{
            changeStatusBar(productController.getProducts().size());
        }
    }

    public void addNewProduct(){
        productName = (EditText)findViewById(R.id.txtName);
        productCalories = (EditText)findViewById(R.id.txtCalories);
        btnAddNewProduct = (Button)findViewById(R.id.btnAddProduct);
        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");

                int holder = newImage();
                CategoryType catHolder = newCategory();
                ProductType prodHolder = newProduct();
                if(products != null){
                    products.add(new Product(productName.getText().toString(), Integer.parseInt(productCalories.getText().toString()),
                            prodHolder,
                            catHolder, holder));

                    productController.setProducts(products);
                    changeStatusBar(products.size());
                }else{
                    productController.addProduct(new Product(productName.getText().toString(), Integer.parseInt(productCalories.getText().toString()),
                            prodHolder,
                            catHolder, holder));

                    products = productController.getProducts();
                    productController.setProducts(products);
                    changeStatusBar(productController.getProducts().size());
                }
            }
        });
    }

    public int initializeDropDownCategoryMenu(){
        dropDownCategories = (Spinner)findViewById(R.id.dropDownCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategories.setAdapter(adapter);
        return R.array.Category_array;
    }

    public int getSelectedProductType(){
        dropDownProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productTempValue = dropDownProducts.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return productTempValue;
    }

    public int getSelectedCategory(){

        dropDownCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempValue = dropDownCategories.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return tempValue;
    }

    public void initializeDropDownCategoryProductMenu(){
        dropDownProducts = (Spinner)findViewById(R.id.dropDownProduct);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Product_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownProducts.setAdapter(adapter);
    }

    public void initializeDropDownImages(){
        dropDownImages = (Spinner)findViewById(R.id.dropDownImage);
        Adapter adapter = new Adapter(this, foods, images);
        dropDownImages.setAdapter(adapter);
    }

    public int getSelectedItem(){
        dropDownImages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageHolder = dropDownImages.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return imageHolder;
    }

    public ProductType newProduct(){
        switch(productTempValue){
            case 0:
                productHolder = ProductType.Alcohol;
                break;
            case 1:
                productHolder = ProductType.Bread;
                break;
            case 2:
                productHolder = ProductType.Chicken;
                break;
            case 3:
                productHolder = ProductType.Dairy;
                break;
            case 4:
                productHolder = ProductType.Fruit;
                break;
            case 5:
                productHolder = ProductType.Meat;
                break;
            case 6:
                productHolder = ProductType.Pasta;
                break;
            case 7:
                productHolder = ProductType.Potatoes;
                break;
            case 8:
                productHolder = ProductType.SandwichFilling;
                break;
            case 9:
                productHolder = ProductType.Snacks;
                break;
            case 10:
                productHolder = ProductType.Vegetables;
                break;
        }

        return productHolder;
    }

    public CategoryType newCategory(){
        switch(tempValue){
            case 0:
                categoryHolder = CategoryType.Breakfast;
                break;
            case 1:
                categoryHolder = CategoryType.Lunch;
                break;
            case 2:
                categoryHolder = CategoryType.Dinner;
                break;
        }

        return categoryHolder;
    }

    public int newImage(){
        switch(imageHolder){
            case 0:
                newProdutImage = R.mipmap.apple;
                break;
            case 1:
                newProdutImage = R.mipmap.baguette;
                break;
            case 2:
                newProdutImage = R.mipmap.banana;
                break;
            case 3:
                newProdutImage = R.mipmap.beerdark;
                break;
            case 4:
                newProdutImage = R.mipmap.beerlight;
                break;
            case 5:
                newProdutImage = R.mipmap.bread;
                break;
            case 6:
                newProdutImage = R.mipmap.bun;
                break;
            case 7:
                newProdutImage = R.mipmap.cheese;
                break;
            case 8:
                newProdutImage = R.mipmap.chicken;
                break;
            case 9:
                newProdutImage = R.mipmap.chocolatespread;
                break;
            case 10:
                newProdutImage = R.mipmap.croissant;
                break;
            case 11:
                newProdutImage = R.mipmap.cucumber;
                break;
            case 12:
                newProdutImage = R.mipmap.dessert;
                break;
            case 13:
                newProdutImage = R.mipmap.ham;
                break;
            case 14:
                newProdutImage = R.mipmap.hamburger;
                break;
            case 15:
                newProdutImage = R.mipmap.lettuce;
                break;
            case 16:
                newProdutImage = R.mipmap.milk;
                break;
            case 17:
                newProdutImage = R.mipmap.orange;
                break;
            case 18:
                newProdutImage = R.mipmap.pancakes;
                break;
            case 19:
                newProdutImage = R.mipmap.peanutbutter;
                break;
            case 20:
                newProdutImage = R.mipmap.pizza;
                break;
            case 21:
                newProdutImage = R.mipmap.potato;
                break;
            case 22:
                newProdutImage = R.mipmap.snacks;
                break;
            case 23:
                newProdutImage = R.mipmap.spagetti;
                break;
            case 24:
                newProdutImage = R.mipmap.steak;
                break;
            case 25:
                newProdutImage = R.mipmap.tomato;
                break;
            case 26:
                newProdutImage = R.mipmap.vegetable;
                break;
            case 27:
                newProdutImage = R.mipmap.wheat;
                break;
            case 28:
                newProdutImage = R.mipmap.yogurt;
                break;
        }
        return newProdutImage;
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
                intent.putExtra("totalProducts", products);
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