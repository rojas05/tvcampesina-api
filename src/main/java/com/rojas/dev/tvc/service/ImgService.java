package com.rojas.dev.tvc.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;


public interface ImgService {

     ResponseEntity<?> subirImagen(MultipartFile imagen,Integer id, String type);


     ResponseEntity<?> verImagen(String nombre) throws MalformedURLException;

}
