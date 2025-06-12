package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class LeerQrFragment extends Fragment {

    private ActivityResultLauncher<ScanOptions> qrLauncher;
    private EditText editTextCodigo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leer_qr, container, false);

        editTextCodigo = new EditText(requireContext());

        Button btnCerrar = view.findViewById(R.id.btnCerrar);
        Button btnLeerQR = new Button(requireContext());

        // Configuración del escáner QR
        qrLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                String contenidoQR = result.getContents();
                editTextCodigo.setText(contenidoQR);
                procesarInsignia(contenidoQR);
            } else {
                Toast.makeText(getContext(), "No se leyó ningún QR", Toast.LENGTH_SHORT).show();
            }
        });

        // Lanzar escáner
        btnLeerQR.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Escanea el QR del profesor");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setCameraId(0);
            qrLauncher.launch(options);
        });

        btnCerrar.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void procesarInsignia(String contenido) {
        if (contenido.contains("insignia=")) {
            Toast.makeText(getContext(), "Insignia recibida: " + contenido, Toast.LENGTH_LONG).show();
            // Aquí puedes extraer y guardar en BD
        } else {
            Toast.makeText(requireContext(), "QR no válido", Toast.LENGTH_SHORT).show();
        }
    }
}

