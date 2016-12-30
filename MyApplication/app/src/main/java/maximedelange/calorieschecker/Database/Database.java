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

    private String prodname = null;
    private String prodcalories = null;
    private String prodproducttype = null;
    private String prodcategorytype = null;
    private String prodimage = null;

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
        String query = "SELECT * FROM " + TABLE_PRODUCTS;// + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Product product = null;
        while(!cursor.isAfterLast()){
            prodname = cursor.getString(cursor.getColumnIndex("name"));
            prodcalories = cursor.getString(cursor.getColumnIndex("calories"));
            prodproducttype = cursor.getString(cursor.getColumnIndex("producttype"));
            prodcategorytype = cursor.getString(cursor.getColumnIndex("categorytype"));
            prodimage = cursor.getString(cursor.getColumnIndex("image"));

            product = new Product(prodname, Integer.valueOf(prodcalories), ProductType.Alcohol, CategoryType.Dinner, Integer.valueOf(prodimage));
            products.add(product);

            cursor.moveToNext();
        }

        db.close();
        return products;
    }

    public void removeFromDatabase(Product productName){//}, int productCalories){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + NAME + "= '" + productName.getName() + "'");
    }
}
