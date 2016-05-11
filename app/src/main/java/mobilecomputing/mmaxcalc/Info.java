package mobilecomputing.mmaxcalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Info extends AppCompatActivity {
    public String username;
    public String password;
    public String weight;
    public String area;
    public String psi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("userName");
        password = extras.getString("password");
        weight = extras.getString("weight");
        area = extras.getString("area");
        psi = extras.getString("psi");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
    }


    public void onBackPressed(){
        Intent i = new Intent(Info.this, ScanActivity.class);
        i.putExtra("userName", username);
        i.putExtra("password", password);
        i.putExtra("weight", weight);
        i.putExtra("area", area);
        i.putExtra("psi", psi);
        startActivity(i);
        finish();
    }
}
