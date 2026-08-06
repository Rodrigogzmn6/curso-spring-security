package com.rodrigoguzman.school_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.CourseRequestDTO;
import com.rodrigoguzman.school_project.dto.CourseResponseDTO;
import com.rodrigoguzman.school_project.dto.ProfessorDTO;
import com.rodrigoguzman.school_project.dto.StudentDTO;
import com.rodrigoguzman.school_project.model.Course;
import com.rodrigoguzman.school_project.model.Professor;
import com.rodrigoguzman.school_project.model.Student;
import com.rodrigoguzman.school_project.repository.ICourseRepository;
import com.rodrigoguzman.school_project.repository.IProfessorRepository;
import com.rodrigoguzman.school_project.repository.IStudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {

    final ICourseRepository repository;
    final IProfessorRepository professorRepository;
    final IStudentRepository studentRepository;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO course) {
        Optional<Professor> professor = Optional.empty();

        if (course.professor() != null) {
            professor = professorRepository
                    .findByDni(course.professor().getDni());
        }

        Set<Student> students = new HashSet<>();

        for (Student student : course.students()) {
            Optional<Student> studentOptional = studentRepository.findByDni(student.getDni());

            if (studentOptional.isPresent()) {
                students.add(studentOptional.get());
            }
        }

        repository.save(Course.builder()
                .name(course.name())
                .professor(professor.isPresent() ? professor.get() : null)
                .students(students)
                .build());

        return CourseResponseDTO.builder()
                .name(course.name())
                .professor(ProfessorDTO.builder()
                        .name(professor.isPresent() ? professor.get().getName() : null)
                        .dni(professor.isPresent() ? professor.get().getDni() : null)
                        .build())
                .students(students.stream()
                        .map(student -> StudentDTO.builder()
                                .name(student.getName())
                                .dni(student.getDni())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Optional<CourseResponseDTO> findCourseById(Long id) {
        return repository.findById(id).map(foundCourse -> CourseResponseDTO.builder()
                .name(foundCourse.getName())
                .professor(foundCourse.getProfessor() != null ? ProfessorDTO.builder()
                        .name(foundCourse.getProfessor().getName())
                        .dni(foundCourse.getProfessor().getDni())
                        .build() : ProfessorDTO.builder().build())
                .students(foundCourse.getStudents().stream()
                        .map(student -> StudentDTO.builder()
                                .name(student.getName())
                                .dni(student.getDni())
                                .build())
                        .collect(Collectors.toSet()))
                .build());
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO course) {
        Course foundCourse = repository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.name() != null && !course.name().isEmpty()) {
            foundCourse.setName(course.name());
        }

        if (course.professor() != null) {
            professorRepository.findByDni(course.professor().getDni())
                    .ifPresent(foundCourse::setProfessor);
        }

        if (course.students() != null) {
            Set<Student> students = new HashSet<>();
            for (Student student : course.students()) {
                studentRepository.findByDni(student.getDni()).ifPresent(students::add);
            }
            foundCourse.setStudents(students);
        }

        repository.save(foundCourse);

        return CourseResponseDTO.builder()
                .name(foundCourse.getName())
                .professor(foundCourse.getProfessor() != null ? ProfessorDTO.builder()
                        .name(foundCourse.getProfessor().getName())
                        .dni(foundCourse.getProfessor().getDni())
                        .build() : ProfessorDTO.builder().build())
                .students(foundCourse.getStudents().stream()
                        .map(student -> StudentDTO.builder()
                                .name(student.getName())
                                .dni(student.getDni())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void deleteCourse(Long id) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        repository.deleteById(id);
    }

    @Override
    public List<CourseResponseDTO> findAllCourses() {
        return repository.findAll().stream()
                .map(course -> CourseResponseDTO.builder()
                        .name(course.getName())
                        .professor(course.getProfessor() != null ? ProfessorDTO.builder()
                                .name(course.getProfessor().getName())
                                .dni(course.getProfessor().getDni())
                                .build() : ProfessorDTO.builder().build())
                        .students(course.getStudents().stream()
                                .map(student -> StudentDTO.builder()
                                        .name(student.getName())
                                        .dni(student.getDni())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toList());
    }

}
