package faraverete.schoolapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private Button refreshButton;

    public static ListView show;

    public static ArrayList<Request> requestArray = new ArrayList<Request>();


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
        View view = findViewById(R.id.MyListView);
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
            if (req.getGrades().equals(r.getGrades()) && req.getAddress().equals(r.getAddress()) && req.getEmail().equals(r.getEmail()) && req.getName().equals(r.getName())) {
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
            //String[] items = grades.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");


            Request r = new Request(name, email, address, grades);
            requestArray.add(r);
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = (EditText) findViewById(R.id.NameInput);
        editEmail = (EditText) findViewById(R.id.EmailInput);
        editAddress = (EditText) findViewById(R.id.AddressInput);
        editGrades = (EditText) findViewById(R.id.GradesInput);

        addButton = (Button) findViewById(R.id.addButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        show = (ListView) findViewById(R.id.MyListView);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    readFile(v, AddStudentActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (requestArray.isEmpty()) {
                    Toast.makeText(getBaseContext(), "List is empty", Toast.LENGTH_LONG).show();
                } else {
                    ArrayAdapter<Request> adapter =
                            new ArrayAdapter<Request>(AddStudentActivity.this, android.R.layout.simple_list_item_1, requestArray);
                    show.setAdapter(adapter);
                }
            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = editName.getText().toString();
                String getEmail = editEmail.getText().toString();
                String getAddress = editAddress.getText().toString();
                String getGrades = editGrades.getText().toString();

                Request r = new Request(getName, getEmail, getAddress, getGrades);

                if (contains(requestArray, r)) {
                    Toast.makeText(getBaseContext(), "Item already in the list", Toast.LENGTH_LONG).show();
                } else if (getName == null || getName.trim().equals("") || getEmail == null || getEmail.equals("")
                        || getAddress == null || getAddress.trim().equals("") || getGrades == null || getGrades.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Some input is empty", Toast.LENGTH_LONG).show();
                } else {
                    requestArray.add(r);
                    ArrayAdapter<Request> adapter =
                            new ArrayAdapter<Request>(AddStudentActivity.this, android.R.layout.simple_list_item_1, requestArray);
                    show.setAdapter(adapter);
                    ((EditText) findViewById(R.id.NameInput)).setText("");
                    ((EditText) findViewById(R.id.EmailInput)).setText("");
                    ((EditText) findViewById(R.id.AddressInput)).setText("");
                    ((EditText) findViewById(R.id.GradesInput)).setText("");

                    try {
                        createFile(v, AddStudentActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Request selectedFromList = (Request) (show.getItemAtPosition(position));

                Intent intent = new Intent(view.getContext(), ModifiyActivity.class);
                intent.putExtra("itemName", selectedFromList.getName());
                intent.putExtra("itemEmail", selectedFromList.getEmail());
                intent.putExtra("itemAddress", selectedFromList.getAddress());
                intent.putExtra("itemGrades", selectedFromList.getGrades());


                ArrayList<String> studentsNames = new ArrayList<String>();
                for (int i = 0; i < requestArray.size(); i++) {
                    studentsNames.add(requestArray.get(i).getName());
                }

                intent.putExtra("arrayStudentsNames", studentsNames);
                intent.putExtra("itemPosition", position);

                AddStudentActivity.this.startActivityForResult(intent, 0);
            }
        });
    }



}



