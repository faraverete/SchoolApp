package faraverete.schoolapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import faraverete.schoolapp.R;

import static faraverete.schoolapp.activities.AddStudentActivity.contains;
import static faraverete.schoolapp.activities.AddStudentActivity.createFile;
import static faraverete.schoolapp.activities.AddStudentActivity.requestArray;
import static faraverete.schoolapp.activities.AddStudentActivity.show;

/**
 * Created by faraverete on 11.12.2016.
 */

public class ModifiyActivity extends AppCompatActivity {

    private EditText receivedName;
    private EditText receivedEmail;
    private EditText receivedAddress;
    private EditText receivedGrades;

    private Button saveButton;
    private Button deleteButton;
    private Button chartButton;

    public ArrayList<String> studentNamesArray = new ArrayList<String>();
    public ArrayList<String> gradesArray = new ArrayList<String>();
    ArrayList<Integer> grades = new ArrayList<Integer>();

    private void convert(ArrayList<String> s, ArrayList<Integer> myint) {
        s = new ArrayList<String>();
        myint = new ArrayList<Integer>(s.size());
        for (String i : s) {
            myint.add(Integer.valueOf(i));
        }
    }


    public void onClickChartButton() {
        convert(gradesArray, grades);
        chartButton = (Button) findViewById(R.id.ChartButton);

        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("faraverete.schoolapp.activities.ChartActivity");
                intent.putExtra("arrayStudentNamesFromModifyActivity", grades);
                startActivity(intent);
            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyactivity);

        onClickChartButton();
        saveButton = (Button) findViewById(R.id.SaveChangesButton);
        deleteButton = (Button) findViewById(R.id.DeleteButton);

        String nameGet = getIntent().getStringExtra("itemName");
        String emailGet = getIntent().getStringExtra("itemEmail");
        String addressGet = getIntent().getStringExtra("itemAddress");
        String gradesGet = getIntent().getStringExtra("itemGrades");
        final int pos = getIntent().getIntExtra("itemPosition", -1);

        studentNamesArray = getIntent().getStringArrayListExtra("arrayStudentsNames");

        receivedName = (EditText) findViewById(R.id.ReceivedName);
        receivedName.setText(nameGet);

        receivedEmail = (EditText) findViewById(R.id.ReceivedEmail);
        receivedEmail.setText(emailGet);

        receivedAddress = (EditText) findViewById(R.id.ReceivedAddress);
        receivedAddress.setText(addressGet);

        receivedGrades = (EditText) findViewById(R.id.ReceivedGrades);
        receivedGrades.setText(gradesGet);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNewName = receivedName.getText().toString();
                String getNewEmail = receivedEmail.getText().toString();
                String getNewAddress = receivedAddress.getText().toString();
                String getNewGrades = receivedGrades.getText().toString();

                Request r = new Request(getNewName, getNewEmail, getNewAddress, getNewGrades);

                if (contains(requestArray, r)) {
                    finish();
                } else if (getNewName == null || getNewName.trim().equals("") || getNewEmail == null || getNewEmail.equals("")
                        || getNewAddress == null || getNewAddress.trim().equals("") || getNewGrades == null || getNewGrades.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Some input is empty", Toast.LENGTH_LONG).show();
                } else {
                    requestArray.set(pos, r);
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
                    show.invalidateViews();

                    try {
                        createFile(v, ModifiyActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finish();
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ModifiyActivity.this);
                builder1.setMessage("Are you sure you want to delete this student?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String getNewName = receivedName.getText().toString();
                                String getNewEmail = receivedEmail.getText().toString();
                                String getNewAddress = receivedAddress.getText().toString();
                                String getNewGrades = receivedGrades.getText().toString();

                                Request r = new Request(getNewName, getNewEmail, getNewAddress, getNewGrades);

                                if (contains(requestArray, r)) {
                                    requestArray.remove(pos);
                                    Toast.makeText(getBaseContext(), "Student deleted!", Toast.LENGTH_LONG).show();
                                    show.invalidateViews();

                                    try {
                                        createFile(v, ModifiyActivity.this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    finish();
                                } else {
                                    Toast.makeText(getBaseContext(), "There is no matching request", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog alert = builder1.create();
                alert.show();
            }
        });

    }


}
