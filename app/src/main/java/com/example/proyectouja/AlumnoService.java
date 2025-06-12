package com.example.proyectouja;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AlumnoService {
    @GET("/api/alumnos")
    Call<List<Alumno>> getAlumnos();
}


