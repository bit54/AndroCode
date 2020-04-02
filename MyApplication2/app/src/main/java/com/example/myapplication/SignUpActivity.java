package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener , RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener
{

        public EditText emailBox, nameBox,passBox,mobile_no_Box,conferm_pass_Box;
        public RadioGroup genderBox;
        public Spinner courseBox;
        public TextView dob_Box;
        public Button showhideBtn, regBtn;
        public boolean isVisible=false;

        //data set
        String dobDate,gender,course,cpass,name,email,mobile_number,pass,dob;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        nameBox=findViewById(R.id.namebox);
        emailBox=findViewById(R.id.emailbox);
        passBox=findViewById(R.id.passbox);
        mobile_no_Box=findViewById(R.id.mobile_no_box);
        dob_Box=findViewById(R.id.bob_box);
        conferm_pass_Box=findViewById(R.id.conferm_pass_box);
        genderBox=findViewById(R.id.genderbox);
        courseBox=findViewById(R.id.course);
        showhideBtn=findViewById(R.id.showhidebtn);
        regBtn=findViewById(R.id.regbtn);



        //binding event
        showhideBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        dob_Box.setOnClickListener(this);
        genderBox.setOnCheckedChangeListener(this);
        courseBox.setOnItemSelectedListener(this);


}


    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        if(viewId==R.id.showhidebtn) {
           // Toast.makeText(this,"show hide",Toast.LENGTH_LONG).show();

            if (isVisible)  //false then hidden
            {
                showhideBtn.setText("Show");
                passBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                isVisible = false;
            } else {
                showhideBtn.setText("Hide");
                passBox.setInputType(InputType.TYPE_CLASS_TEXT);
                isVisible = true;
            }



        }
        else if(viewId == R.id.bob_box)
        {
            //date picker dialog

            //choose current date
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(this,this,year,month,day);
            dpd.show();



        }
        else if (viewId == R.id.regbtn)
        {
            name=nameBox.getText().toString();
            email=emailBox.getText().toString();
            pass=passBox.getText().toString();
            cpass=conferm_pass_Box.getText().toString();
            mobile_number=mobile_no_Box.getText().toString();
            dob=dob_Box.getText().toString();


            if (name.isEmpty())
            {
                nameBox.setError("Enter your name");
                nameBox.requestFocus();
            }
            else if (email.isEmpty())
            {
                emailBox.setError("Enter your email");
                emailBox.requestFocus();
            }
            else if (pass.isEmpty())
            {
                passBox.setError("Enter your password");
                passBox.requestFocus();
            }
            else if (cpass.isEmpty())
            {
                conferm_pass_Box.setError("Enter conferm password");
                conferm_pass_Box.requestFocus();
            }
            else if (mobile_number.isEmpty())
            {
                mobile_no_Box.setError("Enter your mobile number");
                mobile_no_Box.requestFocus();
            }
            else if (dob.isEmpty())
            {
                dob_Box.setError("Date of birth is empty");
            }
            else if (gender.isEmpty())
            {
                Toast.makeText(this,"choose the gender",Toast.LENGTH_SHORT).show();
            }
            else if (course.isEmpty())
            {
                Toast.makeText(this,"choose the Course",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
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
        gender= rBtn.getText().toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        course = courseBox.getSelectedItem().toString();
        Toast.makeText(this,course, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

}



