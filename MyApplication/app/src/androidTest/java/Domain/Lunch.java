package Domain;

/**
 * Created by M on 12/13/2016.
 */

public class Lunch extends Category{

    // Fields
    private boolean isHot;

    // Constructor
    public Lunch(String name, boolean isHot){
        super(name);
        this.isHot = isHot;
    }

    // Methods
    public boolean getIsHot(){
        return this.isHot;
    }
}
