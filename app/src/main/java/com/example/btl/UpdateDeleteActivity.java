package com.example.btl;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner sp;
    private EditText eName, eTime, eDate;
    private Button btUpdate, btBack, btDelete;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initview();
        btUpdate.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        eDate.setOnClickListener(this);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        eName.setText(item.getName());
        eTime.setText(item.getTime());
        eDate.setText(item.getDate());
        int p=0;
        for (int i=0; i<sp.getCount();i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getDes())){
                p=i;
                break;
            }
        }
        sp.setSelection(p);
    }

    private void initview() {
        sp=findViewById(R.id.spDes);
        eName=findViewById(R.id.tvName);
        eTime=findViewById(R.id.tvTime);
        eDate=findViewById(R.id.tvDate);
        btUpdate=findViewById(R.id.btUpdate);
        btBack=findViewById(R.id.btBack);
        btDelete=findViewById(R.id.btDelete);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.Category)));
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        if(view == eDate){
            final Calendar c=Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if(view ==eTime){
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(UpdateDeleteActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                            eTime.setText(time);
                        }
                    }, hour, minute, true); // true để hiển thị dạng 24 giờ
            dialog.show();
        }
        if(view==btBack){
            finish();
        }
        if(view==btUpdate){
            String t1 = eName.getText().toString();
            String t2 = sp.getSelectedItem().toString();
            String t3 = eTime.getText().toString();
            String t4 = eDate.getText().toString();
            if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty() || t4.isEmpty()) {
                Toast.makeText(UpdateDeleteActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!t1.isEmpty()){
                int id=item.getId();
                Item i = new Item(id,t1,t2,t3,t4);
                db = new SQLiteHelper(this);
                db.updateItem(i);
                finish();
            }
        }
        if(view==btDelete){
            int id= item.getId();
            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setMessage("Ban co chac muon xoa "+item.getName()+" khong?");
            builder.setIcon(R.drawable.search_icon);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    SQLiteHelper dd = new SQLiteHelper(getApplicationContext());
                    dd.deleteItem(id);
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }
}