package com.tony.web;

import java.net.HttpURLConnection;
import java.net.URL;

public class WebChecker {
    /**
     * Realiza una petición GET rápida para verificar el estado de una URL.
     * @param urlString La dirección web completa (debe incluir http:// o https://)
     * @return El código de respuesta HTTP (200, 404, etc.) o -1 si falla la conexión.
     */
    public static int getStatus(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000); // 8 segundos de margen
            connection.setReadTimeout(8000);
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            return -1; // Indica que la web es inalcanzable
        }
    }
}
