
package maximedelange.calorieschecker.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import maximedelange.calorieschecker.Controllers.ProductController;
import maximedelange.calorieschecker.Domain.CalorieCounter;
import maximedelange.calorieschecker.Domain.CategoryType;
import maximedelange.calorieschecker.Domain.Product;
import maximedelange.calorieschecker.Domain.ProductType;
import maximedelange.calorieschecker.R;
import maximedelange.calorieschecker.Screens.DinnerScreen;

/**
 * Created by M on 12/28/2016.
 */

public class ProductAdapter extends BaseAdapter implements Serializable{

    private ProductController productController = null;
    Context context;
    int [] imageId;
    String [] result;
    ArrayList<Product> newProduct;
    Product[] product1;
    Product[] breh;
    View rowView;;
    private Button dismisspopup;
    private Button removeproduct;
    private DinnerScreen dinnerScreen;
    private CalorieCounter calorieCounter;

    private static LayoutInflater inflater=null;

    public ProductAdapter(DinnerScreen screenAdapter, ArrayList<Product> products){
        dinnerScreen = new DinnerScreen();
        calorieCounter = new CalorieCounter();
        //dinnerScreen.changeStatusBar(0);
        //dinnerScreen.getTotalCalories();
        context=screenAdapter;
        this.newProduct = products;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        rowView = inflater.inflate(R.layout.productitem, null);
        holder.tv=(TextView) rowView.findViewById(R.id.txtproductname);
        holder.tv2 = (TextView)rowView.findViewById(R.id.txtamountofcalories);
        holder.tv3 = (TextView)rowView.findViewById(R.id.txtproducttype);
        holder.img=(ImageView) rowView.findViewById(R.id.imageproduct);

        breh = createProductArray();


        for(Product product : newProduct){
            if(product.getCategoryType() == CategoryType.Dinner){
                holder.tv.setText(breh[position].getName());
                holder.tv2.setText(String.valueOf(breh[position].getCalories()));
                holder.tv3.setText(String.valueOf(breh[position].getProductType()));
                holder.img.setImageResource(breh[position].getImage());
            }
        }

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final View background = v;
                background.setBackgroundColor(Color.RED);

                return false;
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calorieCounter.addCalories(breh[position].getCalories());
                //dinnerScreen.changeStatusBar(calorieCounter.getCountcalories());
            }
        });

        return rowView;
    }

    public class Holder
    {
        TextView tv;
        TextView tv2;
        TextView tv3;
        ImageView img;
    }

    public Product[] createProductArray(){

        product1 = new Product[newProduct.size()];

        for(int i = 0; i < newProduct.size(); i++){
            product1[i] = newProduct.get(i);
        }

        return product1;
    }
}
