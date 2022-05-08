package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class ServiceTestMock {
    @InjectMocks
    static Service service;
    @Mock
    static StudentXMLRepository mockStudentXMLRepo;
    @Mock
    static HomeworkXMLRepository mockHomeworkXMLRepo;
    @Mock
    static GradeXMLRepository mockGradeXMLRepo;
    @Mock
    static Validator<Student> studentValidator;
    @Mock
    static Validator<Homework> homeworkValidator;
    @Mock
    static Validator<Grade> gradeValidator;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    static void setUp() {
        studentValidator = mock(StudentValidator.class);
        homeworkValidator = mock(HomeworkValidator.class);
        gradeValidator = mock(GradeValidator.class);

        mockStudentXMLRepo = mock(StudentXMLRepository.class);
        mockHomeworkXMLRepo = mock(HomeworkXMLRepository.class);
        mockGradeXMLRepo = mock(GradeXMLRepository.class);

        service = new Service(mockStudentXMLRepo, mockHomeworkXMLRepo, mockGradeXMLRepo);
    }

    @Test
    void updateStudentShouldPass() {
        Student student = new Student("1","Kis Bela",512);
        when(mockStudentXMLRepo.update(any(Student.class))).thenReturn(student);
        int res = service.updateStudent("1","Kis Bela",522);
        verify(mockStudentXMLRepo).update(student);
        Assertions.assertEquals(1, res);
    }

    @Test
    void saveHomeWorkShouldPass() {
        Homework homework = new Homework("12","lab1",16,20);
        when(mockHomeworkXMLRepo.save(any(Homework.class))).thenReturn(homework);
        int result = service.saveHomework("12","lab1",16,20);
        verify(mockHomeworkXMLRepo).save(homework);
        Assertions.assertEquals(1,result);
    }

    @Test
    void deleteHomeworkShouldPass() {
        Homework homework = new Homework("12","lab1",16,20);
        when(mockHomeworkXMLRepo.delete(anyString())).thenReturn(homework);
        int result = service.deleteHomework("12");
        verify(mockHomeworkXMLRepo).delete("12");
        Assertions.assertEquals(1,result);
    }
}