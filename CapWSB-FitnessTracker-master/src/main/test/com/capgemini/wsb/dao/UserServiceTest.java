package com.capgemini.wsb.dao;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class UserServiceTest {

    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {

        UserRepository userRepository = new UserRepository() {
            @Override
            public List<User> findAll(Sort sort) {
                return List.of();
            }

            @Override
            public Page<User> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends User> S save(S entity) {
                return null;
            }

            @Override
            public <S extends User> List<S> saveAll(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public Optional<User> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public List<User> findAll() {
                return List.of();
            }

            @Override
            public List<User> findAllById(Iterable<Long> longs) {
                return List.of();
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(User entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends User> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public void deleteAllInBatch(Iterable<User> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public User getOne(Long aLong) {
                return null;
            }

            @Override
            public User getById(Long aLong) {
                return null;
            }

            @Override
            public User getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends User> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends User> List<S> findAll(Example<S> example) {
                return List.of();
            }

            @Override
            public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
                return List.of();
            }

            @Override
            public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends User> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends User> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }

            @Override
            public List<User> findByBirthdateBefore(LocalDate date) {
                return List.of();
            }

            @Override
            public List<User> findByEmailContainingIgnoreCase(String email) {
                return List.of();
            }
        };
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testFindAllUsers() {
        // Given
        User user1 = new User("John", "Doe", LocalDate.of(1990, 5, 15), "john@example.com");
        User user2 = new User("Jane", "Smith", LocalDate.of(1985, 8, 25), "jane@example.com");

        // When
        userService.saveUser(user1);
        userService.saveUser(user2);
        List<User> result = userService.findAllUsers();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(user1, user2);
    }

    @Test
    public void testFindUserById() {
        // Given
        User user = new User("John", "Doe", LocalDate.of(1990, 5, 15), "john@example.com");
        Long userId = userService.saveUser(user).getId();

        // When
        Optional<User> result = userService.findUserById(userId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testSaveUser() {
        // Given
        User user = new User("John", "Doe", LocalDate.of(1990, 5, 15), "john@example.com");

        // When
        User result = userService.saveUser(user);

        // Then
        assertThat(result).isEqualTo(user);
    }

}
