package com.example.btl;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.User;

public class ForgotPassActivity extends AppCompatActivity {
    private EditText eName, eEmail, eNewPassword;
    private Button btSubmit;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        eName = findViewById(R.id.etName);
        eEmail = findViewById(R.id.etEmail);
        eNewPassword = findViewById(R.id.etNewPassword);
        btSubmit = findViewById(R.id.btSubmit);

        db = new SQLiteHelper(this);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eName.getText().toString();
                String email = eEmail.getText().toString();
                String newPassword = eNewPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(ForgotPassActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = db.getUserByEmail(email);
                if (user != null && user.getName().equals(name)) {
                    user.setPassword(newPassword);
                    db.updateUser(user);
                    Toast.makeText(ForgotPassActivity.this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgotPassActivity.this, "Tên hoặc email không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.com";
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.matches(emailPattern);
    }
}
