package com.example.proyectouja;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseFragment extends Fragment {

    private FirebaseAuth mAuth;
    private NavController navController;

    private EditText nombreEditText, correoEditText, contrasenaEditText, confirmarContrasenaEditText;
    private Button continuarButton;

    public RegistrarseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrarse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);

        nombreEditText = view.findViewById(R.id.editTextNombre);
        correoEditText = view.findViewById(R.id.editTextCorreo);
        contrasenaEditText = view.findViewById(R.id.editTextContrasena);
        confirmarContrasenaEditText = view.findViewById(R.id.editTextConfirmarContrasena);
        continuarButton = view.findViewById(R.id.botonContinuar);

        setupPasswordToggle(contrasenaEditText);
        setupPasswordToggle(confirmarContrasenaEditText);

        continuarButton.setOnClickListener(v -> {
            String nombre = nombreEditText.getText().toString().trim();
            String correo = correoEditText.getText().toString().trim();
            String contrasena = contrasenaEditText.getText().toString().trim();
            String confirmar = confirmarContrasenaEditText.getText().toString().trim();

            if (!validarCampos(nombre, correo, contrasena, confirmar)) return;

            if (!correo.endsWith("@red.ujaen.es") && !correo.endsWith("@ujaen.es")) {
                Toast.makeText(getContext(), "Solo se permiten correos @red.ujaen.es o @ujaen.es", Toast.LENGTH_LONG).show();
                return;
            }

            continuarButton.setEnabled(false);

            mAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        continuarButton.setEnabled(true);
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();
                            guardarUsuarioEnFirestore(uid, nombre, correo);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getContext(), "Este correo ya está en uso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        view.findViewById(R.id.image_arrow).setOnClickListener(v ->
                navController.navigate(R.id.action_registrarseFragment_to_inicioSesionFragment));

        view.findViewById(R.id.textView19).setOnClickListener(v ->
                navController.navigate(R.id.action_registrarseFragment_to_inicioSesionFragment));
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

    private boolean validarCampos(String nombre, String correo, String contrasena, String confirmar) {
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) ||
                TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(confirmar)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!esNombreValido(nombre)) {
            Toast.makeText(getContext(), "El nombre solo debe contener letras y espacios", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(getContext(), "Correo no válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!contrasena.equals(confirmar)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!esContrasenaSegura(contrasena)) {
            Toast.makeText(getContext(), "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas, números y símbolos", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean esNombreValido(String nombre) {
        return nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü\\s]+$");
    }

    private boolean esContrasenaSegura(String contrasena) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.#\\-])[A-Za-z\\d@$!%*?&.#\\-]{8,}$";
        return contrasena.matches(regex);
    }

    private void guardarUsuarioEnFirestore(String uid, String nombre, String correo) {
        String rol = correo.endsWith("@ujaen.es") ? "profesor" : "alumno";

        Map<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("nombre", nombre);
        datosUsuario.put("correo1", correo);
        datosUsuario.put("rol", rol);

        FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document(uid)
                .set(datosUsuario)
                .addOnSuccessListener(aVoid -> {
                    mAuth.getCurrentUser().sendEmailVerification()
                            .addOnSuccessListener(unused ->
                                    Toast.makeText(getContext(), "Verifica tu correo electrónico para activar tu cuenta", Toast.LENGTH_LONG).show()
                            );
                    navController.navigate(R.id.action_registrarseFragment_to_inicioSesionFragment);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Error al guardar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}








