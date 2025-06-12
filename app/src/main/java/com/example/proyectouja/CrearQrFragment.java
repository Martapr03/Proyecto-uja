package com.example.proyectouja;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CrearQrFragment extends DialogFragment {

    private static final String ARG_QR_CONTENT = "qr_content";
    private String contenidoQR;

    public static CrearQrFragment newInstance(String contenidoQR) {
        CrearQrFragment fragment = new CrearQrFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QR_CONTENT, contenidoQR);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_qr, container, false);

        ImageView qrImageView = view.findViewById(R.id.imageView3);
        ImageButton buttonCerrar = view.findViewById(R.id.imageButton13);

        // Obtener el contenido del QR desde los argumentos
        if (getArguments() != null) {
            contenidoQR = getArguments().getString(ARG_QR_CONTENT);
        }

        // Verifica y genera el código QR
        if (contenidoQR != null && !contenidoQR.trim().isEmpty()) {
            Bitmap qrBitmap = generarQR(contenidoQR.trim());
            if (qrBitmap != null) {
                qrImageView.setImageBitmap(qrBitmap);
            }
        }


        buttonCerrar.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_crearQrFragment_to_pantallaPrincipalProfesorFragment2)
        );

        return view;
    }

    private Bitmap generarQR(String contenido) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    contenido,
                    BarcodeFormat.QR_CODE,
                    600, // width
                    600  // height
            );
            return new BarcodeEncoder().createBitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}