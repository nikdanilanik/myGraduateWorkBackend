package dev.repositories.CustomerRepository;

import dev.vorstu.VorstuApplication;
import dev.vorstu.control.BaseController;
import dev.vorstu.entity.User;
import dev.vorstu.components.Initializer;
import dev.vorstu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = VorstuApplication.class)
@ActiveProfiles({"base-test", "local-test", "ValidationMessages.properties"})
@WithMockUser
@Slf4j
@ExtendWith({SpringExtension.class, MockitoExtension.class})
//@ContextConfiguration(classes = {VorstuApplication.class})
public class UserRepositoryTCAutoIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private Initializer initializer;
    @Autowired
    private BaseController baseController;
    @Autowired
    private UserRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testDataBase() throws Exception {
//        initializer.initial();
        Group groupOne = new Group("group1", 12L, "Кристафоренко Юрий Алексеевич");
        groupRepository.save(groupOne);
        studentRepository.save(new User(1L,"Белов Алексей Данилович", groupOne, "+791", "+111"));
        User student = baseController.getUserbyId(1L);
    }
}