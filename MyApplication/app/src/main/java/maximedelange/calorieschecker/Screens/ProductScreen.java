package maximedelange.calorieschecker.Screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.util.ArrayList;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Database.Database;
import maximedelange.calorieschecker.Controllers.Adapter;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;

public class ProductScreen extends AppCompatActivity implements Serializable{

    // Field
    ArrayList<Product> databaseProducts = new ArrayList<>();
    private ProductController productController = null;
    private CalorieCounter calorieCounter = null;
    private ArrayList<Product> products = null;
    private int imageHolder = 0;
    private CategoryType categoryHolder = null;
    private ProductType productHolder = null;
    private int newProdutImage = 0;
    int tempValue = 0;
    int productTempValue = 0;
    private Context context = null;
    private Toast toast = null;
    private Database database = null;
    private int productCounter = 0;

    // GUI Components
    private Button btnAddNewProduct;
    private Spinner dropDownCategories;
    private Spinner dropDownProducts;
    private Spinner dropDownImages;
    private EditText productName;
    private EditText productCalories;
    private String[] foods = {"choose an image", "apple", "baguette", "banana", "beerdark", "beerlight", "bread", "bun",
            "cheese", "chicken", "chocolatespread", "croissant", "cucumber", "dessert",
            "ham", "hamburger", "lettuce", "milk", "orange", "pancakes", "peanutbutter", "pizza", "fries",
            "snacks", "spagetti", "steak", "tomato", "broccoli", "wheat", "yogurt", "hotdog", "avocado", "corn",
            "eggplant", "fish", "grape", "juice", "pear", "popcorn", "rice", "salad", "wine", "tuna", "potato",
            "egg", "cabbage", "carrot", "nuts", "melon", "bellpepper", "beans", "sausage", "pineapple"};
    private int images[] = {R.mipmap.stockimage, R.mipmap.apples, R.mipmap.baguette, R.mipmap.banana, R.mipmap.beerdark,
            R.mipmap.beerlight, R.mipmap.bread, R.mipmap.bun, R.mipmap.cheese, R.mipmap.chicken,
            R.mipmap.chocolatespread, R.mipmap.croissant, R.mipmap.cucumber, R.mipmap.dessert,
            R.mipmap.ham, R.mipmap.hamburger, R.mipmap.lettuce, R.mipmap.milk, R.mipmap.orange,
            R.mipmap.pancakes, R.mipmap.peanutbutter, R.mipmap.pizza, R.mipmap.potato, R.mipmap.snacks,
            R.mipmap.spagetti, R.mipmap.steak, R.mipmap.tomato, R.mipmap.vegetable, R.mipmap.wheat, R.mipmap.yogurt,
            R.mipmap.hotdog, R.mipmap.avocado, R.mipmap.corn, R.mipmap.eggplant, R.mipmap.fish,
            R.mipmap.grape, R.mipmap.juice, R.mipmap.pear, R.mipmap.popcorn, R.mipmap.rice, R.mipmap.salad, R.mipmap.wine,
            R.mipmap.tuna, R.mipmap.potato, R.mipmap.egg, R.mipmap.cabbage, R.mipmap.carrot,
            R.mipmap.nuts, R.mipmap.melon, R.mipmap.bellpepper, R.mipmap.beans, R.mipmap.sausage, R.mipmap.pineapple};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Loading adds in this screen
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9602567565027598~8467791866");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        database = new Database(this, null, null, 1);
        productController = new ProductController();
        calorieCounter = new CalorieCounter();
        //changeStatusBar(productController.getStaticProducts().size());
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
            products = database.readProductFromDatabase();
            changeStatusBar(products.size());
        }
    }

    public void addNewProduct(){
        productName = (EditText)findViewById(R.id.txtName);
        productName.setHint("enter product name");
        productCalories = (EditText)findViewById(R.id.txtCalories);
        productCalories.setHint("enter calories");
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
                    if(!productName.getText().toString().equals("") && !productCalories.getText().toString().equals("")
                            && prodHolder != null && catHolder != null && holder != 0){

                        // SQLITE DATABASE
                        database.addProductToDatabase(new Product(productCounter, productName.getText().toString(), Integer.parseInt(productCalories.getText().toString()),
                                prodHolder,
                                catHolder, holder));
                        products = database.readProductFromDatabase();
                        productCounter++;
                        changeStatusBar(products.size());

                        showToastMessage("Added new product");
                    }else{
                        if(productName.getText().toString().equals("") || productCalories.getText().toString().equals("")){
                            showToastMessage("Cannot add an empty 'name' or amount of 'calories'");
                        }
                    }
                }else{
                    if(!productName.getText().toString().equals("") && !productCalories.getText().toString().equals("")
                            && prodHolder != null && catHolder != null && holder != 0){

                        // SQLITE DATABASE
                        database.addProductToDatabase(new Product(productCounter, productName.getText().toString(), Integer.parseInt(productCalories.getText().toString()),
                                prodHolder,
                                catHolder, holder));
                        productCounter++;
                        products = database.readProductFromDatabase();
                        changeStatusBar(products.size());

                        showToastMessage("Added new product");
                    }else{
                        if(productName.getText().toString().equals("") || productCalories.getText().toString().equals("")){
                            showToastMessage("Cannot add an empty 'name' or amount of 'calories'");
                        }
                    }
                }
            }
        });
    }

    public int initializeDropDownCategoryMenu(){
        dropDownCategories = (Spinner)findViewById(R.id.dropDownCategory);
        dropDownCategories.setPrompt("choose a category");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category_array,
                R.layout.custom_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategories.setAdapter(adapter);
        return R.array.Category_array;
    }

    public int getSelectedProductType(){
        dropDownProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productTempValue = dropDownProducts.getSelectedItemPosition();

                TextView text = (TextView)parent.getChildAt(0);
                if(text.getText().toString().equals("choose a product")){
                    text.setTextColor(Color.parseColor("#808080"));
                }
                else{
                    text.setTextColor(Color.parseColor("#051dba"));
                }
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

                TextView text = (TextView)parent.getChildAt(0);
                if(text.getText().toString().equals("choose a category")){
                    text.setTextColor(Color.parseColor("#808080"));
                }
                else{
                    text.setTextColor(Color.parseColor("#051dba"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return tempValue;
    }

    public void initializeDropDownCategoryProductMenu(){

        dropDownProducts = (Spinner)findViewById(R.id.dropDownProduct);
        dropDownProducts.setPrompt("choose a product");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Product_array,
                R.layout.custom_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownProducts.setAdapter(adapter);
    }

    public void initializeDropDownImages(){
        dropDownImages = (Spinner)findViewById(R.id.dropDownImage);
        dropDownImages.setPrompt("choose an image");
        Adapter adapter = new Adapter(this, foods, images);
        dropDownImages.setAdapter(adapter);
    }

    public void showToastMessage(String message){
        context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        toast = Toast.makeText(context, text, duration);

        toast.setGravity(Gravity.BOTTOM, 0, 0);
        ViewGroup group = (ViewGroup) toast.getView();
        group.setBackgroundResource(R.drawable.toastmessage);
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextColor(Color.RED);
        messageTextView.setTypeface(null, Typeface.BOLD);
        messageTextView.setTextSize(16);

        toast.show();
    }

    public int getSelectedItem(){
        dropDownImages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageHolder = dropDownImages.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView text = (TextView)parent.getChildAt(0);
                if(text.getText().toString().equals("choose a product")){
                    text.setTextColor(Color.parseColor("#808080"));
                }
                else{
                    text.setTextColor(Color.parseColor("#051dba"));
                }
            }
        });

        return imageHolder;
    }

    public ProductType newProduct(){
        switch(productTempValue){
            case 0:
                showToastMessage("Cannot add an empty product type");
                productHolder = null;
                break;
            case 1:
                productHolder = ProductType.Alcohol;
                break;
            case 2:
                productHolder = ProductType.Vegetables;
                break;
            case 3:
                productHolder = ProductType.Dairy;
                break;
            case 4:
                productHolder = ProductType.Grains;
                break;
            case 5:
                productHolder = ProductType.Fruits;
                break;
            case 6:
                productHolder = ProductType.Proteins;
                break;
        }

        return productHolder;
    }

    public CategoryType newCategory(){
        switch(tempValue){
            case 0:
                showToastMessage("Cannot add an empty category");
                categoryHolder = null;
                break;
            case 1:
                categoryHolder = CategoryType.Breakfast;
                break;
            case 2:
                categoryHolder = CategoryType.Lunch;
                break;
            case 3:
                categoryHolder = CategoryType.Dinner;
                break;
        }

        return categoryHolder;
    }

    public int newImage(){
        switch(imageHolder){
            case 0:
                showToastMessage("Cannot add an stock image");
                newProdutImage = 0;
                break;
            case 1:
                newProdutImage = R.mipmap.apples;
                break;
            case 2:
                newProdutImage = R.mipmap.baguette;
                break;
            case 3:
                newProdutImage = R.mipmap.banana;
                break;
            case 4:
                newProdutImage = R.mipmap.beerdark;
                break;
            case 5:
                newProdutImage = R.mipmap.beerlight;
                break;
            case 6:
                newProdutImage = R.mipmap.bread;
                break;
            case 7:
                newProdutImage = R.mipmap.bun;
                break;
            case 8:
                newProdutImage = R.mipmap.cheese;
                break;
            case 9:
                newProdutImage = R.mipmap.chicken;
                break;
            case 10:
                newProdutImage = R.mipmap.chocolatespread;
                break;
            case 11:
                newProdutImage = R.mipmap.croissant;
                break;
            case 12:
                newProdutImage = R.mipmap.cucumber;
                break;
            case 13:
                newProdutImage = R.mipmap.dessert;
                break;
            case 14:
                newProdutImage = R.mipmap.ham;
                break;
            case 15:
                newProdutImage = R.mipmap.hamburger;
                break;
            case 16:
                newProdutImage = R.mipmap.lettuce;
                break;
            case 17:
                newProdutImage = R.mipmap.milk;
                break;
            case 18:
                newProdutImage = R.mipmap.orange;
                break;
            case 19:
                newProdutImage = R.mipmap.pancakes;
                break;
            case 20:
                newProdutImage = R.mipmap.peanutbutter;
                break;
            case 21:
                newProdutImage = R.mipmap.pizza;
                break;
            case 22:
                newProdutImage = R.mipmap.potato;
                break;
            case 23:
                newProdutImage = R.mipmap.snacks;
                break;
            case 24:
                newProdutImage = R.mipmap.spagetti;
                break;
            case 25:
                newProdutImage = R.mipmap.steak;
                break;
            case 26:
                newProdutImage = R.mipmap.tomato;
                break;
            case 27:
                newProdutImage = R.mipmap.vegetable;
                break;
            case 28:
                newProdutImage = R.mipmap.wheat;
                break;
            case 29:
                newProdutImage = R.mipmap.yogurt;
                break;
            case 30:
                newProdutImage = R.mipmap.hotdog;
                break;
            case 31:
                newProdutImage = R.mipmap.avocado;
                break;
            case 32:
                newProdutImage = R.mipmap.corn;
                break;
            case 33:
                newProdutImage = R.mipmap.eggplant;
                break;
            case 34:
                newProdutImage = R.mipmap.fish;
                break;
            case 35:
                newProdutImage = R.mipmap.grape;
                break;
            case 36:
                newProdutImage = R.mipmap.juice;
                break;
            case 37:
                newProdutImage = R.mipmap.pear;
                break;
            case 38:
                newProdutImage = R.mipmap.popcorn;
                break;
            case 39:
                newProdutImage = R.mipmap.rice;
                break;
            case 40:
                newProdutImage = R.mipmap.salad;
                break;
            case 41:
                newProdutImage = R.mipmap.wine;
                break;
            case 42:
                newProdutImage = R.mipmap.tuna;
                break;
            case 43:
                newProdutImage = R.mipmap.potato;
                break;
            case 44:
                newProdutImage = R.mipmap.egg;
                break;
            case 45:
                newProdutImage = R.mipmap.cabbage;
                break;
            case 46:
                newProdutImage = R.mipmap.carrot;
                break;
            case 47:
                newProdutImage = R.mipmap.nuts;
                break;
            case 48:
                newProdutImage = R.mipmap.melon;
                break;
            case 49:
                newProdutImage = R.mipmap.bellpepper;
                break;
            case 50:
                newProdutImage = R.mipmap.beans;
                break;
            case 51:
                newProdutImage = R.mipmap.sausage;
                break;
            case 52:
                newProdutImage = R.mipmap.pineapple;
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

