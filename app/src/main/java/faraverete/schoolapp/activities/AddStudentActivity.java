package faraverete.schoolapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import faraverete.schoolapp.R;

/**
 * Created by faraverete on 09.12.2016.
 */

public class AddStudentActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editEmail;
    private EditText editAddress;
    private EditText editGrades;

    private Button addButton;

    //array that holds the requests
    public static ArrayList<Request> requestArray = new ArrayList<Request>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = (EditText) findViewById(R.id.NameInput);
        editEmail = (EditText) findViewById(R.id.EmailInput);
        editAddress = (EditText) findViewById(R.id.AddressInput);
        editGrades = (EditText) findViewById(R.id.GradesInput);

    }

    public void InsertStudent(View view) {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String address = editAddress.getText().toString();
        String grades = editGrades.getText().toString();

        Request r = new Request(name, email, address, grades);

        if (contains(requestArray, r)) {
            Toast.makeText(getBaseContext(), "Item already in the list", Toast.LENGTH_LONG).show();
        } else if (name == null || name.trim().equals("") || address == null || address.trim().equals("") || email == null || email.equals("") || grades == null | grades.equals("")) {
            Toast.makeText(getBaseContext(), "Some input is empty", Toast.LENGTH_LONG).show();
        } else {
            requestArray.add(r);

            ((EditText) findViewById(R.id.NameInput)).setText("");
            ((EditText) findViewById(R.id.EmailInput)).setText("");
            ((EditText) findViewById(R.id.AddressInput)).setText("");
            ((EditText) findViewById(R.id.GradesInput)).setText("");

            try {
                createFile(view, AddStudentActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
        View view = findViewById(R.id.NameTextView);
        goBack(view);
    }

    public void goBack(View view) {
        Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //checks if a request is contained by a list of request comparing each field
    public static boolean contains(ArrayList<Request> list, Request r) {
        for (Iterator<Request> i = list.iterator(); i.hasNext(); ) {
            Request req = i.next();
            if (req.getAddress().equals(r.getAddress()) && req.getEmail().equals(r.getEmail()) && req.getName().equals(r.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public static void createFile(View v, Context ctx) throws IOException, JSONException {
        JSONArray data = new JSONArray();
        JSONObject request;

        for (int i = 0; i < requestArray.size(); i++) {
            request = new JSONObject();
            request.put("name", requestArray.get(i).getName());
            request.put("email", requestArray.get(i).getEmail());
            request.put("address", requestArray.get(i).getAddress());
            request.put("grades", requestArray.get(i).getGrades());
            data.put(request);
        }

        String text = data.toString();
        FileOutputStream fos = ctx.openFileOutput("requestFile", MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

    }

    public static void readFile(View v, Context ctx) throws IOException, JSONException {
        FileInputStream fis = ctx.openFileInput("requestFile");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();
        while (bis.available() != 0) {
            char c = (char) bis.read();
            b.append(c);
        }

        bis.close();
        fis.close();

        JSONArray data = new JSONArray(b.toString());

        for (int i = 0; i < data.length(); i++) {
            String name = data.getJSONObject(i).getString("name");
            String email = data.getJSONObject(i).getString("email");
            String address = data.getJSONObject(i).getString("address");
            String grades = data.getJSONObject(i).getString("grades");
            String[] items = grades.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");


            Request r = new Request(name, email, address, grades);
            requestArray.add(r);
        }
    }




}
