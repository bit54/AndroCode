package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText emailBox, passBox;
    public CheckBox cheBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in); //will set layout (UserInterface)

        //Getting references

        emailBox=findViewById(R.id.emailBox);
        passBox=findViewById(R.id.passBox);
        cheBox=findViewById(R.id.cheBox);

    }

    public void createAccount(View view)
    {
       //setContentView(R.layout.rl_layout);
        //ToastMessage

      //  Toast msg = Toast.makeText(this,"Hi there",Toast.LENGTH_LONG);
        //msg.show(); */

        Intent intent= new Intent(this,SignUpActivity.class);
        startActivity(intent);

    }

    public void performLogin(View view)
    {
     //   Toast.makeText(this,"your have clicked sign in",Toast.LENGTH_LONG).show();

        //getting eamil and password
        String email = emailBox.getText().toString();  //returns string
        String pass = passBox.getText().toString();
        if (email.isEmpty())
        {
    //        Toast.makeText(this,"email is empty",Toast.LENGTH_LONG).show();


            emailBox.setError("Enter email address :)");  //sets error message
            emailBox.requestFocus();  // to focus on emailBox
        }
        else if (pass.isEmpty())
        {
            //Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
            passBox.setError("Enter password :)");
            passBox.requestFocus();
        }
        else
        {
            if (email.equals("iftum7@gmail.com") && pass.equals("iftu"))
            {
                /*start homepage*/
                Intent intent=new Intent(this,homeActivity.class);


                intent.putExtra("name","Iftekhar");
                intent.putExtra("email",email);
                startActivity(intent);

                finish();    //will finish activity
            }
            else{

                // AlertDialog
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Login Error");
                alert.setMessage("Incorrect email or password");

                alert.setCancelable(false); //alert box will not close on clicking back_button

                //so add button
                alert.setPositiveButton("close",null);
                alert.setIcon(R.drawable.ic_error);


                alert.show();
            }

        }
    }

}
