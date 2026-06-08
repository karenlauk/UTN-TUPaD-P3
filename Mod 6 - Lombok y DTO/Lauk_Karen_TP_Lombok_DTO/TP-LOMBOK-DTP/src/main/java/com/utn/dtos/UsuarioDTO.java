package com.utn.dtos;
import com.utn.entities.Usuario;
public record UsuarioDTO(
        String nombre,
        String apellido,
        String mail
) {
    public static UsuarioDTO desdeEntidad(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMail()
        );
    }
}