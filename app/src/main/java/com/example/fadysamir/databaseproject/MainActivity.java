package com.example.fadysamir.databaseproject;

import android.support.annotation.NonNull;
import android.support.v4.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button sendDatatButton;
    EditText nameEditText, mailEditText;
    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendDatatButton = findViewById(R.id.buttonSend);
        nameEditText = findViewById(R.id.nameEditText);
        mailEditText = findViewById(R.id.mailEditText);
        textView = findViewById(R.id.textView11);
        listView = findViewById(R.id.listView1);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        final ArrayList <String> nameArrayList = new ArrayList<>();
        final ArrayList <String> mailArrayList = new ArrayList<>();
        final ArrayList <String> keyArrayList = new ArrayList<>();

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameArrayList);
        listView.setAdapter(arrayAdapter);


/*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                textView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Users users = dataSnapshot.getValue(Users.class);
                nameArrayList.add(users.getName()+" "+users.getMail());
                mailArrayList.add(users.getMail());
                keyArrayList.add(dataSnapshot.getKey());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Users newUser = dataSnapshot.getValue(Users.class);

                //String newName = dataSnapshot.getValue().toString();
                String newName = newUser.getName();
                String newMail = newUser.getMail();
                String changedKey = dataSnapshot.getKey();
                int index = keyArrayList.indexOf(changedKey);
                nameArrayList.set(index, newName);
                mailArrayList.set(index, newMail);
                arrayAdapter.notifyDataSetChanged();



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        sendDatatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //databaseReference.child("name").push().setValue("fady");

                String name = nameEditText.getText().toString();
                String mail = mailEditText.getText().toString();

                Users user1 = new Users(name, mail);
                databaseReference.push().setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Data set successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Error, Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }
}
