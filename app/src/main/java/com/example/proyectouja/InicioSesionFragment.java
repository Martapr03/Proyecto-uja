package com.example.proyectouja;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioSesionFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public InicioSesionFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio_sesion, container, false);
    }

    private void setupPasswordToggle(EditText editText) {
        Drawable iconoMostrar = ContextCompat.getDrawable(requireContext(), R.drawable.eye);
        Drawable iconoOcultar = ContextCompat.getDrawable(requireContext(), R.drawable.eye_off);
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconoMostrar, null);

        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = editText.getCompoundDrawables()[DRAWABLE_END];
                if (drawableEnd != null && event.getRawX() >= (editText.getRight() - drawableEnd.getBounds().width())) {
                    boolean esVisible = (editText.getInputType() & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                    int cursorPos = editText.getSelectionStart();
                    if (esVisible) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconoMostrar, null);
                    } else {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconoOcultar, null);
                    }
                    editText.setSelection(cursorPos);
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        view.findViewById(R.id.image_arrow).setOnClickListener(v ->
                navController.navigate(R.id.action_inicioSesionFragment_to_rolFragment));

        view.findViewById(R.id.textView10).setOnClickListener(v ->
                navController.navigate(R.id.action_inicioSesionFragment_to_registrarseFragment));

        view.findViewById(R.id.textView11).setOnClickListener(v ->
                navController.navigate(R.id.action_inicioSesionFragment_to_contrasenaOlvidadaFragment));

        EditText emailEditText = view.findViewById(R.id.editTextEmailAddress);
        EditText passwordEditText = view.findViewById(R.id.editTextNumberPassword);
        Button loginButton = view.findViewById(R.id.buttonIniciarSesion);

        setupPasswordToggle(passwordEditText);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.endsWith("@red.ujaen.es") && !email.endsWith("@ujaen.es")) {
                Toast.makeText(getContext(), "Solo se permiten correos @red.ujaen.es o @ujaen.es", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                if (!user.isEmailVerified()) {
                                    Toast.makeText(getContext(), "Debes verificar tu correo electrónico antes de iniciar sesión.", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                String uid = user.getUid();
                                db.collection("usuarios").document(uid).get()
                                        .addOnSuccessListener(document -> {
                                            if (document.exists()) {
                                                String rol = document.getString("rol");

                                                if (rol == null) {
                                                    Toast.makeText(getContext(), "Rol no definido. Contacta con soporte.", Toast.LENGTH_LONG).show();
                                                    return;
                                                }

                                                SharedPreferences prefs = requireActivity().getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
                                                prefs.edit().putString("correo", email).putString("rol", rol).apply();

                                                if ("profesor".equalsIgnoreCase(rol)) {
                                                    //navController.navigate(R.id.action_inicioSesionFragment_to_pantallaPrincipalProfesorFragment);
                                                } else if ("alumno".equalsIgnoreCase(rol)) {
                                                    //navController.navigate(R.id.action_inicioSesionFragment_to_pantallaPrincipalAlumnoFragment);
                                                } else {
                                                    Toast.makeText(getContext(), "Rol desconocido: " + rol, Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e ->
                                                Toast.makeText(getContext(), "Error al obtener datos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            Toast.makeText(getContext(), "Error de autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}

