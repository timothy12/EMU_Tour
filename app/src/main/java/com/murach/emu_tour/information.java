package com.murach.emu_tour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class information extends AppCompatActivity implements View.OnClickListener {
    private Button Back;
    private Intent BackIntent;
    private Spinner location;
    private Button Go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Back = (Button) findViewById(R.id.button);
        Go = (Button) findViewById(R.id.button2);
        Back.setOnClickListener(this);
        BackIntent = new Intent(getApplicationContext(),
                MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        location = (Spinner) findViewById(R.id.spinner);

        //setting up an array for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter);

        //set up listeners
        Go.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button:
                startActivity(BackIntent);
            case R.id.button2:
                //must collect data on location from database
                //based on the location selected in the spinner
        }

    }

}
