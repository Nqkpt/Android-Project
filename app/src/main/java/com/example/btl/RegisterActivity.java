package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText eName, eEmail, ePassword;
    private Button btRegister;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eName = findViewById(R.id.Name_text);
        eEmail = findViewById(R.id.Email_text);
        ePassword = findViewById(R.id.Password_text);
        btRegister = findViewById(R.id.sign_up_button);

        db = new SQLiteHelper(this);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eName.getText().toString();
                String email = eEmail.getText().toString();
                String password = ePassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, email, password);
                    db.addUser(user);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
