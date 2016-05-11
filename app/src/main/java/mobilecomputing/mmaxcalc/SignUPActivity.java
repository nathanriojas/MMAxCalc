package mobilecomputing.mmaxcalc;

import android.app.Activity;
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

public class SignUPActivity extends AppCompatActivity
{
    EditText editTextUserName,editTextPassword,editTextConfirmPassword, editWeight, editArea;
    Button btnCreateAccount;
    Float psi = (float) 0;

    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        //loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        editWeight=(EditText)findViewById(R.id.editWeight);
        editArea=(EditText)findViewById(R.id.editArea);


        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword= editTextConfirmPassword.getText().toString();
                String weight = editWeight.getText().toString();
                String area = editArea.getText().toString();
                //String weight = Integer.parseInt(editWeight.getText().toString());
                //String area = Float.parseFloat(editArea.getText().toString());

                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals("")||weight.equals("")||area.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                } else {
                        if(loginDataBaseAdapter.getPassword(userName).length() > 1) {
                            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
                            return;
                        }else {
                            // Save the Data in Database
                            loginDataBaseAdapter.insertData(userName, password, weight, area, psi.toString());
                            Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(),"Redirecting...", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUPActivity.this, ScanActivity.class);
                            i.putExtra("userName", userName);
                            i.putExtra("password", password);
                            i.putExtra("weight", weight);
                            i.putExtra("area", area);
                            i.putExtra("psi", psi.toString());
                            startActivity(i);
                        }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}