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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
