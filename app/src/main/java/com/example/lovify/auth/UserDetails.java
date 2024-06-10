package com.example.lovify.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lovify.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class UserDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText regName,regDOB;
    Spinner regSpinner;
    Button nextBtn;
    String[] gender = new String[]{"Male", "Female"};
    HashMap<String,Object> data = new HashMap<>();
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();
        data = (HashMap<String, Object>)intent.getSerializableExtra("data");

        regName = findViewById(R.id.regName);
        regDOB = findViewById(R.id.regDOB);
        regSpinner = findViewById(R.id.regSpinner);

        nextBtn = findViewById(R.id.nextBtn);

        regSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item,gender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regSpinner.setAdapter(arrayAdapter);

        nextBtn.setOnClickListener(v->{
            Intent i = new Intent(this, Pref.class);
            data.put("Name",regName.getText().toString());
            data.put("DOB",regDOB.getText().toString());
            int age = calculateAge(myCalendar.getTimeInMillis());
            data.put("Age", age);
            i.putExtra("data",data);
            startActivity(i);
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        data.put("Gender",gender[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };

        new DatePickerDialog(UserDetails.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(){
        String myFormat = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        regDOB.setText(sdf.format(myCalendar.getTime()));
    }

    private int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
}