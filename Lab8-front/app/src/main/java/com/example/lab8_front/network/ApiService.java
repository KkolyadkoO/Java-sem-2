package com.example.lab8_front.network;

import com.example.lab8_front.model.AuthResponse;
import com.example.lab8_front.model.Student;
import com.example.lab8_front.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth")
    Call<AuthResponse> register(@Body User body);

    @PUT("auth")
    Call<AuthResponse> login(@Body User body);

    @GET("students")
    Call<List<Student>> getStudents();

    @POST("students")
    Call<Void> createStudent(@Body Student s);

    @DELETE("students/{id}")
    Call<Void> deleteStudent(@Path("id") int id);

    @PUT("students/{id}")
    Call<Void> updateStudent(@Path("id") int id, @Body Student s);
}
