package com.example.lab8_front;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab8_front.model.AuthResponse;
import com.example.lab8_front.model.Student;
import com.example.lab8_front.model.User;
import com.example.lab8_front.network.ApiService;
import com.example.lab8_front.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText loginEdit, passEdit;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEdit = findViewById(R.id.loginEdit);
        passEdit = findViewById(R.id.passEdit);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> authorize(true));
        btnRegister.setOnClickListener(v -> authorize(false));
    }

    private void authorize(boolean loginMode) {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        User body = new User(loginEdit.getText().toString(),
                passEdit.getText().toString());

        Call<AuthResponse> call = loginMode ?
                api.login(body) : api.register(body);
        Log.d("URL", call.request().url().toString());
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> resp) {
                if (resp.isSuccessful()) {
                    // Извлекаем JSESSIONID из Set‑Cookie
                    String cookie = resp.headers().get("set-cookie");
                    if (cookie != null && cookie.contains("JSESSIONID")) {
                        String id = cookie.split("JSESSIONID=")[1].split(";")[0];
                        RetrofitClient.setSessionId(id);
                    }

                    Toast.makeText(MainActivity.this,
                            "Success: " + resp.body().getStatus(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, StudentsActivity.class));
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}