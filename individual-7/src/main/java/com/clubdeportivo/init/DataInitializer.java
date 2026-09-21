package com.clubdeportivo.init;

import com.clubdeportivo.entity.*;
import com.clubdeportivo.entity.enums.*;
import com.clubdeportivo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * =========================================================================================
 * COMPONENTE: DataInitializer (Carga Inicial Demostrativa)
 * =========================================================================================
 * Precarga datos realistas al arrancar la aplicación para permitir la evaluación y navegación
 * inmediata de todos los módulos solicitados:
 * - Credenciales de acceso de prueba (ADMIN, OPERADOR, SOCIO).
 * - Puntos de acceso físicos y molinetes.
 * - Actividades y profesores con matricula.
 * - Socios titulares con fotografías de rostro y sus respectivos Grupos Familiares con Adherentes.
 * - Cuotas emitidas en estados PENDIENTE, VENCIDA y PAGADA.
 * - Pagos efectuados con los tres medios requeridos: Efectivo, Transferencia y Mercado Pago.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PuntoDeAccesoRepository puntoRepository;
    private final ActividadRepository actividadRepository;
    private final ProfesorRepository profesorRepository;
    private final SocioRepository socioRepository;
    private final GrupoFamiliarRepository grupoFamiliarRepository;
    private final FamiliarAdherenteRepository familiarRepository;
    private final CuotaRepository cuotaRepository;
    private final PagoRepository pagoRepository;
    private final RegistroAccesoRepository registroAccesoRepository;
    private final PasswordEncoder passwordEncoder;

    // Foto de perfil demo en formato SVG data URI para no depender de archivos externos
    private static final String FOTO_CARLOS = "data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120' viewBox='0 0 120 120'><circle cx='60' cy='60' r='58' fill='%231976d2'/><circle cx='60' cy='45' r='22' fill='%23ffffff'/><ellipse cx='60' cy='95' rx='36' ry='22' fill='%23ffffff'/><text x='60' y='65' font-size='11' text-anchor='middle' fill='%231976d2' font-family='Arial' font-weight='bold'>CARLOS</text></svg>";
    private static final String FOTO_MARIANA = "data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120' viewBox='0 0 120 120'><circle cx='60' cy='60' r='58' fill='%23e91e63'/><circle cx='60' cy='45' r='22' fill='%23ffffff'/><ellipse cx='60' cy='95' rx='36' ry='22' fill='%23ffffff'/><text x='60' y='65' font-size='11' text-anchor='middle' fill='%23e91e63' font-family='Arial' font-weight='bold'>MARIANA</text></svg>";
    private static final String FOTO_ROBERTO = "data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120' viewBox='0 0 120 120'><circle cx='60' cy='60' r='58' fill='%234caf50'/><circle cx='60' cy='45' r='22' fill='%23ffffff'/><ellipse cx='60' cy='95' rx='36' ry='22' fill='%23ffffff'/><text x='60' y='65' font-size='11' text-anchor='middle' fill='%234caf50' font-family='Arial' font-weight='bold'>ROBERTO</text></svg>";
    private static final String FOTO_MATEO = "data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120' viewBox='0 0 120 120'><circle cx='60' cy='60' r='58' fill='%23ff9800'/><circle cx='60' cy='45' r='22' fill='%23ffffff'/><ellipse cx='60' cy='95' rx='36' ry='22' fill='%23ffffff'/><text x='60' y='65' font-size='11' text-anchor='middle' fill='%23ff9800' font-family='Arial' font-weight='bold'>MATEO</text></svg>";

    public DataInitializer(UsuarioRepository usuarioRepository,
                           PuntoDeAccesoRepository puntoRepository,
                           ActividadRepository actividadRepository,
                           ProfesorRepository profesorRepository,
                           SocioRepository socioRepository,
                           GrupoFamiliarRepository grupoFamiliarRepository,
                           FamiliarAdherenteRepository familiarRepository,
                           CuotaRepository cuotaRepository,
                           PagoRepository pagoRepository,
                           RegistroAccesoRepository registroAccesoRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.puntoRepository = puntoRepository;
        this.actividadRepository = actividadRepository;
        this.profesorRepository = profesorRepository;
        this.socioRepository = socioRepository;
        this.grupoFamiliarRepository = grupoFamiliarRepository;
        this.familiarRepository = familiarRepository;
        this.cuotaRepository = cuotaRepository;
        this.pagoRepository = pagoRepository;
        this.registroAccesoRepository = registroAccesoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return; // Ya inicializado
        }

        // 1. Usuarios del Sistema
        Usuario admin = new Usuario("admin", passwordEncoder.encode("admin123"), "Administrador General", RolUsuario.ROLE_ADMIN);
        Usuario operador = new Usuario("operador", passwordEncoder.encode("operador123"), "Recepción Molinetes", RolUsuario.ROLE_OPERADOR);
        Usuario socioUser = new Usuario("socio", passwordEncoder.encode("socio123"), "Carlos Gómez (Portal Socio)", RolUsuario.ROLE_SOCIO);
        usuarioRepository.save(admin);
        usuarioRepository.save(operador);
        usuarioRepository.save(socioUser);

        // 2. Puntos de Acceso Físicos
        PuntoDeAcceso p1 = new PuntoDeAcceso("Molinete Principal - Acceso Central", "Sede Central - Hall", EstadoPuntoAcceso.OPERATIVO, "192.168.1.50");
        PuntoDeAcceso p2 = new PuntoDeAcceso("Molinete Natatorio Climatizado", "Sector Piscinas", EstadoPuntoAcceso.OPERATIVO, "192.168.1.51");
        PuntoDeAcceso p3 = new PuntoDeAcceso("Portón Vehicular Norte", "Estacionamiento", EstadoPuntoAcceso.OPERATIVO, "192.168.1.52");
        PuntoDeAcceso p4 = new PuntoDeAcceso("Molinete Canchas de Tenis", "Sector Polideportivo", EstadoPuntoAcceso.EN_MANTENIMIENTO, "192.168.1.53");
        puntoRepository.save(p1);
        puntoRepository.save(p2);
        puntoRepository.save(p3);
        puntoRepository.save(p4);

        // 3. Actividades Deportivas
        Actividad a1 = new Actividad("ACT-NAT", "Natación Libre y Competitiva", "Lunes a Viernes 08:00 - 20:00", 35);
        Actividad a2 = new Actividad("ACT-TEN", "Tenis Inicial y Avanzado", "Martes y Jueves 16:00 - 19:00", 15);
        Actividad a3 = new Actividad("ACT-FUT", "Escuelita de Fútbol", "Sábados 09:00 - 12:00", 50);
        Actividad a4 = new Actividad("ACT-FIT", "Musculación y Crossfit", "Lunes a Sábados 07:00 - 22:00", 60);
        actividadRepository.save(a1);
        actividadRepository.save(a2);
        actividadRepository.save(a3);
        actividadRepository.save(a4);

        // 4. Profesores (Herencia multinivel: Profesor -> Empleado -> Persona)
        Profesor profNatacion = new Profesor("28999111", "Lucas", "Martínez", LocalDate.of(1983, 5, 14),
                "+54 11 4455-1234", "prof.lucas@clubdeportivo.com", "LEG-501", "Profesor Natación", "Natación y Salvamento", "MAT-BA-4512");
        profNatacion.setFotografia(new Fotografia(FOTO_ROBERTO, "image/svg+xml", 1200L));
        profNatacion.asignarActividad(a1);
        profesorRepository.save(profNatacion);

        // 5. Socios Titulares, Fotografías y Grupos Familiares
        // Socio 1: Carlos Gómez (Al día)
        Socio socio1 = new Socio("32456789", "Carlos", "Gómez", LocalDate.of(1986, 7, 20),
                "+54 11 5566-7788", "carlos.gomez@gmail.com", "SOC-1001", CategoriaSocio.ACTIVO);
        socio1.setFotografia(new Fotografia(FOTO_CARLOS, "image/svg+xml", 1500L));
        socioRepository.save(socio1);

        GrupoFamiliar gf1 = new GrupoFamiliar("GF-FAM-GOMEZ", socio1, "Familia Gómez Fernández");
        grupoFamiliarRepository.save(gf1);
        socio1.setGrupoFamiliar(gf1);

        // Adherentes de Carlos Gómez
        FamiliarAdherente adh1 = new FamiliarAdherente("34111222", "Laura", "Fernández", LocalDate.of(1988, 3, 10),
                "+54 11 5566-7799", "laura.fernandez@gmail.com", Parentesco.CONYUGE);
        adh1.setGrupoFamiliar(gf1);
        familiarRepository.save(adh1);

        FamiliarAdherente adh2 = new FamiliarAdherente("52333444", "Mateo", "Gómez", LocalDate.of(2014, 11, 25), // 11 años: menor de edad
                "", "", Parentesco.HIJO);
        adh2.setFotografia(new Fotografia(FOTO_MATEO, "image/svg+xml", 1100L));
        adh2.setGrupoFamiliar(gf1);
        familiarRepository.save(adh2);

        gf1.agregarIntegrante(adh1);
        gf1.agregarIntegrante(adh2);

        // Socio 2: Mariana Morales (Con cuota vencida para pruebas de morosidad y bloqueo)
        Socio socio2 = new Socio("35987654", "Mariana", "Morales", LocalDate.of(1990, 9, 12),
                "+54 11 3322-1100", "mariana.morales@hotmail.com", "SOC-1002", CategoriaSocio.ACTIVO);
        socio2.setFotografia(new Fotografia(FOTO_MARIANA, "image/svg+xml", 1400L));
        socioRepository.save(socio2);

        GrupoFamiliar gf2 = new GrupoFamiliar("GF-FAM-MORALES", socio2, "Familia Morales");
        grupoFamiliarRepository.save(gf2);
        socio2.setGrupoFamiliar(gf2);

        // Socio 3: Roberto Benítez (Vitalicio)
        Socio socio3 = new Socio("18123456", "Roberto", "Benítez", LocalDate.of(1955, 2, 8),
                "+54 11 9988-7766", "roberto.benitez@yahoo.com.ar", "SOC-1003", CategoriaSocio.VITALICIO);
        socio3.setFotografia(new Fotografia(FOTO_ROBERTO, "image/svg+xml", 1300L));
        socioRepository.save(socio3);

        GrupoFamiliar gf3 = new GrupoFamiliar("GF-FAM-BENITEZ", socio3, "Familia Benítez");
        grupoFamiliarRepository.save(gf3);
        socio3.setGrupoFamiliar(gf3);

        // 6. Cuotas Familiares y Demostración de Medios de Pago (Efectivo, Transferencia, Mercado Pago)

        // Cuota 1: Carlos Gómez - Mes Anterior PAGADA con EFECTIVO
        Cuota cuota1 = new Cuota("2026-08", new BigDecimal("15000.00"), BigDecimal.ZERO, LocalDate.now().minusMonths(1).withDayOfMonth(10), socio1, gf1);
        cuota1.setEstado(EstadoCuota.PAGADA);
        cuota1.setFechaPago(LocalDate.now().minusMonths(1).withDayOfMonth(5));
        cuotaRepository.save(cuota1);

        PagoEfectivo medioEfectivo = new PagoEfectivo(new BigDecimal("1000.00"), "CAJA-01", "Martín Recepcionista");
        Pago pagoEfectivo = new Pago("REC-20260805-EF01", new BigDecimal("14000.00"), cuota1, medioEfectivo, socio1);
        cuota1.setPago(pagoEfectivo);
        pagoRepository.save(pagoEfectivo);

        // Cuota 2: Carlos Gómez - Mes Actual PAGADA con MERCADO PAGO
        Cuota cuota2 = new Cuota("2026-09", new BigDecimal("16500.00"), BigDecimal.ZERO, LocalDate.now().plusDays(15), socio1, gf1);
        cuota2.setEstado(EstadoCuota.PAGADA);
        cuota2.setFechaPago(LocalDate.now().minusDays(2));
        cuotaRepository.save(cuota2);

        PagoMercadoPago medioMP = new PagoMercadoPago("MP-894521742", "PREF-CLUB-0926", "https://mercadopago.com.ar/qr/demo", "accredited");
        Pago pagoMP = new Pago("REC-20260918-MP02", new BigDecimal("16500.00"), cuota2, medioMP, socio1);
        cuota2.setPago(pagoMP);
        pagoRepository.save(pagoMP);

        // Cuota 3: Roberto Benítez - Mes Anterior PAGADA con TRANSFERENCIA BANCARIA
        Cuota cuota3 = new Cuota("2026-08", new BigDecimal("12000.00"), BigDecimal.ZERO, LocalDate.now().minusMonths(1).withDayOfMonth(10), socio3, gf3);
        cuota3.setEstado(EstadoCuota.PAGADA);
        cuota3.setFechaPago(LocalDate.now().minusMonths(1).withDayOfMonth(8));
        cuotaRepository.save(cuota3);

        PagoTransferencia medioTransferencia = new PagoTransferencia("0110599540000012345678", "0000003100010002000304", "Banco Galicia", "TRF-99881122", "HASH-F4A8C19B");
        Pago pagoTRF = new Pago("REC-20260808-TR03", new BigDecimal("12000.00"), cuota3, medioTransferencia, socio3);
        cuota3.setPago(pagoTRF);
        pagoRepository.save(pagoTRF);

        // Cuota 4: Mariana Morales - Cuota VENCIDA (Mes Anterior impaga con recargo por mora) -> Deja al socio como NO AL DÍA
        Cuota cuotaVencida = new Cuota("2026-08", new BigDecimal("15000.00"), new BigDecimal("2500.00"), LocalDate.now().minusDays(25), socio2, gf2);
        cuotaVencida.setEstado(EstadoCuota.VENCIDA);
        cuotaRepository.save(cuotaVencida);

        // Cuota 5: Mariana Morales - Cuota Mes Actual PENDIENTE de pago
        Cuota cuotaPendiente = new Cuota("2026-09", new BigDecimal("16500.00"), new BigDecimal("2000.00"), LocalDate.now().plusDays(5), socio2, gf2);
        cuotaPendiente.setEstado(EstadoCuota.PENDIENTE);
        cuotaRepository.save(cuotaPendiente);

        // Cuota 6: Roberto Benítez - Cuota Mes Actual PENDIENTE de pago (dentro de término)
        Cuota cuotaRoberto = new Cuota("2026-09", new BigDecimal("12000.00"), new BigDecimal("1500.00"), LocalDate.now().plusDays(10), socio3, gf3);
        cuotaRoberto.setEstado(EstadoCuota.PENDIENTE);
        cuotaRepository.save(cuotaRoberto);

        // 7. Registros de Acceso Previos
        RegistroAcceso reg1 = new RegistroAcceso();
        reg1.setPersona(socio1);
        reg1.setPuntoDeAcceso(p1);
        reg1.setTipoMovimiento(TipoMovimiento.ENTRADA);
        reg1.setFechaHoraEntrada(LocalDateTime.now().minusHours(3));
        reg1.setFechaHoraSalida(LocalDateTime.now().minusHours(1).minusMinutes(15));
        reg1.setObservacion("Ingreso habitual con cuota al día");
        registroAccesoRepository.save(reg1);

        RegistroAcceso reg2 = new RegistroAcceso();
        reg2.setPersona(adh2); // Mateo Gómez
        reg2.setPuntoDeAcceso(p2);
        reg2.setTipoMovimiento(TipoMovimiento.ENTRADA);
        reg2.setFechaHoraEntrada(LocalDateTime.now().minusMinutes(50));
        reg2.setObservacion("Entrada al natatorio infantil con acompañante");
        registroAccesoRepository.save(reg2);

        RegistroAcceso reg3 = new RegistroAcceso();
        reg3.setPersona(profNatacion);
        reg3.setPuntoDeAcceso(p1);
        reg3.setTipoMovimiento(TipoMovimiento.ENTRADA);
        reg3.setFechaHoraEntrada(LocalDateTime.now().minusHours(4));
        reg3.setObservacion("Ingreso laboral de profesor");
        registroAccesoRepository.save(reg3);
    }
}
