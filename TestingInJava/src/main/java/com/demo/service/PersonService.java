package com.demo.service;

import com.demo.entities.Person;
import com.demo.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Annotation
@Service
public class PersonService {

    @Autowired
    private PersonRepo repo;

    public List<Person> getAllPerson()
    {
        return this.repo.findAll();
    }

    public PersonService(PersonRepo repo)
    {
        // this keyword refers to current instance
        this.repo = repo;
    }
}