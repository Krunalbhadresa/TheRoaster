package com.example.kbhadresha0939.theroster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.kbhadresha0939.theroster.R.id.button_birthdate;
import static com.example.kbhadresha0939.theroster.R.id.checkBox_no;
import static com.example.kbhadresha0939.theroster.R.id.checkBox_yes;
import static com.example.kbhadresha0939.theroster.R.id.pant_value;

public class MainActivity extends AppCompatActivity {

    private Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute;
    String birth_date;
    TextView pant_value, shirt_value, shoe_value;
    private EditText edittext_person_name;
    private CheckBox check_yes, check_no;
    private Spinner spinner;
    private Button btn_select_date;
    private RadioButton radio_xs, radio_s, radio_m, radio_l, radio_xl, radio_xxl;
    private SeekBar seek1, seek2, seek3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar a1 = getSupportActionBar();
        a1.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        edittext_person_name = (EditText) findViewById(R.id.editText_person_name);
        check_yes = (CheckBox) findViewById(checkBox_yes);
        check_no = (CheckBox) findViewById(checkBox_no);
        spinner = (Spinner) findViewById(R.id.spinner);
        btn_select_date = (Button) findViewById(R.id.button_birthdate);
        pant_value = (TextView) findViewById(R.id.pant_value);
        shirt_value = (TextView) findViewById(R.id.shirt_value);
        shoe_value = (TextView) findViewById(R.id.shoe_value);
        radio_xs = (RadioButton) findViewById(R.id.radio_XS);
        radio_s = (RadioButton) findViewById(R.id.radio_S);
        radio_m = (RadioButton) findViewById(R.id.radio_M);
        radio_l = (RadioButton) findViewById(R.id.radio_L);
        radio_xl = (RadioButton) findViewById(R.id.radio_XL);
        radio_xxl = (RadioButton) findViewById(R.id.radio_XXL);

        seek1 = (SeekBar) findViewById(R.id.seekBar_pant_size);
        seek2 = (SeekBar) findViewById(R.id.seekBar_shirt_size);
        seek3 = (SeekBar) findViewById(R.id.seekBar_shoe_size);

        ArrayList arrayList_eye_color = new ArrayList();
        arrayList_eye_color.add("Blue");
        arrayList_eye_color.add("Black");
        arrayList_eye_color.add("Grey");
        arrayList_eye_color.add("Green");
        arrayList_eye_color.add("Yellow");
        arrayList_eye_color.add("Red");

        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList_eye_color);
        spinner.setAdapter(arrayAdapter);

        SharedPreferences sharedData = getSharedPreferences("person_data", MODE_PRIVATE);
        String person_data = sharedData.getString("name","Not");

        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear++;
                                btn_select_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                                birth_date = dayOfMonth + "-" + monthOfYear + "-" + year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        if (!person_data.equals("Not")) {
            String name= sharedData.getString("name","available");
            String chbox= sharedData.getString("checkbox","available");
            String eye= sharedData.getString("eye","available");
            String b= sharedData.getString("date","available");
            String shirt= sharedData.getString("shirt_size","available");
            String pant= sharedData.getString("pant_value","available");
            String s= sharedData.getString("shirt_value","available");
            String shoe= sharedData.getString("shoe_value","available");

            edittext_person_name.setText(name);
            if(chbox.equals("Yes"))
            {
                check_yes.setChecked(true);
            }
            else{
                check_no.setChecked(true);
            }
            btn_select_date.setText(b);

            if(shirt.equals("XS"))
            {
                radio_xs.setChecked(true);
            }
            else if(shirt.equals("S"))
            {
                radio_s.setChecked(true);
            }
            else if(shirt.equals("M"))
            {
                radio_m.setChecked(true);
            }
            else if(shirt.equals("L"))
            {
                radio_l.setChecked(true);
            }
            else if(shirt.equals("XL"))
            {
                radio_xl.setChecked(true);
            }
            else
            {
                radio_xxl.setChecked(true);
            }

            seek1.setProgress(Integer.parseInt(pant));
            seek2.setProgress(Integer.parseInt(s));
            seek3.setProgress(Integer.parseInt(shoe));

            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter

            int spinnerPosition = myAdap.getPosition(eye);

//set the default according to value
            spinner.setSelection(spinnerPosition);
        } else {
            check_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_no.isChecked()) {

                        check_no.setChecked(false);

                    }

                }
            });
            check_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_yes.isChecked()) {

                        check_yes.setChecked(false);

                    }

                }
            });



            //For SeekBar code

            seek1.setMax(16);
            seek2.setMax(12);
            seek3.setMax(12);
            seek3.setProgress(4);

            seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int min = 0;
                int pant_size;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress >= min) {
                        pant_size = seekBar.getProgress();
                    } else {
                        seekBar.setProgress(min);
                    }
                    String pant_size_value = Integer.toString(pant_size);
                    pant_value.setText(pant_size_value);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

            });

            seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int min = 4;
                int shirt_size;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress >= min) {
                        shirt_size = seekBar.getProgress();
                    } else {
                        seekBar.setProgress(min);
                    }
                    String shoe_size_value = Integer.toString(shirt_size);
                    shirt_value.setText(shoe_size_value);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

            });


            seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int min = 4;
                int shoe_size;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress >= min) {
                        shoe_size = seekBar.getProgress();
                    } else {
                        seekBar.setProgress(min);
                    }
                    String shoe_size_value = Integer.toString(shoe_size);
                    shoe_value.setText(shoe_size_value);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

            });
        }

    }

    public void saveData(View view) {
        String person_name = edittext_person_name.getText().toString();
        String eye_color = spinner.getSelectedItem().toString();
        String checkbox_value;
        String shirt_size_value;

        //For checkbox value
        if (check_yes.isChecked()) {
            checkbox_value = "Yes";
        } else {
            checkbox_value = "No";
        }

        //For radiobutton value
        if (radio_xs.isChecked()) {
            shirt_size_value = "XS";
        } else if (radio_s.isChecked()) {
            shirt_size_value = "S";
        } else if (radio_m.isChecked()) {
            shirt_size_value = "M";
        } else if (radio_l.isChecked()) {
            shirt_size_value = "L";
        } else if (radio_xl.isChecked()) {
            shirt_size_value = "XL";
        } else {
            shirt_size_value = "XXL";
        }

        //Get Value of Seekbar
        int val1 = seek1.getProgress();
        int val2 = seek2.getProgress();
        int val3 = seek3.getProgress();




            SharedPreferences sharedPreferences = getSharedPreferences("person_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            System.out.println("First User");
            editor.putString("name", person_name);
            editor.putString("checkbox", checkbox_value);
            editor.putString("eye", eye_color);
            editor.putString("date", birth_date);
            editor.putString("shirt_size", shirt_size_value);
            editor.putString("pant_value", val1 + "");
            editor.putString("shirt_value", val2 + "");
            editor.putString("shoe_value", val3 + "");
            editor.commit();





        Toast.makeText(MainActivity.this, "Your data added", Toast.LENGTH_SHORT).show();


    }
}




