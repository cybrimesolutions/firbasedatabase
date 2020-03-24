package com.example.firbasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private FirebaseFirestore objectFirebaseFirestore;

    private Dialog objectDialog;
    private EditText nameform,phoneform,emailform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        objectFirebaseFirestore=FirebaseFirestore.getInstance();
        objectDialog=new Dialog(this);

        objectDialog.setContentView(R.layout.wait_page);
        nameform=findViewById(R.id.nameform);

        phoneform=findViewById(R.id.phoneform);
        emailform=findViewById(R.id.emailform);
    }

    public void addValues(View v)
    {
        try
        {
            if(!nameform.getText().toString().isEmpty() && !phoneform.getText().toString().isEmpty()
                    && !emailform.getText().toString().isEmpty()) {
                objectDialog.show();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("phone_number", phoneform.getText().toString());

                objectMap.put("email_details", emailform.getText().toString());
                objectFirebaseFirestore.collection("NewData")
                        .document(nameform.getText().toString()).set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "M", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            objectDialog.dismiss();
            Toast.makeText(this, "addValues:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
