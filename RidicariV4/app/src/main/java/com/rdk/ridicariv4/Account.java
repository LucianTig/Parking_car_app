package com.rdk.ridicariv4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Account extends AppCompatActivity {

    private EditText edtx_name;
    private EditText edtx_car_id;
    private TextView text_view_email;

    private Button log_out;
    private Button save_modification;
    private Button change_pass;

    FirebaseFirestore db;
    FirebaseAuth mFireBaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        db = FirebaseFirestore.getInstance();
        mFireBaseAuth = FirebaseAuth.getInstance();

        edtx_name = findViewById(R.id.name);
        edtx_car_id = findViewById(R.id.car_id);
        text_view_email = findViewById(R.id.email);

        log_out = findViewById(R.id.button_log_out);
        save_modification = findViewById(R.id.button_save_modification);
        change_pass = findViewById(R.id.button_change_password);

        final String uID = mFireBaseAuth.getCurrentUser().getUid().toString();
        DocumentReference docRef = db.collection("users").document(uID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        String name = document.getString("name");
                        String carid = document.getString("carID");
                        String email = document.getString("email");

                        if (name != null)
                            edtx_name.setText(name);
                        else
                            edtx_name.setText("None");

                        if (carid != null)
                            edtx_car_id.setText(carid);
                        else
                            edtx_car_id.setText("None");

                        if (email != null)
                            text_view_email.setText(email);
                    }
                } else {
                    Toast.makeText(Account.this, "Can't find your account information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity( new Intent(Account.this, MainActivity.class));
            }
        });



        save_modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtx_name.getText().toString();
                String carid = edtx_car_id.getText().toString();

                db.collection("users").document(uID).update("name", name);
                db.collection("users").document(uID).update("carID", carid);

                Toast.makeText(Account.this, "Modification saved", Toast.LENGTH_SHORT).show();


            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i= new Intent(Account.this, Change.class);
                startActivity(i);
            }
        });
    }
}
