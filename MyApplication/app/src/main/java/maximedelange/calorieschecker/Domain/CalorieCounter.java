package maximedelange.calorieschecker.Domain;

/**
 * Created by M on 12/15/2016.
 */

public class CalorieCounter {

    // Fields
    private int countcalories;

    // Constructor
    public CalorieCounter(){
    }

    // Methods
    public void setCountcalories(int countcalories){
        this.countcalories = countcalories;
    }

    public int getCountcalories(){
        return this.countcalories;
    }

    public void addCalories(int countcalories){
        this.countcalories += countcalories;
    }
}
