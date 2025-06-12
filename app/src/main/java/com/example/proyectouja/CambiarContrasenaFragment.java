package com.example.proyectouja;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class CambiarContrasenaFragment extends Fragment {

    private ImageButton btnAtras;
    private EditText emailInput;
    private Button btnEnviar;

    public CambiarContrasenaFragment() {}

    public static CambiarContrasenaFragment newInstance(String param1, String param2) {
        CambiarContrasenaFragment fragment = new CambiarContrasenaFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ✅ Inicializa Firebase correctamente con contexto
        FirebaseApp.initializeApp(requireContext());

        View view = inflater.inflate(R.layout.fragment_cambiar_contrasena, container, false);

        // Inicialización de vistas
        btnAtras = view.findViewById(R.id.imageButton17);
        emailInput = view.findViewById(R.id.editTextTextEmailAddress);
        btnEnviar = view.findViewById(R.id.button4);

        // Botón atrás
        btnAtras.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        // Botón enviar correo
        btnEnviar.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Correo no válido", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Correo enviado. Revisa tu bandeja de entrada.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        return view;
    }
}
