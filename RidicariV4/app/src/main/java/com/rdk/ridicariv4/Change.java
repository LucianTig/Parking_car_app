package com.rdk.ridicariv4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Change extends AppCompatActivity {

    private EditText edtx_newpass;
    private Button change_passs;
    private EditText edtx_oldpass;
    ProgressDialog dialog;

    FirebaseFirestore db;
    FirebaseAuth mFireBaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        dialog=new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        mFireBaseAuth = FirebaseAuth.getInstance();

        edtx_newpass =(EditText)findViewById(R.id.pass);

        change_passs = findViewById(R.id.button_change);



        change_passs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    dialog.setMessage("Changing password, please wait!");
                    dialog.show();
                    user.updatePassword(edtx_newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                dialog.dismiss();
                                Toast.makeText(Change.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                startActivity( new Intent(Change.this, MainActivity.class));
                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(Change.this, "Password could not be changed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
   }


}