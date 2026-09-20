package com.colegio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * =========================================================================================
 * DTO: CambioPasswordDTO
 * =========================================================================================
 * Satisface el requisito funcional:
 * "además el sistema debe tener la posibilidad de cambiar la contraseña."
 *
 * Captura la contraseña anterior para su validación contra el hash en base de datos,
 * junto con la nueva contraseña y su confirmación.
 */
public class CambioPasswordDTO {

    @NotBlank(message = "Debe ingresar su contraseña actual")
    private String passwordActual;

    @NotBlank(message = "La nueva contraseña es requerida")
    @Size(min = 6, message = "La nueva contraseña debe tener como mínimo 6 caracteres")
    private String nuevoPassword;

    @NotBlank(message = "Debe confirmar la nueva contraseña")
    private String confirmPassword;

    public CambioPasswordDTO() {
    }

    // Getters y Setters
    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

