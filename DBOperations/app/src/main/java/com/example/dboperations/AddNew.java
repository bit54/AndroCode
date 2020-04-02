package com.example.dboperations;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddNew extends AppCompatActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener , RadioGroup.OnCheckedChangeListener{

    public EditText emailBox, nameBox,mobile_no_Box;
    public RadioGroup genderBox;

    public TextView dob_Box;
    public Button  regBtn;


    public DBHelper helper;
    public SQLiteDatabase sql;

    //data set
    String dobDate,gender,name,email,mobile_number,dob;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        nameBox=findViewById(R.id.namebox);
        emailBox=findViewById(R.id.emailbox);

        mobile_no_Box=findViewById(R.id.mobile_no_box);
        dob_Box=findViewById(R.id.bob_box);

        genderBox=findViewById(R.id.genderbox);


        regBtn=findViewById(R.id.regbtn);



        //binding event

        regBtn.setOnClickListener(this);
        dob_Box.setOnClickListener(this);
        genderBox.setOnCheckedChangeListener(this);




    }


    public void onClick(View v) {

        int viewId = v.getId();


        if (viewId == R.id.bob_box) {
            //date picker dialog

            //choose current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(this, this, year, month, day);
            dpd.show();


        } else if (viewId == R.id.regbtn) {
            name = nameBox.getText().toString();
            email = emailBox.getText().toString();

            mobile_number = mobile_no_Box.getText().toString();
            dob = dob_Box.getText().toString();



            if (name.isEmpty()) {
                nameBox.setError("Enter your name");
                nameBox.requestFocus();
            } else if (email.isEmpty()) {
                emailBox.setError("Enter your email");
                emailBox.requestFocus();
            } else if (mobile_number.isEmpty()) {
                mobile_no_Box.setError("Enter your mobile number");
                mobile_no_Box.requestFocus();
            } else if (dob == null) {
                dob_Box.setError("Date of birth is empty");
                dob_Box.requestFocus();
            } else if (gender == null) {
                Toast.makeText(this, "choose the gender", Toast.LENGTH_SHORT).show();
            } else {
                helper = new DBHelper(this);
                sql = helper.getWritableDatabase();

                //creating content class's object
                ContentValues values = new ContentValues();

                values.put("name", name);
                values.put("email", email);
                values.put("mobile", mobile_number);
                values.put("dob", dob);
                values.put("gender", gender);

                long res = sql.insert("StudentsInfo", null, values);

                if (res > 0) {

                    nameBox.setText("");
                    emailBox.setText("");
                    mobile_no_Box.setText("");
                    dob_Box.setText("DOB(dd-mm-yy");
//                  genderBox.clearCheck();

                    ((RadioButton)findViewById(R.id.male)).setChecked(false);
                    ((RadioButton)findViewById(R.id.female)).setChecked(false);



                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        dobDate= day +"/"+(month+1)+"/"+year;
        dob_Box.setText(dobDate);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton rBtn= findViewById(i);
        gender = rBtn.getText().toString();
    }

}
