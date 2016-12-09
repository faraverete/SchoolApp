package faraverete.schoolapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import faraverete.schoolapp.R;
import utils.AppState;
import utils.Validator;
import utils.Constants;

/**
 * Created by faraverete on 08.12.2016.
 */

public class LoginActivity extends Activity {

//    private String username;
//    private String password;
//    private EditText editTextUsername;
//    private EditText editTextPassword;
//    private Validator validator;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        this.validator = new Validator();
//        if (AppState.LOGGED) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            finish();
//            startActivity(intent);
//        } else {
//            setContentView(R.layout.activity_login);
//            editTextUsername = (EditText) findViewById(R.id.EditTextUsename);
//            editTextPassword = (EditText) findViewById(R.id.EditTextPassword);
//            editTextUsername.setHint(Constants.username);
//            editTextPassword.setHint(Constants.password);
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if(Integer.parseInt(android.os.Build.VERSION.SDK)> 5
//            &&keyCode == KeyEvent.KEYCODE_BACK
//            &&event.getRepeatCount() == 0){
//            onBackPressed();
//            return true;
//        }
//        return super.onKeyDown(keyCode,event);
//    }
//
//    @Override
//    public void onBackPressed(){
//        finish();
//    }
//
//    public void invokeLogin(View view){
//        username = editTextUsername.getText().toString();
//        password = editTextPassword.getText().toString();
//
//        switch (validator.ValidateUsernameAndPassword(username,password)){
//            case EMPTY_FIELD:
//                Toast.makeText(getApplicationContext(), Constants.EmptyLoginFields, Toast.LENGTH_LONG).show();
//                break;
//            case OK:
//                login(username, password);
//                break;
//            default:
//        }
//    }
//
//    private void login(final String username, String password){
//
//    }

}
