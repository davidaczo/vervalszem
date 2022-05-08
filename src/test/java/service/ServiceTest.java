package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    static Service service;


    //pl: findall -> legkerem az osszeset hozzaadok egyet s ujra lekerem
    //talaljunk ki uj dolgokat es ugy teszteljuk ne csak a visszateretesi ertekkel

    @BeforeEach
    public void doItBeforeAll() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"3"})
    void deleteHomeworkShouldPass(String id) {
        Integer returnedValued = service.deleteHomework(id);
        Assertions.assertEquals(0, returnedValued);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "", " "})
    void deleteHomeworkShouldFail(String id) {
        Integer returnedValued = service.deleteHomework(id);
        Assertions.assertEquals(0, returnedValued);
    }

    @Test
    void updateStudentShouldFail() {
        new Student("1","Kis Bela",512);
        Integer returnedValued = service.updateStudent("-1","Kis Bela",522);
        Assertions.assertEquals(0,returnedValued);
    }

    @Test
    void saveHomeWorkShouldFail() {
        int result = service.saveHomework("-1234","lab1",12,8);
        Assertions.assertEquals(1,result);
    }

    @Test
    void findAllWithCreateShouldPass() {
        Iterable<Student> list = service.findAllStudents();
        int InitalSize = 0;
        for(Student s : list) {
            InitalSize++;
        }
        System.out.println(list);
        service.saveStudent("10","Kis Bela",512);
        Iterable<Student> list2 = service.findAllStudents();
        int afterSize = 0;
        for(Student s : list2) {
            afterSize++;
        }
        System.out.println(list);
        assertNotEquals(InitalSize, afterSize);
    }
    @Test
    void deleteStudentShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> {
           service.deleteStudent(null);
        });
    }

}