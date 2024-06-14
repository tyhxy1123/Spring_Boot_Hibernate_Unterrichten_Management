package de.tustudent.cruddemo.dao;

import de.tustudent.cruddemo.entity.Course;
import de.tustudent.cruddemo.entity.Instructor;
import de.tustudent.cruddemo.entity.InstructorDetail;
import de.tustudent.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{
    private EntityManager entityManager;


    /**
     * By saving instructor, the instructor detail is also saved
     * because the instructor detail has been set as cascade in instructor class
     */
    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {
        Instructor ins = entityManager.find(Instructor.class, id);
        if(ins == null) return;
        for(Course c : ins.getCourses()) {
            c.setInstructor(null);
        }

        entityManager.remove(ins);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail detail = entityManager.find(InstructorDetail.class, id);

        if(detail == null) return;

        // remove the associated object reference
        // break bi-directional link
        detail.getInstructor().setInstructorDetail(null);
        entityManager.remove(detail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("from Course where instructor.id = :data", Course.class);
        query.setParameter("data", id);
        return query.getResultList();
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        TypedQuery<Instructor> query = entityManager.createQuery("from Instructor as i join fetch i.courses join fetch i.instructorDetail where i.id = :data", Instructor.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course c = entityManager.find(Course.class, id);
        if(c == null) return;

        entityManager.remove(c);
    }

    @Override
    @Transactional
    public void save(Course course) {

        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {

        TypedQuery<Course> query = entityManager.createQuery("select c from Course as c join fetch c.reviews where c.id = :data", Course.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteCourseAndReviewsByCourseId(int id) {
        Course c = entityManager.find(Course.class, id);

        if(c == null) return;

        entityManager.remove(c);
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course as c join fetch c.students where c.id=:data", Course.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    public Student findStudentAndItsCoursesByStudentId(int id) {
        TypedQuery<Student> query = entityManager.createQuery("select s from Student as s join fetch s.courses where s.id=:data", Student.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        Student s = entityManager.find(Student.class, id);
        entityManager.remove(s);
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
