package com.example.appmovil04;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference humedadRef, presionRef, velocidadRef, temperaturaRef;
    private TextView tvTemperatura, tvHumedad, tvPresion, tvVelocidad;
    private EditText etSetTemperatura, etSetHumedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        tvTemperatura = findViewById(R.id.valor_Temperatura);
        tvHumedad = findViewById(R.id.valor_Humedad);
        tvPresion = findViewById(R.id.valor_Presion);
        tvVelocidad = findViewById(R.id.valor_Velocidad);
        etSetTemperatura = findViewById(R.id.setvalor_Temperatura);
        etSetHumedad = findViewById(R.id.setvalor_Humedad);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        humedadRef = database.getReference("Sensores/Humedad");
        presionRef = database.getReference("Sensores/Presión");
        velocidadRef = database.getReference("Sensores/Velocidad");
        temperaturaRef = database.getReference("Sensores/Temperatura");


        temperaturaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvTemperatura.setText(snapshot.getValue().toString() + " °C");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        humedadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvHumedad.setText(snapshot.getValue().toString() + " %");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        presionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvPresion.setText(snapshot.getValue().toString() + " hPa");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        velocidadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvVelocidad.setText(snapshot.getValue().toString() + " km/h");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    public void clickBotonTemperatura(View view) {
        String valor = etSetTemperatura.getText().toString();
        if (!valor.isEmpty()) {
            temperaturaRef.setValue(Double.parseDouble(valor));
        }
    }

    public void clickBotonHumedad(View view) {
        String valor = etSetHumedad.getText().toString();
        if (!valor.isEmpty()) {
            humedadRef.setValue(Double.parseDouble(valor));
        }
    }
}