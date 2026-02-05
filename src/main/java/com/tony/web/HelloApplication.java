package com.tony.web;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class HelloApplication extends Application {
    private Timer timer;
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void start(Stage stage) {
        // --- DISEÑO DE LA INTERFAZ ---
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1e1e1e;"); // Estilo Dark Mode
        Image icon = new Image(getClass().getResourceAsStream("/com/tony/web/icono.png"));
        stage.getIcons().add(icon);


        Label title = new Label("Monitor de Sitios Web");
        title.setStyle("-fx-text-fill: #00ff99; -fx-font-size: 22px; -fx-font-weight: bold;");

        // Entrada de URL
        TextField urlInput = new TextField("https://www.zaragoza.es");
        urlInput.setPromptText("https://ejemplo.com");
        urlInput.setPrefWidth(300);
        urlInput.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-border-color: #444444;");

        Button startBtn = new Button("Vigilar");
        startBtn.setStyle("-fx-background-color: #00ff99; -fx-text-fill: black; -fx-font-weight: bold; -fx-cursor: hand;");

        HBox inputRow = new HBox(10, urlInput, startBtn);

        // Indicadores visuales
        Circle statusCircle = new Circle(8, Color.GRAY);
        Label statusLabel = new Label("Listo para monitorear");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Segoe UI';");
        HBox statusRow = new HBox(10, statusCircle, statusLabel);

        // Lista de Logs (Historial)
        ListView<String> logList = new ListView<>();
        logList.setPrefHeight(250);
        logList.setStyle("-fx-control-inner-background: #2b2b2b; -fx-text-fill: #00ff99; -fx-font-family: 'Consolas';");

        root.getChildren().addAll(title, inputRow, statusRow, logList);

        // --- LÓGICA DEL BOTÓN ---
        startBtn.setOnAction(e -> {
            String url = urlInput.getText().trim();
            if (!url.startsWith("http")) {
                statusLabel.setText("Error: La URL debe empezar con http:// o https://");
                return;
            }

            if (timer != null) timer.cancel(); // Detener vigilancia anterior si existe

            logList.getItems().clear();
            logList.getItems().add(">>> Iniciando vigilancia: " + url);
            iniciarVigilancia(url, statusCircle, statusLabel, logList);
        });

        Scene scene = new Scene(root, 500, 450);
        stage.setTitle("Monitor Web");
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarVigilancia(String url, Circle circle, Label label, ListView<String> logs) {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int code = WebChecker.getStatus(url);
                String now = LocalTime.now().format(timeFormat);

                // IMPORTANTE: Los cambios en UI deben ir en Platform.runLater
                Platform.runLater(() -> {
                    if (code >= 200 && code < 400) {
                        circle.setFill(Color.LIME);
                        label.setText("ONLINE: " + url);
                        logs.getItems().add(0, "[" + now + "] OK (200)");
                    } else {
                        circle.setFill(Color.RED);
                        label.setText("FALLO: " + url + " (Cod: " + code + ")");
                        logs.getItems().add(0, "[" + now + "] ERROR: " + (code == -1 ? "Caída" : code));

                        // Alertar al usuario
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        registrarErrorEnArchivo(url, code);
                    }
                });
            }
        }, 0, 60000); // Chequeo cada 1 minuto
    }

    private void registrarErrorEnArchivo(String url, int code) {
        try (FileWriter fw = new FileWriter("uptime_errors.log", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[" + LocalDateTime.now() + "] URL: " + url + " | CÓDIGO: " + code);
        } catch (Exception e) {
            System.err.println("No se pudo escribir en el log.");
        }
    }

    @Override
    public void stop() {
        if (timer != null) timer.cancel();
    }

    public static void main(String[] args) {
        launch();
    }
}
