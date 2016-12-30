package maximedelange.calorieschecker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;

/**
 * Created by M on 12/29/2016.
 */

public class Database extends SQLiteOpenHelper{

    // Fields
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "product.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CALORIES = "calories";
    public static final String PRODUCT_TYPE = "producttype";
    public static final String CATEGORY_TYPE = "categorytype";
    public static final String IMAGE = "image";

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

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        System.out.println("DATABASE PATH: " + context.getDatabasePath("product.db"));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(db.isOpen());

        String query = "create table " + TABLE_PRODUCTS + "(" +
                ID + " integer primary key auto increment" +
                NAME + " text " +
                CALORIES + " text " +
                PRODUCT_TYPE + " text " +
                CATEGORY_TYPE + " text " +
                IMAGE+ " text " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRODUCTS);
        onCreate(db);
    }

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

    public void removeFromDatabase(Product product){//}, int productCalories){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + NAME + "= '" + product.getName() + "' AND " + ID + "= " + product.getID());
    }

    public ProductType getProductTypeCast(String productType){
        switch (productType){
            case "Alcohol":
                tempProductType = ProductType.Alcohol;
                break;
            case "Bread":
                tempProductType = ProductType.Bread;
                break;
            case "Chicken":
                tempProductType = ProductType.Chicken;
                break;
            case "Dairy":
                tempProductType = ProductType.Dairy;
                break;
            case "Fruit":
                tempProductType = ProductType.Fruit;
                break;
            case "Meat":
                tempProductType = ProductType.Meat;
                break;
            case "Pasta":
                tempProductType = ProductType.Pasta;
                break;
            case "Potatoes":
                tempProductType = ProductType.Potatoes;
                break;
            case "SandwichFilling":
                tempProductType = ProductType.SandwichFilling;
                break;
            case "Snacks":
                tempProductType = ProductType.Snacks;
                break;
            case "Vegetables":
                tempProductType = ProductType.Vegetables;
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
