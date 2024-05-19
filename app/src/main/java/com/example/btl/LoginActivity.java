package com.example.btl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dal.SQLiteHelper;
import com.example.btl.model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText eEmail, ePassword;
    private Button btLogin, btRegister, btForgotPassword;
    private SQLiteHelper db;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eEmail = findViewById(R.id.editTextEmail);
        ePassword = findViewById(R.id.editTextPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        btForgotPassword = findViewById(R.id.btForgotPassword);

        db = new SQLiteHelper(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eEmail.getText().toString();
                String password = ePassword.getText().toString();
                if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = db.getUserByEmail(email);

                if (user != null && user.getPassword().equals(password)) {
                    //Log.d("Khanh", "Condition met: user is not null and password matches");
                    saveLoginStatus(true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //Log.d(TAG, "Starting MainActivity");
                    finish();
                } else {
                    //Log.d(TAG, "Password does not match");
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.com";
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.matches(emailPattern);
    }


}
