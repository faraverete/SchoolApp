package faraverete.schoolapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import faraverete.schoolapp.R;

/**
 * Created by faraverete on 09.12.2016.
 */

public class AddStudentActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        View view = findViewById(R.id.textView2);
        goBack(view);
    }

    public void goBack(View view) {
        Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
