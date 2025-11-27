package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> subirImagen(@RequestParam("img") MultipartFile imagen,
                                         @PathVariable("id") Integer id,
                                         @RequestParam("type") String type
                                         ) {
        return imgService.subirImagen(imagen,id,type);
    }

    // Servir la imagen
    @GetMapping("/get/{nombre}")
    public ResponseEntity<?> verImagen(@PathVariable String nombre) throws IOException {
        return imgService.verImagen(nombre);
    }
}

