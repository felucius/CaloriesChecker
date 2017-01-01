package maximedelange.calorieschecker.Screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Database.Database;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.DayOfTheWeek;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class CaloriesListScreen extends AppCompatActivity {

    private ProductController productController = null;
    private ArrayList<Product> products = null;
    private ArrayList<Product> combinedProducts = null;
    private ArrayList<Product> databaseProducts = null;
    private TableLayout tableLayout = null;
    private GridLayout gridLayout = null;
    private TableRow tableRow = null;
    private Bitmap bitmap = null;
    private Bitmap resizedbitmap = null;
    private CalorieCounter calorieCounter = null;
    private Context context = null;
    private Toast toast = null;
    private Database database = null;
    private String information = null;
    private int day;

    // GUI Components
    private TextView txtDayMonday;
    private TextView txtDayTuesday;
    private TextView txtDayWednesdag;
    private TextView txtDayThursday;
    private TextView txtDayFriday;
    private TextView txtDaySaturday;
    private TextView txtDaySunday;
    private TextView txtDate1;
    private TextView txtDate2;
    private TextView txtDate3;
    private TextView txtDate4;
    private TextView txtDate5;
    private TextView txtDate6;
    private TextView txtDate7;

    // Time
    Date date = new Date();
    private Calendar calendar;
    private DayOfTheWeek dayOfTheWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_list_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        combinedProducts = new ArrayList<>();
        databaseProducts = new ArrayList<>();
        database = new Database(this, null, null, 1);

        // Check time. Store calories at 00:00 for the given day.
        calendar = GregorianCalendar.getInstance();
        System.out.println("CURRENT MONTH: " + date.getMonth());
        calendar.setTime(date);
        //System.out.println("Day of the week: " + calendar.get(Calendar.DAY_OF_WEEK) + " hour: " + calendar.get(Calendar.HOUR) + " minute: " + calendar.get(Calendar.MINUTE));
        day = calendar.get(Calendar.DAY_OF_WEEK);
        int totalCalories = database.readCaloriesFromDatabase(day);

        Intent intent = getIntent();
        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");
        if(products != null){
            calorieCounter = new CalorieCounter();
            changeStatusBar(totalCalories);
            getTotalCalories();
            getTotalProducts();
        }else{
            productController = new ProductController();
            calorieCounter = new CalorieCounter();
            changeStatusBar(totalCalories);
            getTotalCalories();
            getTotalProducts();
        }

        checkTime();
    }

    public void changeStatusBar(int calories){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Total calories this week: " + calories);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getTotalCalories(){
        Intent intent = getIntent();
        information = intent.getStringExtra("totalCalories");
        if(information != null){
            System.out.println("INFORMATION RETRIEVED" + information);
            calorieCounter.addCalories(Integer.valueOf(information));
            changeStatusBar(Integer.valueOf(information));
        }
    }

    public void getTotalProducts(){
        Intent intent = getIntent();
        products = (ArrayList<Product>)intent.getSerializableExtra("totalProducts");
        if(products != null){
            databaseProducts = database.readProductFromDatabase();
            combinedProducts.addAll(products);
            productController.setStaticProducts(combinedProducts);

        }else{
            databaseProducts = database.readProductFromDatabase();
            combinedProducts.addAll(databaseProducts);
            productController.setStaticProducts(combinedProducts);
        }
    }

    public void checkTime(){
        txtDate1 = (TextView)findViewById(R.id.txtmondayDate);
        txtDate2 = (TextView)findViewById(R.id.txttuesdayDate);
        txtDate3 = (TextView)findViewById(R.id.txtwednesdayDate);
        txtDate4 = (TextView)findViewById(R.id.txtthursdayDate);
        txtDate5 = (TextView)findViewById(R.id.txtfridayDate);
        txtDate6 = (TextView)findViewById(R.id.txtsaturdayDate);
        txtDate7 = (TextView)findViewById(R.id.txtsundayDate);
        txtDayMonday = (TextView)findViewById(R.id.txtMonday);
        txtDayTuesday = (TextView)findViewById(R.id.txtTuesday);
        txtDayWednesdag = (TextView)findViewById(R.id.txtWednesday);
        txtDayThursday = (TextView)findViewById(R.id.txtThursday);
        txtDayFriday = (TextView)findViewById(R.id.txtFriday);
        txtDaySaturday = (TextView)findViewById(R.id.txtSaturday);
        txtDaySunday = (TextView)findViewById(R.id.txtSunday);

        // Check time. Store calories at 00:00 for the given day.
        //calendar = GregorianCalendar.getInstance();
        //System.out.println("CURRENT MONTH: " + date.getMonth());
        //calendar.setTime(date);
        //System.out.println("Day of the week: " + calendar.get(Calendar.DAY_OF_WEEK) + " hour: " + calendar.get(Calendar.HOUR) + " minute: " + calendar.get(Calendar.MINUTE));

        //int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(information == null){
            information = String.valueOf(0);
        }

        int totalCalories = database.readCaloriesFromDatabase(day);
        information = String.valueOf(totalCalories);

        txtDate1.setText("n/a");
        txtDate2.setText("n/a");
        txtDate3.setText("n/a");
        txtDate4.setText("n/a");
        txtDate5.setText("n/a");
        txtDate6.setText("n/a");
        txtDate7.setText("n/a");
        txtDayMonday.setText(String.valueOf(0));
        txtDayTuesday.setText(String.valueOf(0));
        txtDayWednesdag.setText(String.valueOf(0));
        txtDayThursday.setText(String.valueOf(0));
        txtDayFriday.setText(String.valueOf(0));
        txtDaySaturday.setText(String.valueOf(0));
        txtDaySunday.setText(String.valueOf(0));

        switch (day){
            case 2:
                txtDate1.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDayMonday.setText(information);
                break;
            case 3:
                txtDate2.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDayTuesday.setText(information);
                break;
            case 4:
                txtDate3.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDayWednesdag.setText(information);
                break;
            case 5:
                txtDate4.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDayThursday.setText(information);
                break;
            case 6:
                txtDate5.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDayFriday.setText(information);
                break;
            case 7:
                txtDate6.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDaySaturday.setText(information);
                break;
            case 1:
                txtDate7.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + calendar.get(Calendar.MONTH));
                txtDaySunday.setText(information);
                break;
        }
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
