package com.example.lab8_front.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab8_front.R;
import com.example.lab8_front.model.Student;
import com.example.lab8_front.network.ApiService;
import com.example.lab8_front.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends ArrayAdapter<Student> {

    private final List<Student> students;
    private final Context ctx;

    public StudentAdapter(Context context, List<Student> list) {
        super(context, 0, list);
        this.students = list;
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = (convertView != null)
                ? convertView
                : LayoutInflater.from(ctx).inflate(R.layout.item_student, parent, false);

        TextView txtName = v.findViewById(R.id.txtName);
        Button btnEdit = v.findViewById(R.id.btnEdit);
        Button btnDelete = v.findViewById(R.id.btnDelete);

        Student s = students.get(position);
        txtName.setText(s.getName() + " (" + s.getGroup() + ") " + s.getAvgGrade());

        btnDelete.setOnClickListener(view -> deleteStudent(s));
        btnEdit.setOnClickListener(view -> editStudent(s));

        return v;
    }

    private void deleteStudent(Student s) {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.deleteStudent(s.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    students.remove(s);
                    notifyDataSetChanged();
                    Toast.makeText(ctx, "Удален: " + s.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Ошибка удаления (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editStudent(Student s) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View dialogView = inflater.inflate(R.layout.dialog_edit_student, null);

        EditText editName  = dialogView.findViewById(R.id.editName);
        EditText editGroup = dialogView.findViewById(R.id.editGroup);
        EditText editAvg   = dialogView.findViewById(R.id.editAvg);

        editName.setText(s.getName());
        editGroup.setText(s.getGroup());
        editAvg.setText(String.valueOf(s.getAvgGrade()));

        new AlertDialog.Builder(ctx)
                .setTitle("Редактировать")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    s.setName(editName.getText().toString());
                    s.setGroup(editGroup.getText().toString());
                    s.setAvgGrade(Double.parseDouble(editAvg.getText().toString()));
                    updateStudentOnServer(s);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void updateStudentOnServer(Student s) {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.updateStudent(s.getId(), s).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ctx, "Обновлен: " + s.getName(), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(ctx, "Ошибка обновления ("+response.code()+")", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}