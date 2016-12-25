package maximedelange.calorieschecker.Domain;

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

public class Adapter extends ArrayAdapter<String>{

    // Fields
    private Context context;
    private String[] foods;
    private int[] images;

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

        TextView name = (TextView)convertView.findViewById(R.id.productName);
        ImageView image = (ImageView)convertView.findViewById(R.id.productImage);

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

        TextView name = (TextView)convertView.findViewById(R.id.productName);
        ImageView image = (ImageView)convertView.findViewById(R.id.productImage);

        name.setText(foods[position]);
        image.setImageResource(images[position]);

        return convertView;
    }
}
