package maximedelange.calorieschecker.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import maximedelange.calorieschecker.R;

/**
 * Created by M on 12/21/2016.
 */
/*
Custom adapter to show all product images and text. This allows the user to add a new product with
an image of choice. This information will all be displayed in a spinner.
 */
public class Adapter extends ArrayAdapter<String>{

    // Fields
    private Context context;
    private String[] foods;
    private int[] images;

    // Constructor
    public Adapter(Context context, String[] foods, int[] images){
        super(context, R.layout.custom_spinner, foods);
        this.context = context;
        this.foods = foods;
        this.images = images;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner, null);
        }

        // Name and image of the products
        TextView name = (TextView)convertView.findViewById(R.id.productName);
        ImageView image = (ImageView)convertView.findViewById(R.id.productImage);

        // Set the position for each image and name
        name.setText(foods[position]);
        image.setImageResource(images[position]);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner, null);
        }

        // Name and image of the products
        TextView name = (TextView)convertView.findViewById(R.id.productName);
        ImageView image = (ImageView)convertView.findViewById(R.id.productImage);

        // Set the position for each image and name
        name.setText(foods[position]);
        image.setImageResource(images[position]);

        return convertView;
    }
}
