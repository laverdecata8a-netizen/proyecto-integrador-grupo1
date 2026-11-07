package com.example.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service 
public class GeocodificacionService {

    private final HttpClient cliente = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String obtenerCoordenadas(String direccion) {

    String direccionCodificada = URLEncoder.encode(
        direccion,
        StandardCharsets.UTF_8
    );

    String url = "https://nominatim.openstreetmap.org/search"
            + "?q=" + direccionCodificada
            + "&format=json"
            + "&limit=1";

    HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent",
                        "GatoGo-ProyectoAcademico/1.0 (contacto: tata85742@gmail.com)")
                .GET()
                .build();


try {

    HttpResponse<String> respuesta = cliente.send(
            solicitud,
            HttpResponse.BodyHandlers.ofString()
    );

if (respuesta.statusCode() == 200) {

    JsonNode datos = mapper.readTree(respuesta.body());

    if (datos.isArray() && !datos.isEmpty()) {

        JsonNode ubicacion = datos.get(0);

        String latitud = ubicacion.get("lat").asText();
        String longitud = ubicacion.get("lon").asText();

        return "Latitud: " + latitud
                + ", Longitud: " + longitud;

    } else {
        return "No se encontró la dirección";
    }

} else {
    return "Error HTTP: " + respuesta.statusCode();
}

} catch (Exception e) {

    return "Error al consultar la API: " + e.getMessage();

}

    }
public JsonNode obtenerDireccion(double latitud, double longitud) {

    String url = "https://nominatim.openstreetmap.org/reverse"
            + "?lat=" + latitud
            + "&lon=" + longitud
            + "&format=json"
            + "&addressdetails=1";

    HttpRequest solicitud = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent",
                    "GatoGo-ProyectoAcademico/1.0 (contacto: tata85742@gmail.com)")
            .GET()
            .build();

    try {

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() == 200) {
            return mapper.readTree(respuesta.body());
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
 
}
