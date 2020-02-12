package com.hexactive.proscheduler.LoginModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hexactive.proscheduler.MainModule.MainActivity;
import com.hexactive.proscheduler.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextInputEditText edittext_email,edittext_password;
    private CheckBox remember_me,show_password;
    private ProgressDialog dialog;
    private TextView forgot_textview, contact_textview;
    private Button login_btn;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        SharedPreferences sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);

        edittext_email.setText(sp.getString("loginuname",""));
        edittext_password.setText(sp.getString("loginpassword",""));
        remember_me.setChecked(sp.getBoolean("remembermestatus",false));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn=findViewById(R.id.login_button);
        edittext_email=findViewById(R.id.edittext_email);
        edittext_password=findViewById(R.id.edittext_password);
        show_password=findViewById(R.id.show_password);
        remember_me=findViewById(R.id.remember_me);
        dialog = new ProgressDialog(new ContextThemeWrapper(LoginActivity.this, R.style.MyProgressDialog));
        forgot_textview=findViewById(R.id.forgot_textview);


        forgot_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edittext_email.getText().toString()==null)
                {
                    Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email=edittext_email.getText().toString()+"@cb.students.amrita.edu";
                    mAuth.sendPasswordResetEmail("hmttlp9775@smlmail.com");
                }

            }
        });

        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edittext_password.setTransformationMethod(null);
                } else {
                    edittext_password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),edittext_email.getText().toString()+" "+edittext_password.getText().toString(),Toast.LENGTH_SHORT ).show();

                if(edittext_email.getText().toString()==null||edittext_password.getText().toString()==null)
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog.setMessage("Logging in...");
                    dialog.show();
                    String username=edittext_email.getText().toString();
                    String email=edittext_email.getText().toString()+"@cb.students.amrita.edu";
                    String password=edittext_password.getText().toString();


                    if(remember_me.isChecked())
                    {
                        SharedPreferences sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("loginuname", username);
                        edit.putString("loginpassword",password);
                        edit.putBoolean("remembermestatus",true);
                        edit.apply();
                    }
                    else
                    {
                        SharedPreferences sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("loginuname", "");
                        edit.putString("loginpassword","");
                        edit.putBoolean("remembermestatus",false);
                        edit.apply();
                    }
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        currentUser=mAuth.getCurrentUser();
                                        Log.d("Login",currentUser.getEmail());
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Login","Failed Login");
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
//                    if (dialog.isShowing()) {
//                        dialog.dismiss();
//                    }
                }

            }
        });
    }
}
