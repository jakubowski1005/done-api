//package com.jakubowski.spring.done.repositories;
//
//import com.jakubowski.spring.done.entities.User;
//import com.jakubowski.spring.done.resources.Data;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.is;
//
////@RunWith(MockitoJUnitRunner.class)
////@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class UserRepositoryTest {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    TestEntityManager testEntityManager;
//
//    @Test
//    public void shouldFindUserByItsEmail() {
//
//        //given
//        User user = Data.getTestData();
//        String email = user.getEmail();
//        testEntityManager.persist(user);
//        testEntityManager.flush();
//
//        //when
//        Optional<User> found = userRepository.findByEmail(email);
//
//        //then
//        assertThat(user, is(equalTo(found)));
//    }
//
//    @Test
//    public void shouldFindUserByUsername() {
//
//    }
//
//    @Test
//    public void shouldFindUserByUsernameOrEmail() {
//
//    }
//
//    @Test
//    public void shouldReturnTrueIfUserExists() {
//
//    }
//
//    @Test
//    public void shouldReturnFalseIfUserDoesntExist() {
//
//    }
//}
