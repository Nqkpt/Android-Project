package com.example.btl.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.adapter.RecycleViewAdapter;
import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private Button btSearch;
    private SearchView searchView;
    private EditText eFrom, eTo;
    private Spinner spDes;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list=db.getAllItems();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list=db.searchItemsByName(s);
                adapter.setList(list);
                return true;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        spDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String des = spDes.getItemAtPosition(position).toString();
                List<Item> list;
                if(des.equalsIgnoreCase("all")){
                    list=db.getAllItems();
                } else {
                    list=db.searchItemsByDes(des);
                }
                adapter.setList(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        btSearch=view.findViewById(R.id.btSearch);
        searchView=view.findViewById(R.id.search);
        eFrom=view.findViewById(R.id.edFrom);
        eTo=view.findViewById(R.id.edTo);
        spDes=view.findViewById(R.id.spDes);
        String[] arr=getResources().getStringArray(R.array.Category);
        String[] arr1=new String[arr.length+1];
        arr1[0]="All";
        for (int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        spDes.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));


    }

    @Override
    public void onClick(View view) {
        if(view==eFrom){
            final Calendar c=Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date="";
                    if(m>8){
                        date=d+"/"+(m+1)+"/"+y;
                    } else {
                        date=d+"/0"+(m+1)+"/"+y;
                    }
                    eFrom.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view==eTo){
            final Calendar c=Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date="";
                    if(m>8){
                        date=d+"/"+(m+1)+"/"+y;
                    } else {
                        date=d+"/0"+(m+1)+"/"+y;
                    }
                    eTo.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view==btSearch){
            String from = eFrom.getText().toString();
            String to = eTo.getText().toString();
            if(!from.isEmpty() && !to.isEmpty()){
                List<Item> list=db.searchItemsByDateRange(from,to);
                adapter.setList(list);
            }
        }
    }
}
