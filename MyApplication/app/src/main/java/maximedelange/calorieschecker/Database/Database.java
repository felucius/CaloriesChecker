package maximedelange.calorieschecker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.DateConverter;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;

/**
 * Created by M on 12/29/2016.
 */

/*
SQLite database that creates tables and saves data to their local devices.
 */
public class Database extends SQLiteOpenHelper{

    // Fields
    private final static int DATABASE_VERSION = 3;
    // Table products
    private final static String DATABASE_NAME = "calorieapp.db";
    public static final String TABLE_PRODUCTS = "product";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CALORIES = "calories";
    public static final String PRODUCT_TYPE = "producttype";
    public static final String CATEGORY_TYPE = "categorytype";
    public static final String IMAGE = "image";

    // Table Calories
    public static final String TABLE_CALORIES = "calories";
    public static final String CALID = "id";
    public static final String AMOUNT_OF_CALORIES = "amountOfcalories";
    public static final String DAY_OF_THE_WEEK = "dayoftheweek";
    public static final String DAY_OF_THE_MONTH = "dayofthemonth";
    public static final String IS_INSERTED = "isinserted";

    // Table product fields
    private String prodID = null;
    private String prodname = null;
    private String prodcalories = null;
    private String prodproducttype = null;
    private String prodcategorytype = null;
    private String prodimage = null;
    private ProductType tempProductType = null;
    private ProductType newProductType = null;
    private CategoryType tempCatType = null;
    private CategoryType newCategoryType = null;
    private String day;

    // Table Calories fields
    private String calID = null;
    private String dayoftheweek = null;
    private String dayofthemonth = null;
    private String totalCalories = null;
    private String isinserted = null;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        System.out.println("DATABASE PATH: " + context.getDatabasePath("calorieapp.db"));
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(db.isOpen());

        String create_table_products = "create table " + TABLE_PRODUCTS + "(" +
                ID + " integer primary key autoincrement, " +
                NAME + " text, " +
                CALORIES + " text, " +
                PRODUCT_TYPE + " text, " +
                CATEGORY_TYPE + " text, " +
                IMAGE+ " text " +
                ");";
        db.execSQL(create_table_products);

