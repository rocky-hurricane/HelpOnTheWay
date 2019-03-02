package yuqis.cmu.edu.aircasting;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class PM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PM ma = this;


        Button submitButton = (Button)findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText)findViewById(R.id.searchCity)).getText().toString();
                GetPM gp = new GetPM();
                gp.search(searchTerm, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });
    }

    /**
     * Called by the GetPM object to display the pm 2.5 information to UI
     * @param picture
     */
    public void displayInfo(String picture) {
        TextView searchView = (EditText)findViewById(R.id.searchCity);

        TextView resultView = (EditText)findViewById(R.id.searchResult);

        String searchTerm = ((EditText)findViewById(R.id.searchCity)).getText().toString();

        if (picture != null) {
            resultView.setText(picture);
            resultView.setVisibility(View.VISIBLE);

            //pictureView.setImageBitmap(picture);
            //pictureView.setVisibility(View.VISIBLE);
        } else {
            resultView.setText("Sorry, we can not find the PM 2.5 level for "+searchTerm);
            resultView.setVisibility(View.VISIBLE);

            //pictureView.setImageResource(R.mipmap.ic_launcher);
            //pictureView.setVisibility(View.INVISIBLE);
        }
        searchView.setText("");
        //pictureView.invalidate();
    }
}