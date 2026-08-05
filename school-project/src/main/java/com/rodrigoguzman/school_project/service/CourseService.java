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

        Optional<Professor> professor = professorRepository
                .findByDni(course.professor().getDni());

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
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO course) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<CourseResponseDTO> findAllCourses() {
        // TODO Auto-generated method stub
        return null;
    }

}
