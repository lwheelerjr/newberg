package com.spiritflightapps.newberg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spiritflightapps.newberg.data.DatabaseHandler;

public class TestListAdapter extends BaseAdapter{

    private String[] bergTestDescriptions = {
            "1. SITTING TO STANDING",
            "2. STANDING UNSUPPORTED",
            "3. SIT WITH BACK\n UNSUPPORTED AND\n FEET SUPPORTED",
            "4. STANDING TO SITTING",
            "5. TRANSFERS",
            "6. STAND UNSUPPORTED\n WITH EYES CLOSED",
            "7. STAND UNSUPPORTED\n  WITH FEET TOGETHER",
            "8. STAND-REACH\n FORWARD WITH ARM",
            "9. STAND-PICKUP OBJECT\n FROM FLOOR",
            "10. STAND-LOOK BEHIND\n SHOULDERS",
            "11. TURN 360 DEGREES",
            "12. PLACE ALTERNATING\n FOOT ON STEP WHILE\n UNSUPPORTED",
            "13. STAND UNSUPPORTED\n ONE FOOT IN FRONT OF\n THE OTHER",
            "14. STANDING ON ONE LEG"};

    private Context context;
    private static LayoutInflater inflater=null;
    private Patient patient;
    private int currentTestIndex = 0;
    DatabaseHandler db;


    public TestListAdapter(Context context, Long patientId) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHandler(context);
        patient = db.getPatient(patientId);
    }

    public int getCount() {
        return bergTestDescriptions.length + 1;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        View vi;


        if(position < bergTestDescriptions.length ) {

            vi = inflater.inflate(R.layout.test_list_row, null);

            TextView symbol = (TextView) vi.findViewById(R.id.testname);
            //Patient patient = listData.get(position);

            symbol.setText(bergTestDescriptions[position]);
            Spinner spinner = (Spinner) vi.findViewById(R.id.score_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.score_array, R.layout.spinner_layout);
            adapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(adapter);
            spinner.setSelection(patient.getBergTests().get(currentTestIndex).getScores()[position] - 1 , false);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {
                    System.out.println("Called pos: " + pos + " id: " + id);
                    patient.getBergTests().get(currentTestIndex).getScores()[position] = new Integer(pos +1);
                    notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });


        } else {
            vi = inflater.inflate(R.layout.test_total_row, null);

            int sum = 0;
            for(Integer score : patient.getBergTests().get(currentTestIndex).getScores()) {
                sum += score;
            }
            TextView totalField = (TextView) vi.findViewById(R.id.total_score);
            totalField.setText(String.valueOf(sum));
        }

        return vi;
    }

    public void addTest(String name) {
        Integer[] tests = new Integer[bergTestDescriptions.length];
        for(int i=0; i<tests.length; ++i) {
            tests[i] = 0;
        }
        patient.getBergTests().add(new BergTest(name, tests));
        currentTestIndex++;
        notifyDataSetChanged();
    }

    public void deleteTest() {
        String name = patient.getBergTests().get(currentTestIndex).getName();
        patient.getBergTests().remove(currentTestIndex);
        notifyDataSetChanged();
        Toast.makeText(context, "Test '" + name + "' deleted.", Toast.LENGTH_LONG).show();
    }

    public void saveTests() {
        db.savePatient(patient);
    }


    public String getPatientName() {
        return patient.getName();
    }

    public String getCurrentTestName() {
        return patient.getBergTests().get(currentTestIndex).getName();
    }

}
