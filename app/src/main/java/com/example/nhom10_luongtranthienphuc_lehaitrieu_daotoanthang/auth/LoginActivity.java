package com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.MainActivity;
import com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextView txtNewMem;
    EditText editUser, editPass;
    Button btnSignIn;
    FirebaseAuth fAuth;
    FirebaseDatabase fDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUser = findViewById(R.id.edit_user_log_in);
        editPass = findViewById(R.id.edit_pass_log_in);
        btnSignIn = findViewById(R.id.btnLogin);
        txtNewMem = findViewById(R.id.txtNewMember);
        fAuth = FirebaseAuth.getInstance();
        fDB = FirebaseDatabase.getInstance();
        //đăng kí
        txtNewMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //đăng nhập
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editUser.getText().toString();
                String pass = editPass.getText().toString();
                if (editUser.getText().toString().isEmpty()){
                    editUser.setError("Vui lòng nhập tên tài khoản");
                    return;
                }
                if (editPass.getText().toString().isEmpty()){
                    editPass.setError("Vui lòng nhập mật khẩu");
                    return;
                }
                if (editPass.length()<6){
                    editPass.setError("Mật khẩu không được dưới 6 kí tự");
                    return;
                }
                fAuth.signInWithEmailAndPassword(user,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
    }
}