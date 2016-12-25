package maximedelange.calorieschecker.Screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import maximedelange.calorieschecker.R;

public class HomeScreen extends AppCompatActivity {

    // GUI components
    private ImageButton btnGoTo;
    private Bitmap bitmap;
    private Bitmap resizedbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Goes to the main screen
        goToCategory();
        btnGoTo = (ImageButton)findViewById(R.id.btnImage);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scale);
        int width=500;
        int height=500;
        resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        btnGoTo.setImageBitmap(resizedbitmap);

    }

    public void goToCategory(){
        btnGoTo = (ImageButton)findViewById(R.id.btnImage);
        btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryScreen.class);
                startActivity(intent);
            }
        });
    }

}
