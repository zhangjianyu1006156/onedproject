package com.example.onedproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private EditText edit;
    private Button add;

    private ListView listView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,StartActivity.class));
            }
        });

        //add one object
        //mDatabase.child("Name").setValue("jy");

        //add a HashMap to firebase
        /**HashMap<String, Object> datalist = new HashMap<>();
        datalist.put("jy","60kg");
        datalist.put("cat","10kg");
        FirebaseDatabase.getInstance().getReference()
         .child("user").updateChildren(datalist);**/

        /**Information first_food = new Information("Apple","1kg");
        Information second_food = new Information("Banana","2kg");
        Information third_food = new Information("Orange","3kg");
        //HashMap<Object, Object> list = new HashMap<>();
        mDatabase.child("Information").child("one").setValue(first_food);
        mDatabase.child("Information").child("two").setValue(second_food);
        mDatabase.child("Information").child("three").setValue(third_food);**/

        //use EditText to add data to firebase
         add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = edit.getText().toString();
                if (txt_name.isEmpty()){
                    Toast.makeText(MainActivity.this,"No name entered",Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.child("User").child("Name").setValue(txt_name);
                }
            }
        });

        //Retrieving data from firebase
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        DatabaseReference reference = mDatabase.child("Information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Information info = snapshot.getValue(Information.class);
                    String txt = info.getFood() + "=" + info.getWeight();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        trend_class trend = new trend_class("Running","20","min",1);
        mDatabase.child("Trend").setValue(trend);
    }
}