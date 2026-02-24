package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.UsuarioRepository;
import com.rojas.dev.tvc.dto.UpdateUserRequest;
import com.rojas.dev.tvc.entity.Usuario;
import com.rojas.dev.tvc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<?> obtenerUsuarioPorId(Integer id) {
        try {
            Usuario result = usuarioRepository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public void actualizarUsuario(Integer idUsuario, UpdateUserRequest request) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(request.getNombre());
        usuario.setIdentificacion(request.getIdentificacion());
        usuario.setTelefono(request.getTelefono());
        usuario.setMunicipio(request.getMunicipio());
        usuario.setDepartamento(request.getDepartamento());

        usuarioRepository.save(usuario);
    }

    @Override
    public void actualizarState(Integer idUsuario, String state) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setState(state);

        usuarioRepository.save(usuario);
    }
}
