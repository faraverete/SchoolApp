package faraverete.schoolapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import faraverete.schoolapp.R;

/**
 * Created by faraverete on 11.12.2016.
 */

public class ChartActivity extends AppCompatActivity {

    private static String TAG = " ChartActivity";

    public ArrayList<String> studentNamesArray = new ArrayList<String>();
    List<Integer> yDataList = new ArrayList<>(Arrays.asList(0, 0, 0));
    List<String> xDataList = new ArrayList<>(Arrays.asList("", "", ""));

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        studentNamesArray = getIntent().getStringArrayListExtra("arrayStudentNamesFromModifyActivity");

        HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

        for (int i = 0; i < studentNamesArray.size(); i++) {
            if (!dictionary.containsKey(studentNamesArray.get(i))) {
                dictionary.put(studentNamesArray.get(i), 0); // add to dictionary with value 0
            }
        }


        //increment values
        for (int i = 0; i < studentNamesArray.size(); i++) {
            dictionary.put(studentNamesArray.get(i), dictionary.get(studentNamesArray.get(i)) + 1);
        }

        for (String key : dictionary.keySet()) {
            dictionary.put(key, dictionary.get(key));
        }

        if (dictionary.size() > 0) {
            Map.Entry<String, Integer> maxEntry1 = null;

            for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                if (maxEntry1 == null || entry.getValue().compareTo(maxEntry1.getValue()) > 0) {
                    maxEntry1 = entry;
                }
            }

            yDataList.set(0, maxEntry1.getValue());
            xDataList.set(0, maxEntry1.getKey());
            dictionary.remove(maxEntry1.getKey());
        }

        if (dictionary.size() > 0) {
            Map.Entry<String, Integer> maxEntry2 = null;

            for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                if (maxEntry2 == null || entry.getValue().compareTo(maxEntry2.getValue()) > 0) {
                    maxEntry2 = entry;
                }
            }

            yDataList.set(1, maxEntry2.getValue());
            xDataList.set(1, maxEntry2.getKey());
            dictionary.remove(maxEntry2.getKey());
        }

        if (dictionary.size() > 0) {
            Map.Entry<String, Integer> maxEntry3 = null;

            for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                if (maxEntry3 == null || entry.getValue().compareTo(maxEntry3.getValue()) > 0) {
                    maxEntry3 = entry;
                }
            }

            yDataList.set(1, maxEntry3.getValue());
            xDataList.set(1, maxEntry3.getKey());
            dictionary.remove(maxEntry3.getKey());
        }

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(10);
        pieChart.setCenterText("CHART");

        Description d = new Description();
        d.setText("Each slice is a student name.");
        d.setTextSize(10);

        pieChart.setDescription(d);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String nrStudents = e.toString().substring(pos1 + 3);

                for (int i = 0; i < yDataList.size(); i++) {
                    if (yDataList.get(i) == Float.parseFloat(nrStudents)) {
                        pos1 = i;
                        break;
                    }
                }

                String brand = xDataList.get(pos1);
                Toast.makeText(ChartActivity.this, brand, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yDataList.size(); i++) {
            yEntrys.add(new PieEntry(yDataList.get(i), i));
        }

        for (int i = 0; i < xDataList.size(); i++) {
            xEntrys.add(xDataList.get(i));
        }

        //create data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Students");
        pieDataSet.setSliceSpace(10);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setSelectionShift(5);

        //colors to data set
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);

        pieDataSet.setColors(colors);

        //legend to data set
        Legend legend = pieChart.getLegend();

        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setXEntrySpace(20);
        legend.setYEntrySpace(50);
        legend.setTextSize(15);
        legend.setWordWrapEnabled(true);

        // create pie data object

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        //update
        pieChart.invalidate();
    }


}
