package com.example.btl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.Item;

import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    public Spinner sp;
    private EditText eName, eTime, eDate;
    private Button btUpdate, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initview();
        btUpdate.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
        eTime.setOnClickListener(this);
    }

    private void initview() {
        sp=findViewById(R.id.spDes);
        eName=findViewById(R.id.tvName);
        eTime=findViewById(R.id.tvTime);
        eDate=findViewById(R.id.tvDate);
        btUpdate=findViewById(R.id.btUpdate);
        btCancel=findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.Category)));
    }

    @Override
    public void onClick(View v) {
        if(v ==eDate){
            final Calendar c=Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date="";
                    if(m>8){
                        date=d+"/"+(m+1)+"/"+y;
                    } else {
                        date=d+"/0"+(m+1)+"/"+y;
                    }
                    eDate.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(v ==eTime){
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(AddActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                            eTime.setText(time);
                        }
                    }, hour, minute, true);
            dialog.show();
        }
        if(v == btCancel){
            finish();
        }
        if (v == btUpdate){
            String t1 = eName.getText().toString();
            String t2 = sp.getSelectedItem().toString();
            String t3 = eTime.getText().toString();
            String t4 = eDate.getText().toString();
            if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty() || t4.isEmpty()) {
                Toast.makeText(AddActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Item i = new Item(t1,t2,t3,t4);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(i);
                finish();
            }

        }
    }
}