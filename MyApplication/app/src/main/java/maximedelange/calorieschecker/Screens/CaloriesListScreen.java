package maximedelange.calorieschecker.Screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.GridLayout;
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
import maximedelange.calorieschecker.Domain.DateConverter;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.R;

public class CaloriesListScreen extends AppCompatActivity {

    private ProductController productController = null;
    private ArrayList<Product> products = null;
    private ArrayList<Product> combinedProducts = null;
    private ArrayList<Product> databaseProducts = null;
    private CalorieCounter calorieCounter = null;
    private Context context = null;
    private Toast toast = null;
    private Database database = null;
    private String information = null;
    private int day;
    private String dayMonth;
    private int totalCalories;
    int calthisweek = 0;

    // GUI Components
    private TextView txtDayMonday;
    private TextView txtDayTuesday;
    private TextView txtDayWednesday;
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
    private TextView txtCaloriesThisWeek;

    private Button btnClear;
    private Button dismisspopup;
    private Button removecalories;

    // Time
    Date date = new Date();
    private Calendar calendar;

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
        calendar.setTime(date);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        dayMonth = database.getDateTime();
        totalCalories = database.readCaloriesFromDatabase(dayMonth, day);

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
        clearCalories();
    }

    public void changeStatusBar(int calories){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Total calories this week");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_light)));
    }

    public void getTotalCalories(){
        Intent intent = getIntent();
        information = intent.getStringExtra("totalCalories");
        if(information != null){
            System.out.println("INFORMATION RETRIEVED" + information);
            calorieCounter.addCalories(Integer.valueOf(information));
            calorieCounter.setCountcalories(totalCalories);
            changeStatusBar(Integer.valueOf(information));
        }else{
            calorieCounter.setCountcalories(totalCalories);
            changeStatusBar(Integer.valueOf(totalCalories));
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
        txtDayWednesday = (TextView)findViewById(R.id.txtWednesday);
        txtDayThursday = (TextView)findViewById(R.id.txtThursday);
        txtDayFriday = (TextView)findViewById(R.id.txtFriday);
        txtDaySaturday = (TextView)findViewById(R.id.txtSaturday);
        txtDaySunday = (TextView)findViewById(R.id.txtSunday);
        txtCaloriesThisWeek = (TextView)findViewById(R.id.txtTotalCalories);

        if(information == null){
            information = String.valueOf(0);
        }

        int totalCalories = database.readCaloriesFromDatabase(database.getDateTime(), day);
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
        txtDayWednesday.setText(String.valueOf(0));
        txtDayThursday.setText(String.valueOf(0));
        txtDayFriday.setText(String.valueOf(0));
        txtDaySaturday.setText(String.valueOf(0));
        txtDaySunday.setText(String.valueOf(0));

        ArrayList<DateConverter> weekInfo = database.getWeekInformation();
        for(DateConverter day : weekInfo){
            switch (day.getDay()){
                case 2:
                    txtDate1.setText(day.getDate());
                    txtDayMonday.setText(String.valueOf(day.getCalories()));
                    break;
                case 3:
                    txtDate2.setText(day.getDate());
                    txtDayTuesday.setText(String.valueOf(day.getCalories()));
                    break;
                case 4:
                    txtDate3.setText(day.getDate());
                    txtDayWednesday.setText(String.valueOf(day.getCalories()));
                    break;
                case 5:
                    txtDate4.setText(day.getDate());
                    txtDayThursday.setText(String.valueOf(day.getCalories()));
                    break;
                case 6:
                    txtDate5.setText(day.getDate());
                    txtDayFriday.setText(String.valueOf(day.getCalories()));
                    break;
                case 7:
                    txtDate6.setText(day.getDate());
                    txtDaySaturday.setText(String.valueOf(day.getCalories()));
                    break;
                case 1:
                    txtDate7.setText(day.getDate());
                    txtDaySunday.setText(String.valueOf(day.getCalories()));
                    break;
            }

            calthisweek += day.getCalories();
            txtCaloriesThisWeek.setText(String.valueOf(calthisweek));
        }
    }

    public void clearCalories(){
        btnClear = (Button)findViewById(R.id.btnClearCalories);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View background = v;
                final Dialog dialog = new Dialog(CaloriesListScreen.this);
                dialog.setContentView(R.layout.popup_overviewscreen);
                dialog.show();

                dismisspopup = (Button)dialog.findViewById(R.id.dismissPopup);
                dismisspopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        background.setBackgroundDrawable(getResources().getDrawable(R.drawable.productimages));
                    }
                });

                removecalories = (Button)dialog.findViewById(R.id.removeProduct);
                removecalories.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        database.removeCaloriesFromDatabase(database.getDateTime());
                        calorieCounter.setCountcalories(0);
                        changeStatusBar(calorieCounter.getCountcalories());
                        calthisweek = 0;
                        showToastMessage("calories of today are cleared");
                        checkTime();
                    }
                });
            }
        });
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
