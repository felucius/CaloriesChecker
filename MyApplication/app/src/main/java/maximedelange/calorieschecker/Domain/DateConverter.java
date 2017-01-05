package maximedelange.calorieschecker.Domain;

import java.util.ArrayList;

/**
 * Created by M on 1/4/2017.
 */

public class DateConverter {

    // Fields
    private String date;
    private int day;
    private int calories;

    // Constructor
    public DateConverter(String date, int day, int calories){
        this.date = date;
        this.day = day;
        this.calories = calories;
    }

    // Methods
    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return this.date;
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getDay(){
        return this.day;
    }

    public void setCalories(int calories){
        this.calories = calories;
    }

    public int getCalories(){
        return this.calories;
    }
}
