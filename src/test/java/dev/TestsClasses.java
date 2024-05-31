package dev;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import dev.vorstu.VorstuApplication;
import dev.vorstu.control.BaseController;
import dev.vorstu.entity.User;
import dev.vorstu.components.Initializer;
import dev.vorstu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@WithMockUser
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {VorstuApplication.class})
@Slf4j
public class TestsClasses {
    @MockBean
    private Initializer initializer;
    @MockBean
    private UserRepository studentRepository;
    @MockBean
    private BaseController baseController;
    @MockBean
    private GroupRepository groupRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    public void whenFindByName_thenReturnEmployee() throws Exception {
        Group groupOne = new Group("group1", 12L, "Кристафоренко Юрий Алексеевич");
        User student = new User(1L,"Белов Алексей Данилович", groupOne, "+791", "+111");

        Mockito.when(studentRepository.findByFio(Mockito.anyString())).thenReturn(student);
        // Это правильная версия фигни чуть выше
        // Но без "выше" это "правильно" не работает :/
        Mockito.when(studentRepository.save(Mockito.any())).thenAnswer(an -> an.getArguments()[0]);

        // when
        User found = studentRepository.findByFio(student.getFio());

        // then
        assertThat(found.getFio())
                .isEqualTo(student.getFio());

        // Так можно тестить отдельные методы
        Assertions.assertEquals(found.getFio(), student.getFio());
        Assertions.assertEquals(Type.String, found.getClass());
        Assertions.

        mvc.perform(get("/api/base/students/fio/test"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetStudentbyId() throws Exception {
        Group groupOne = new Group("group1", 12L, "Кристафоренко Юрий Алексеевич");

        List<User> listStudents = new ArrayList<>();

        listStudents.add(new User(1L,"Белов Алексей Данилович", groupOne, "+791", "+111"));
        listStudents.add(new User(2L,"Филиппов Севастьян Агафонович", groupOne, "+792", "+112"));
        listStudents.add(new User(3L, "Савина Дэнна Антоновна", groupOne, "+793", "+113"));
        listStudents.add(new User(4L, "Терентьев Исаак Адольфович", groupOne, "+794", "+114"));
        listStudents.add(new User(5L, "Авдеева Юнона Мэлсовна", groupOne, "+795", "+115"));
        listStudents.add(new User(6L, "Морозов Самуил Митрофанович", groupOne, "+796", "+116"));
        listStudents.add(new User(7L, "Мамонтов Святослав Серапионович", groupOne, "+797", "+117"));
        listStudents.add(new User(8L, "Морозов Ираклий Петрович", groupOne, "+798", "+118"));
        listStudents.add(new User(9L, "Красильников Назарий Николаевич", groupOne, "+799", "+119"));
        listStudents.add(new User(10L, "Гусева Пелагея Романовна", groupOne, "+800", "+120"));
        listStudents.add(new User(11L, "Белов Вадим Валентинович", groupOne, "+801", "+121"));
        listStudents.add(new User(12L, "Попова Зара Серапионовна", groupOne, "+802", "+122"));

//        Mockito.when(studentRepository.save(listStudents.get(3))).thenAnswer(an -> an.getArguments()[0]);
        Mockito.when(studentRepository.findById(4L)).thenReturn(Optional.of(listStudents.get(3)));
        Mockito.when(baseController.getAllStudents()).thenReturn(listStudents);

        final Optional<User> found = studentRepository.findById(4L);

        if (found.isPresent()) {
            assertThat(found.get())
                    .isEqualTo(listStudents.get(3));
        } else {
            throw new RuntimeException("student with id: " + listStudents.get(3).getId() + "was not found");
        }

        mvc.perform(get("/api/base/students/3"))
                .andExpect(status().isOk())
                .andReturn();
    }

//    @Tag("fast")
//    @Test
//    public void testAdd() {
//        assertEquals(42, Integer.sum(19, 23));
//    }

}
