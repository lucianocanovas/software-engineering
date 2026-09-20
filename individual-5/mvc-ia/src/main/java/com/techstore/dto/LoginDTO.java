package com.techstore.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * =============================================================================
 * CAPA DTO: LoginDTO (Data Transfer Object)
 * =============================================================================
 * Encapsula las credenciales remitidas desde el formulario de inicio de sesión
 * protegiendo a la entidad de dominio Usuario de sobre-exposición de atributos.
 */
public class LoginDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

