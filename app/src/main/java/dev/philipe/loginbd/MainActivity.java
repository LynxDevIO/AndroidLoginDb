package dev.philipe.loginbd;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dev.philipe.loginbd.database.DatabaseConfig;
import dev.philipe.loginbd.database.DatabaseConnection;
import dev.philipe.loginbd.database.PostgreConfig;

public class MainActivity extends AppCompatActivity {
    private TextView connectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        connectionStatus = findViewById(R.id.lbDBConnectionStatus);
        connectionStatus.setText("Tentando conectar ao banco de dados...");

        // Executar a conexão com o banco de dados em uma thread separada
        new Thread(() -> {
            try {
                DatabaseConfig postgres = new PostgreConfig();
                Connection con = DatabaseConnection.getInstance(postgres).getConnection();
                
                if (con != null) {
                    try (Statement st = con.createStatement()) {
                        st.executeUpdate("create table if not exists user_ (id serial primary key, name varchar(255), password varchar(255))");
                        
                        // Atualizar a UI na thread principal
                        runOnUiThread(() -> {
                            connectionStatus.setText("Conectado ao Banco de Dados");
                            connectionStatus.setTextColor(Color.GREEN);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                
                // Atualizar a UI na thread principal
                runOnUiThread(() -> {
                    connectionStatus.setText("Erro ao conectar: " + e.getMessage());
                    connectionStatus.setTextColor(Color.RED);
                });
            }
        }).start();
    }
}