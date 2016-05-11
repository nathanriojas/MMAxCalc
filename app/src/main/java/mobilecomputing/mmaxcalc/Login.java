package mobilecomputing.mmaxcalc;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    Button btnSignIn,btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);

        // Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
                startActivity(intentSignUP);
            }
        });
    }
    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        final Dialog dialog = new Dialog(Login.this);
        dialog.setContentView(R.layout.login_layout);
        dialog.setTitle("Login");

        // get the Refferences of views
        final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                //List userAttributes = new ArrayList();
                // fetch the Password form database for respective user name
                String PASSWORD = loginDataBaseAdapter.getPassword(userName);

                if (PASSWORD.length() > 1) {

                    String storedWeight = loginDataBaseAdapter.getWeight(userName);
                    String storedArea = loginDataBaseAdapter.getArea(userName);
                    String storedPsi = loginDataBaseAdapter.getPSI(userName);
                    Toast.makeText(Login.this, storedWeight, Toast.LENGTH_SHORT).show();
                    // check if the Stored password matches with  Password entered by user
                    if(password.equals(PASSWORD))
                    {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        Intent i = new Intent(Login.this, ScanActivity.class);
                        i.putExtra("userName", userName);
                        i.putExtra("password", password);
                        i.putExtra("weight", storedWeight);
                        i.putExtra("area", storedArea);
                        i.putExtra("psi", storedPsi);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(Login.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                    }
                }

                else{
                    Toast.makeText(Login.this, "Username does not exist", Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}

