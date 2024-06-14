package de.tustudent.cruddemo;

import de.tustudent.cruddemo.dao.AppDAO;
import de.tustudent.cruddemo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	private AppDAO dao;

	@Autowired
	public CruddemoApplication(AppDAO dao) {
		this.dao = dao;
	}

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return runner -> {
			// createCourseAndStudents();

			// findCourseAndStudentsByCourseId();

			// findStudentAndCoursesByStudentId();

			// addMoreCoursesForStudents();

			// deleteCourseById(dao);

			// deleteStudent();
		};
	}

	private void deleteStudent() {
		var id = 1;
		dao.deleteStudentById(id);
		System.out.println("Done!");
	}

	private void addMoreCoursesForStudents() {
		int id = 2;
		Student theStudent = dao.findStudentAndItsCoursesByStudentId(id);
		Course newCourse1 = new Course("Rubik's Cube - How to Speed Cube");
		Course newCourse2 = new Course("Atari 2600 - Game Development");

		theStudent.addCourse(newCourse1).addCourse(newCourse2);

		System.out.println("Updating student: " + theStudent);
		System.out.println("associated courses: " + theStudent.getCourses());
		dao.update(theStudent);
		System.out.println("Done!");
	}

	private void findStudentAndCoursesByStudentId() {
		int id = 1;
		Student s = dao.findStudentAndItsCoursesByStudentId(id);
		System.out.println(s);
		System.out.println(s.getCourses());
	}

	private void findCourseAndStudentsByCourseId() {
		int id = 10;

		Course c = dao.findCourseAndStudentsByCourseId(id);
		System.out.println(c);
		System.out.println(c.getStudents());
	}

	private void createCourseAndStudents() {
		Course course1 = new Course("Pacman - How To Score One Million Points");

		Student student1 = new Student("John", "Doe", "john@tustudent.com");
		Student student2 = new Student("Mary", "Public", "mary@tustudent.com");

		course1.addStudent(student1).addStudent(student2);

		System.out.println("Saving the course: " + course1);
		System.out.println("Associated students: " + course1.getStudents());

		dao.save(course1);

		System.out.println("Done!");
	}

	public void deleteCourseAndReviewsByCourseId() {
		int id = 11;

		System.out.println("Deleting course id: " + id);
		dao.deleteCourseAndReviewsByCourseId(id);

		System.out.println("Done!");
	}

	private void findCourseAndReviewsByCourseId() {
		int id = 11;
		Course c = dao.findCourseAndReviewsByCourseId(id);
		System.out.println("course: " + c);
		System.out.println("associated reviews: " + c.getReviews());
		System.out.println("Done!");
	}

	private void createCourseAndReviews() {
		// create courses
		Course c = new Course("Pacman - How To Score One Million Points");

		// create reviews
		// assign reviews to course
		c.addReview(new Review("Great course ... loved it!"));
		c.addReview(new Review("Cool course, job well done."));
		c.addReview(new Review("What a dumb course, you are an idiot!"));

		// save the course and reviews and leverage the cascade all
		System.out.println("Saving the course");
		System.out.println(c);
		System.out.println(c.getReviews());

		dao.save(c);

		System.out.println("Done!");
	}

	public void deleteCourseById(AppDAO dao) {
		int id = 10;

		System.out.println("Deleting course id: " + id);
		dao.deleteCourseById(id);
		System.out.println("Done!");
	}
	private void updateCourse(AppDAO dao) {
		int id = 10;

		System.out.println("Finding course id: " + id);
		Course course = dao.findCourseById(id);

		System.out.println("Updating course id: " + id);
		course.setTitle("Enjoy the Simple Things");

		dao.update(course);

		System.out.println("Done!");
	}

	private void updateInstructor(AppDAO dao) {
		int id = 1;

		// find the instructor
		Instructor instructor = dao.findInstructorById(id);

		// update the instructor
		instructor.setLastName("TESTER");

		dao.update(instructor);

		System.out.println("Done!");
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO dao) {
		int id = 1;
		Instructor i = dao.findInstructorByIdJoinFetch(id);
		System.out.println("Instructor: " + i);
		System.out.println("Courses: " + i.getCourses());
		System.out.println("Done!");
	}

	private void findCoursesForInstructorById(AppDAO dao) throws ClassNotFoundException {
		int instructorId = 1;
		Instructor instructor = dao.findInstructorById(instructorId);
		List<Course> courses = dao.findCoursesByInstructorId(instructorId);
		if(instructor == null) throw new ClassNotFoundException("No instructor is found!");
		instructor.setCourses(courses);

		System.out.println("instrucor: " + instructor);
		System.out.println("courses: " + instructor.getCourses());

	}

	private void findInstructorWithCourses(AppDAO dao) {
		int id = 1;
		System.out.println("Finding instructor id: " + id);

		Instructor instructor = dao.findInstructorById(id);

		System.out.println("instructor: " + instructor);
		System.out.println("the associated courses: " + instructor.getCourses());

		System.out.println("Done!");
	}

	private void createInstructorWithCourses(AppDAO dao) {
		Instructor susan = new Instructor("Susan", "Public", "susan.public@tustudent.de");
		InstructorDetail chadDetail = new InstructorDetail("http://www.youtube.com", "Video Games");

		susan.setInstructorDetail(chadDetail);

		Course course1 = new Course("Air Guitar - The Ultimate Guide");
		Course course2 = new Course("The Pinball Masterclass");

		susan.add(course1);
		susan.add(course2);

		System.out.println("Saving instructor: " + susan);
		System.out.println("The courses: " + susan.getCourses());
		dao.save(susan);

		System.out.println("Done!");

		/* Instructor madhu = new Instructor("Madhu", "Patel", "madhu@tustudent.de");
		InstructorDetail madhuDetail = new InstructorDetail("http://www.youtube.com", "Guitar");
		madhu.setInstructorDetail(madhuDetail);
		dao.save(madhu); */



	}


	private void deleteInstructorDetail(AppDAO dao) {
		int id = 20;
		System.out.println("Deleting instructordetail id: " + id);
		dao.deleteInstructorDetailById(id);
		System.out.println("Done!");
	}


	private void findInstructorDetail(AppDAO dao) {
		int id = 19;
		InstructorDetail detail = dao.findInstructorDetailById(id);

		System.out.println("Instructor detail: " + detail);

		System.out.println("The associated instructor: " + detail.getInstructor());
	}

	private void deleteInstructor(AppDAO dao) {
		int id = 1;
		System.out.println("Deleting instructor id: " + id);

		dao.deleteInstructorById(id);

		System.out.println("Done!");
	}

	private void findInstructor(AppDAO dao) {
		int id = 2;
		System.out.println("Finding instructor id: " + id);

		Instructor instructor = dao.findInstructorById(id);

		System.out.println("Instructor: " + instructor);
		System.out.println("the associate instructorDetail only: " + instructor.getInstructorDetail());

	}

	private void createInstructor(AppDAO dao) {
		Instructor chad = new Instructor("Chad", "Darby", "chad@tustudent.de");
		InstructorDetail chadDetail = new InstructorDetail("http://www.ch.com/c", "just code!");
		chad.setInstructorDetail(chadDetail);
		dao.save(chad);

		Instructor madhu = new Instructor("Madhu", "Patel", "madhu@tustudent.com");
		InstructorDetail madhuDetail = new InstructorDetail("http://www.youtube.com", "Guitar");
		madhu.setInstructorDetail(madhuDetail);
		dao.save(madhu);
	}
}