        String create_table_calories = "create table " + TABLE_CALORIES + "(" +
                CALID + " integer primary key autoincrement, " +
                AMOUNT_OF_CALORIES + " text, " +
                DAY_OF_THE_WEEK + " text, " +
                DAY_OF_THE_MONTH + " datetime, " +
                IS_INSERTED + " text " +
                ");";
        db.execSQL(create_table_calories);
    }

    // If there is a new version, there is going to be an upgrade.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALORIES);
        onCreate(db);
    }

    // Insert new calories.
    public void insertCaloriesOfDayToDatabase(int calories, int day, String dayOfMonth, String inserted){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CALORIES + " WHERE " + DAY_OF_THE_MONTH + "= '" + dayOfMonth + "' AND " + IS_INSERTED + "= 'true' AND " + DAY_OF_THE_WEEK + "= " + day;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        isinserted = cursor.getString(cursor.getColumnIndex("isinserted"));

        if(isinserted.toString().equals("true")){
            ContentValues values = new ContentValues();
            values.put(AMOUNT_OF_CALORIES, calories);
            values.put(DAY_OF_THE_WEEK, day);
            values.put(DAY_OF_THE_MONTH, dayOfMonth);

            db.execSQL("UPDATE " + TABLE_CALORIES +
                    " SET " + AMOUNT_OF_CALORIES + " = " + calories + ", " +
                    DAY_OF_THE_WEEK + " = " + day + ", " +
                    DAY_OF_THE_MONTH + " = '" + dayOfMonth + "' WHERE " +
                    DAY_OF_THE_MONTH + " = '" + dayOfMonth + "'");
        }else{
            ContentValues values = new ContentValues();
            values.put(AMOUNT_OF_CALORIES, calories);
            values.put(DAY_OF_THE_WEEK, day);
            values.put(DAY_OF_THE_MONTH, dayOfMonth);
            values.put(IS_INSERTED, inserted);

            db.insert(TABLE_CALORIES, null, values);
        }

        db.close();
    }

    // Reading calories.
    public int readCaloriesFromDatabase(String dayOfMonth, int day){
        int newTotalCalories = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CALORIES + " WHERE " + DAY_OF_THE_MONTH + "= '" + dayOfMonth + "' and " + DAY_OF_THE_WEEK + " = " + day;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() <= 0){
            ContentValues values = new ContentValues();
            values.put(DAY_OF_THE_WEEK, day);
            values.put(AMOUNT_OF_CALORIES, 0);
            values.put(DAY_OF_THE_MONTH, dayOfMonth);
            values.put(IS_INSERTED, "true");

            db.replace(TABLE_CALORIES, null, values);
        }else{
            while(!cursor.isAfterLast()){
                totalCalories = cursor.getString(cursor.getColumnIndex("amountOfcalories"));
                dayoftheweek = cursor.getString(cursor.getColumnIndex("dayoftheweek"));
                dayofthemonth = cursor.getString(cursor.getColumnIndex("dayofthemonth"));
                isinserted = cursor.getString(cursor.getColumnIndex("isinserted"));

                cursor.moveToNext();
            }

            newTotalCalories = Integer.valueOf(totalCalories);
        }

        db.close();
        return newTotalCalories;
    }

    // Gets the week information for the overview page.
    public ArrayList<DateConverter> getWeekInformation(){
        ArrayList<DateConverter> dates = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_CALORIES + " where " +DAY_OF_THE_MONTH + " >= DATE('now', '-7 days')";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        DateConverter date = null;
        while(!cursor.isAfterLast()){
            totalCalories = cursor.getString(cursor.getColumnIndex("amountOfcalories"));
            dayofthemonth = cursor.getString(cursor.getColumnIndex("dayofthemonth"));
            dayoftheweek = cursor.getString(cursor.getColumnIndex("dayoftheweek"));

            date = new DateConverter(dayofthemonth, Integer.valueOf(dayoftheweek), Integer.valueOf(totalCalories));
            dates.add(date);
            cursor.moveToNext();
        }

        db.close();

        return dates;
    }

    // Adding a new product to the database.
    public void addProductToDatabase(Product product){
        ContentValues values = new ContentValues();
        values.put(NAME, product.getName());
        values.put(CALORIES, product.getCalories());
        values.put(PRODUCT_TYPE, String.valueOf(product.getProductType()));
        values.put(CATEGORY_TYPE, String.valueOf(product.getCategoryType()));
        values.put(IMAGE, product.getImage());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // Reading the products from the database.
    public ArrayList<Product> readProductFromDatabase(){
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Product product = null;
        while(!cursor.isAfterLast()){
            prodID = cursor.getString(cursor.getColumnIndex("id"));
            prodname = cursor.getString(cursor.getColumnIndex("name"));
            prodcalories = cursor.getString(cursor.getColumnIndex("calories"));
            prodproducttype = cursor.getString(cursor.getColumnIndex("producttype"));
            prodcategorytype = cursor.getString(cursor.getColumnIndex("categorytype"));
            prodimage = cursor.getString(cursor.getColumnIndex("image"));

            newProductType = getProductTypeCast(prodproducttype);
            newCategoryType = getCategoryTypeCast(prodcategorytype);

            product = new Product(Integer.valueOf(prodID), prodname, Integer.valueOf(prodcalories), newProductType, newCategoryType, Integer.valueOf(prodimage));
            products.add(product);

            cursor.moveToNext();
        }

        db.close();
        return products;
    }

    // Get the actual date time from the device.
    public String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Remove all calories of current day.
    public void removeCaloriesFromDatabase(String dayOfMonth){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_CALORIES + " SET " + AMOUNT_OF_CALORIES + "= 0 " + " WHERE " + DAY_OF_THE_MONTH + "= '" + dayOfMonth + "'");
    }

    // Remove specific product from database.
    public void removeProductFromDatabase(Product product){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + NAME + "= '" + product.getName() + "' AND " + ID + "= " + product.getID());
    }

    public ProductType getProductTypeCast(String productType){
        switch (productType){
            case "Alcohol":
                tempProductType = ProductType.Alcohol;
                break;
            case "Vegetables":
                tempProductType = ProductType.Vegetables;
                break;
            case "Dairy":
                tempProductType = ProductType.Dairy;
                break;
            case "Grains":
                tempProductType = ProductType.Grains;
                break;
            case "Fruits":
                tempProductType = ProductType.Fruits;
                break;
            case "Proteins":
                tempProductType = ProductType.Proteins;
                break;
        }

        return tempProductType;
    }

    public CategoryType getCategoryTypeCast(String categoryType){
        switch (categoryType){
            case "Breakfast":
                tempCatType = CategoryType.Breakfast;
                break;
            case "Lunch":
                tempCatType = CategoryType.Lunch;
                break;
            case "Dinner":
                tempCatType = CategoryType.Dinner;
                break;
        }

        return tempCatType;
    }
}
