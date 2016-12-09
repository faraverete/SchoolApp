package faraverete.schoolapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import faraverete.schoolapp.R;
import utils.Constants;

public class MainActivity extends AppCompatActivity {

    //buttons
    private Button addStudentButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcomeText = (TextView) findViewById(R.id.WelcomeMessageText);
        welcomeText.setText(Constants.WelcomeMessageText);

        TextView appDetailsText = (TextView) findViewById(R.id.AppDetailsAdminText);
        appDetailsText.setText(Constants.AppDetailsAdminText);

    }
}


