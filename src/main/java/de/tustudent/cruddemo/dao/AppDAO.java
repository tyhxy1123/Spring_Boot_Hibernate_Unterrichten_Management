package de.tustudent.cruddemo.dao;

import de.tustudent.cruddemo.entity.Course;
import de.tustudent.cruddemo.entity.Instructor;
import de.tustudent.cruddemo.entity.InstructorDetail;
import de.tustudent.cruddemo.entity.Student;

import java.util.List;

public interface AppDAO {

    void save(Instructor instructor);
    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail findInstructorDetailById(int id);

    void deleteInstructorDetailById(int id);

    List<Course> findCoursesByInstructorId(int id);

    Instructor findInstructorByIdJoinFetch(int id);

    void update(Instructor instructor);

    Course findCourseById(int id);
    void update(Course course);

    void deleteCourseById(int id);

    void save(Course course);

    Course findCourseAndReviewsByCourseId(int id);

    void deleteCourseAndReviewsByCourseId(int id);

    Course findCourseAndStudentsByCourseId(int id);

    Student findStudentAndItsCoursesByStudentId(int id);

    void update(Student student);

    void deleteStudentById(int id);

}
