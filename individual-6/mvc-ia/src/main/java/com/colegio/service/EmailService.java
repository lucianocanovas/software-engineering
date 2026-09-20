package com.colegio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * =========================================================================================
 * SERVICIO DE MENSAJERÍA ELECTRÓNICA: EmailService
 * =========================================================================================
 * Satisface el requerimiento:
 * "Al registrarse un decente en el sistema debe enviar un correo de bienvenida al correo personal."
 *
 * Utiliza JavaMailSender para despachar el correo electrónico.
 * Incorpora manejo de resiliencia y tolerancia a fallos: en caso de que no exista conexión
 * a internet o que el servidor SMTP sea un entorno de prueba local sin credenciales productivas,
 * captura la excepción, registra el contenido íntegro en consola/logs y permite que la
 * transacción de registro del docente continúe exitosamente sin interrumpir la experiencia de usuario.
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    /**
     * Envía el correo de bienvenida al correo personal del docente recién registrado.
     *
     * @param destinatario   Correo personal del docente (que funge como usuario).
     * @param nombreCompleto Nombre y apellido del docente.
     * @param legajo         Número de legajo asignado.
     */
    public void enviarCorreoBienvenida(String destinatario, String nombreCompleto, Integer legajo) {
        log.info("=======================================================================");
        log.info(">>> DISPARANDO NOTIFICACIÓN: Correo de Bienvenida a Docente");
        log.info(">>> Destinatario: {}", destinatario);
        log.info(">>> Docente: {}", nombreCompleto);
        log.info(">>> Legajo: {}", legajo);
        log.info("=======================================================================");

        String asunto = "¡Bienvenido/a al Sistema de Gestión Escolar!";
        String cuerpo = String.format(
            "Estimado/a Profesor/a %s,\n\n" +
            "Le damos la más cálida bienvenida al Sistema de Gestión Escolar.\n\n" +
            "Su cuenta docente ha sido creada con éxito con los siguientes datos:\n" +
            " - Usuario / Correo de Acceso: %s\n" +
            " - Legajo Institucional: %d\n\n" +
            "A partir de este momento, podrá iniciar sesión en la plataforma para:\n" +
            " • Consultar sus materias y aulas pedagógicas asignadas.\n" +
            " • Gestionar el registro de alumnos por comisiones.\n" +
            " • Programar evaluaciones académicas.\n" +
            " • Asentar calificaciones y promedios por materia.\n" +
            " • Modificar su contraseña en cualquier momento desde su panel de perfil.\n\n" +
            "Atentamente,\n" +
            "Dirección Académica y Coordinación Escolar\n",
            nombreCompleto, destinatario, legajo
        );

        try {
            if (mailSender != null) {
                SimpleMailMessage mensaje = new SimpleMailMessage();
                mensaje.setTo(destinatario);
                mensaje.setSubject(asunto);
                mensaje.setText(cuerpo);
                mensaje.setFrom("notificaciones.colegio@educacion.edu.ar");
                mailSender.send(mensaje);
                log.info(">>> [CORREO ENVIADO CON ÉXITO] Mensaje SMTP despachado a {}", destinatario);
            } else {
                log.warn(">>> [SIMULACIÓN LOCAL] JavaMailSender no configurado. El correo se emite en logs de prueba:\n{}", cuerpo);
            }
        } catch (Exception e) {
            log.warn(">>> [ALERTA SMTP] No fue posible enviar el correo vía servidor SMTP externo ({}). " +
                     "Se registra simulación en consola para garantizar funcionamiento offline.", e.getMessage());
            log.info(">>> [CONTENIDO DEL MENSAJE DE BIENVENIDA]:\n{}", cuerpo);
        }
    }
}

