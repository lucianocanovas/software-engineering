package com.colegio.service;

import com.colegio.model.*;
import com.colegio.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * =========================================================================================
 * COMPONENTE: DataInitializationService
 * =========================================================================================
 * Inicializa la base de datos relacional SQLite con un conjunto coherente de datos de prueba
 * al arrancar el sistema por primera vez:
 * - Institución Educativa (Colegio)
 * - Niveles y Grados Académicos (Composición)
 * - Comisiones y Aulas (Composición y Agregación)
 * - Docentes con credenciales de acceso (BCrypt)
 * - Alumnos matriculados
 * - Materias de la currícula escolar (Asociación)
 * - Evaluaciones y Notas por materia (Composición y Asociación)
 */
@Component
public class DataInitializationService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializationService.class);

    private final ColegioRepository colegioRepository;
    private final GradoRepository gradoRepository;
    private final AulaRepository aulaRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;
    private final AlumnoRepository alumnoRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final NotaRepository notaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializationService(ColegioRepository colegioRepository,
                                     GradoRepository gradoRepository,
                                     AulaRepository aulaRepository,
                                     MateriaRepository materiaRepository,
                                     ProfesorRepository profesorRepository,
                                     AlumnoRepository alumnoRepository,
                                     EvaluacionRepository evaluacionRepository,
                                     NotaRepository notaRepository,
                                     PasswordEncoder passwordEncoder) {
        this.colegioRepository = colegioRepository;
        this.gradoRepository = gradoRepository;
        this.aulaRepository = aulaRepository;
        this.materiaRepository = materiaRepository;
        this.profesorRepository = profesorRepository;
        this.alumnoRepository = alumnoRepository;
        this.evaluacionRepository = evaluacionRepository;
        this.notaRepository = notaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (profesorRepository.count() > 0) {
            log.info("Base de datos SQLite ya contiene registros. Omitiendo seed inicial.");
            return;
        }

        log.info(">>> Inicializando datos de prueba en la base de datos SQLite (colegio.db)...");

        // 1. Crear Colegio (Institución raíz)
        Colegio colegio = new Colegio("Colegio Nacional San Martín", "Av. Libertador 1250, Buenos Aires", "+54 11 4567-8900");
        colegioRepository.save(colegio);

        // 2. Crear Grados Escolares (Composición: Colegio -> Grado)
        Grado grado1Sec = new Grado("Secundaria", 1, colegio);
        Grado grado2Sec = new Grado("Secundaria", 2, colegio);
        Grado grado3Sec = new Grado("Secundaria", 3, colegio);
        gradoRepository.save(grado1Sec);
        gradoRepository.save(grado2Sec);
        gradoRepository.save(grado3Sec);

        // 3. Crear Aulas (Composición: Grado -> Aula)
        Aula aula1A = new Aula("A", "Mañana", 30, grado1Sec);
        Aula aula1B = new Aula("B", "Tarde", 28, grado1Sec);
        Aula aula2A = new Aula("A", "Mañana", 32, grado2Sec);
        aulaRepository.save(aula1A);
        aulaRepository.save(aula1B);
        aulaRepository.save(aula2A);

        // 4. Crear Materias Curriculares
        Materia matMatematica = new Materia("Matemáticas I", 5, "Álgebra elemental, geometría y funciones", grado1Sec);
        Materia matLengua = new Materia("Lengua y Literatura I", 4, "Comprensión lectora, gramática y análisis de textos", grado1Sec);
        Materia matHistoria = new Materia("Historia Argentina I", 3, "Procesos históricos del siglo XIX", grado1Sec);
        Materia matFisica = new Materia("Física Introductoria", 4, "Cinemática y leyes de Newton", grado2Sec);
        materiaRepository.save(matMatematica);
        materiaRepository.save(matLengua);
        materiaRepository.save(matHistoria);
        materiaRepository.save(matFisica);

        // 5. Crear Docente Principal de Demostración
        Profesor docenteDemo = new Profesor(
                "32456789",
                "Carlos",
                "Gómez",
                "profesor.demo@colegio.edu.ar",
                Sexo.MASCULINO,
                LocalDate.of(1985, 4, 12),
                passwordEncoder.encode("password123"),
                1001,
                "Ciencias Exactas y Matemáticas",
                850000.0
        );
        docenteDemo.agregarMateria(matMatematica);
        docenteDemo.agregarMateria(matFisica);
        docenteDemo.agregarAula(aula1A);
        docenteDemo.agregarAula(aula2A);
        profesorRepository.save(docenteDemo);

        // Segundo Docente
        Profesor docente2 = new Profesor(
                "28765432",
                "Mariana",
                "López",
                "mariana.lopez@colegio.edu.ar",
                Sexo.FEMENINO,
                LocalDate.of(1982, 9, 25),
                passwordEncoder.encode("password123"),
                1002,
                "Letras y Humanidades",
                820000.0
        );
        docente2.agregarMateria(matLengua);
        docente2.agregarMateria(matHistoria);
        docente2.agregarAula(aula1A);
        docente2.agregarAula(aula1B);
        profesorRepository.save(docente2);

        // 6. Crear Alumnos (Agregación: Aula -> Alumnos)
        Alumno alu1 = new Alumno(
                "45111222", "Lucas", "Benítez", "lucas.benitez@alumno.colegio.edu.ar",
                Sexo.MASCULINO, LocalDate.of(2009, 3, 15),
                passwordEncoder.encode("alumno123"), 202401, LocalDate.of(2024, 3, 1), aula1A
        );
        Alumno alu2 = new Alumno(
                "45222333", "Sofía", "Martínez", "sofia.martinez@alumno.colegio.edu.ar",
                Sexo.FEMENINO, LocalDate.of(2009, 7, 22),
                passwordEncoder.encode("alumno123"), 202402, LocalDate.of(2024, 3, 1), aula1A
        );
        Alumno alu3 = new Alumno(
                "45333444", "Mateo", "Fernández", "mateo.fernandez@alumno.colegio.edu.ar",
                Sexo.MASCULINO, LocalDate.of(2009, 11, 5),
                passwordEncoder.encode("alumno123"), 202403, LocalDate.of(2024, 3, 1), aula1B
        );
        Alumno alu4 = new Alumno(
                "44555666", "Camila", "Díaz", "camila.diaz@alumno.colegio.edu.ar",
                Sexo.FEMENINO, LocalDate.of(2008, 5, 18),
                passwordEncoder.encode("alumno123"), 202301, LocalDate.of(2023, 3, 1), aula2A
        );
        alumnoRepository.save(alu1);
        alumnoRepository.save(alu2);
        alumnoRepository.save(alu3);
        alumnoRepository.save(alu4);

        // 7. Crear Evaluaciones (Asociación con Materia y Docente)
        Evaluacion eval1 = new Evaluacion(
                "Primer Parcial de Álgebra",
                "Examen Parcial",
                LocalDate.now().minusWeeks(3),
                1.0,
                matMatematica,
                docenteDemo
        );
        Evaluacion eval2 = new Evaluacion(
                "Trabajo Práctico N° 1 - Ecuaciones",
                "Trabajo Práctico",
                LocalDate.now().minusWeeks(1),
                0.5,
                matMatematica,
                docenteDemo
        );
        evaluacionRepository.save(eval1);
        evaluacionRepository.save(eval2);

        // 8. Crear Notas (Composición: Evaluacion -> Notas, y Asociación con Alumno)
        Nota nota1 = new Nota(8.5, LocalDate.now().minusWeeks(3), "Excelente resolución analítica", eval1, alu1);
        Nota nota2 = new Nota(9.0, LocalDate.now().minusWeeks(3), "Impecable desarrollo y justificación", eval1, alu2);
        Nota nota3 = new Nota(7.0, LocalDate.now().minusWeeks(1), "Muy buen trabajo grupal", eval2, alu1);
        Nota nota4 = new Nota(6.5, LocalDate.now().minusWeeks(1), "Aprobado con observaciones mínimas", eval2, alu2);

        notaRepository.save(nota1);
        notaRepository.save(nota2);
        notaRepository.save(nota3);
        notaRepository.save(nota4);

        log.info(">>> [SEED EXITOSO] Datos iniciales cargados en SQLite colegio.db");
        log.info(">>> Usuario Docente de prueba: profesor.demo@colegio.edu.ar / password: password123");
    }
}

