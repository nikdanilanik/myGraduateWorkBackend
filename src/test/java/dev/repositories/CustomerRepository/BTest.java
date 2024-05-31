package dev.repositories.CustomerRepository;

import dev.vorstu.VorstuApplication;
import dev.vorstu.control.BaseController;
import dev.vorstu.entity.*;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.repositories.UserAccessRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@SpringBootTest(classes = VorstuApplication.class)
@RunWith(SpringRunner.class)
//@SpringBootTest
@ContextConfiguration(initializers = {BTest.Initializer.class})
public class BTest {
    @Autowired
    private UserRepository studentRepository;
    @Autowired
    private BaseController baseController;
    @Autowired
    private UserAccessRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.3")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgreSQLContainer.start();

            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationNative_ThenModifyMatchingUsers() {
        // same as above
        System.out.println("31245");
        //this.initial();
        Group groupOne = new Group("group1", 12L, "Кристафоренко Юрий Алексеевич");
        Group groupTwo = new Group("group2", 0L, "Криренко Дед Алексеевич");

        groupRepository.save(groupOne);
        groupRepository.save(groupTwo);

        List<User> studentRepository2;

        studentRepository.save(new User(1L,"Белов Алексей Данилович",           groupOne, "+791", "+111"));
        studentRepository.save(new User(2L,"Филиппов Севастьян Агафонович",     groupOne, "+792", "+112"));
        studentRepository.save(new User(3L, "Савина Дэнна Антоновна",            groupOne, "+793", "+113"));
        studentRepository.save(new User(4L, "Терентьев Исаак Адольфович",        groupOne, "+794", "+114"));
        studentRepository.save(new User(5L, "Авдеева Юнона Мэлсовна",            groupOne, "+795", "+115"));
        studentRepository.save(new User(6L, "Морозов Самуил Митрофанович",       groupOne, "+796", "+116"));
        studentRepository.save(new User(7L, "Мамонтов Святослав Серапионович",   groupOne, "+797", "+117"));
        studentRepository.save(new User(8L, "Морозов Ираклий Петрович",          groupOne, "+798", "+118"));
        studentRepository.save(new User(9L, "Красильников Назарий Николаевич",   groupOne, "+799", "+119"));
        studentRepository.save(new User(10L, "Гусева Пелагея Романовна",          groupOne, "+800", "+120"));
        studentRepository.save(new User(11L, "Белов Вадим Валентинович",          groupOne, "+801", "+121"));
        studentRepository.save(new User(12L, "Попова Зара Серапионовна",          groupOne, "+802", "+122"));
        studentRepository2 = studentRepository.findAll();
        User student = studentRepository.findByFio("Белов Алексей Данилович");
        User studen2 = baseController.getUserbyId(1L);
        User studen3 = baseController.getUserbyId(21L);
        User student1 = studentRepository.findByFio("Белов Алексей Данилович");
    }
}
