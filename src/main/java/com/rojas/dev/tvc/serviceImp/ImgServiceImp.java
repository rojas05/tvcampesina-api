package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.ComercianteRepository;
import com.rojas.dev.tvc.Repository.ProductoRepository;
import com.rojas.dev.tvc.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImgServiceImp implements ImgService {

    @Autowired
    ComercianteRepository comercianteRepository;

    @Autowired
    ProductoRepository productoRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public ResponseEntity<?> subirImagen(MultipartFile imagen, Integer id, String type) {
        try {
            String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + nombreArchivo);
            Files.createDirectories(path.getParent());
            Files.write(path, imagen.getBytes());

            // Retorna la URL pública (ajústala si estás en producción)
            String url = "http://192.168.0.109:8081/tvc/api/v1/img/get/" + nombreArchivo;

            if (Objects.equals(type, "comerciante")) {
                comercianteRepository.actualizarImagen(id,url);
            } else if (Objects.equals(type, "producto")) {
                productoRepository.actualizarImagen(id,url);
            }

            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir imagen");
        }
    }


    @Override
    public ResponseEntity<?> verImagen(String nombre) throws MalformedURLException {
        Path path = Paths.get(UPLOAD_DIR + nombre);
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        UrlResource resource = new UrlResource(path.toUri());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // O detecta el tipo
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
