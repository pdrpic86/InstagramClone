package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave;
    private EditText etName, etPunchSpeed, etPunchPower, etKickSpeed, etKickPower;
    private TextView tvGetData;
    private Button bGetAllData;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.bSave);
        etName = findViewById(R.id.etName);
        etPunchSpeed = findViewById(R.id.etPunchSpeed);
        etPunchPower = findViewById(R.id.etPunchPower);
        etKickSpeed = findViewById(R.id.etKickSpeed);
        etKickPower = findViewById(R.id.etKickPower);
        tvGetData = findViewById(R.id.tvGetData);
        bGetAllData = findViewById(R.id.bGetAllData);

        btnSave.setOnClickListener(MainActivity.this);

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("UKWKpzVzC0", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if(object != null && e == null){
                            tvGetData.setText(object.get("name") + " - " + "Punch Power: " + object.get("punchPower"));
                        }

                    }
                });
            }
        });

        bGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(e == null ){

                            if(objects.size() > 0){

                                for(ParseObject kickBoxer : objects){
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                                }
                                FancyToast.makeText(MainActivity.this, allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                            }else{
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {

        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", etName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(etPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(etPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(etKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(etKickPower.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(MainActivity.this, kickBoxer.get("name") + " is saved to server", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else
                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }

            });
        }catch (Exception e){

            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }


    }
}
