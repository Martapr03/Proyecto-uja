package com.example.proyectouja;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

public class ContrasenaOlvidadaFragment extends Fragment {

    public ContrasenaOlvidadaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contrasena_olvidada, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView buttonAtras = view.findViewById(R.id.image_arrow);
        buttonAtras.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());

        EditText correoEditText = view.findViewById(R.id.editTextTextEmailAddress2);
        Button btnContinuar = view.findViewById(R.id.buttonContinuar2);

        btnContinuar.setOnClickListener(v -> {
            String correo = correoEditText.getText().toString().trim();

            if (TextUtils.isEmpty(correo) || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(getContext(), "Introduce un correo válido", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Correo enviado. Revisa tu bandeja de entrada.", Toast.LENGTH_LONG).show();

                            // Redirigir opcionalmente a inicio de sesión:
                            NavHostFragment.findNavController(this)
                                    .navigate(R.id.action_contrasenaOlvidadaFragment_to_inicioSesionFragment);
                        } else {
                            Toast.makeText(getContext(), "Error al enviar correo: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}

