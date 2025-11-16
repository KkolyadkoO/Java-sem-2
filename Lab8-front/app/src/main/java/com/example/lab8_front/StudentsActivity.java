package com.example.lab8_front;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab8_front.model.Student;
import com.example.lab8_front.network.ApiService;
import com.example.lab8_front.network.RetrofitClient;
import com.example.lab8_front.ui.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsActivity extends AppCompatActivity {

    ListView listStudents;
    TextView sessionInfo;
    Button btnAdd;
    List<Student> currentList = new ArrayList<>();
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        listStudents = findViewById(R.id.listStudents);
        sessionInfo = findViewById(R.id.sessionInfoText);
        btnAdd = findViewById(R.id.btnAddStudent);

        loadStudents();

        btnAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadStudents() {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.getStudents().enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentList = response.body();
                    adapter = new StudentAdapter(StudentsActivity.this, currentList);
                    listStudents.setAdapter(adapter);

                    String counterHdr = response.headers().get("X-Request-Count");
                    if (counterHdr != null)
                        sessionInfo.setText("Session requests: " + counterHdr);
                } else {
                    Toast.makeText(StudentsActivity.this,
                            "Ошибка загрузки студентов", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(StudentsActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_edit_student, null);

        EditText editName = dialogView.findViewById(R.id.editName);
        EditText editGroup = dialogView.findViewById(R.id.editGroup);
        EditText editAvg = dialogView.findViewById(R.id.editAvg);

        new AlertDialog.Builder(this)
                .setTitle("Добавить студента")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String group = editGroup.getText().toString().trim();
                    double avg = Double.parseDouble(editAvg.getText().toString().trim());
                    addStudent(new Student(name, group, avg));
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void addStudent(Student s) {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.createStudent(s).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(StudentsActivity.this,
                            "Студент добавлен", Toast.LENGTH_SHORT).show();
                    loadStudents();
                } else {
                    Toast.makeText(StudentsActivity.this,
                            "Ошибка добавления (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
                String counterHdr = response.headers().get("X-Request-Count");
                if (counterHdr != null)
                    sessionInfo.setText("Session requests: " + counterHdr);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(StudentsActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}