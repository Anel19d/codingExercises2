package com.demo.repo;

import com.demo.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepoTest {
    @Autowired
    private PersonRepo personRepo;

    @Test
    void isPersonExitsById() {
        Person person = new Person(1001, "Amiya", "Bhubaneswar");
        personRepo.save(person);
        Boolean actualResult = personRepo.isPersonExitsById(1001);
        assertThat(actualResult).isTrue();
    }
}