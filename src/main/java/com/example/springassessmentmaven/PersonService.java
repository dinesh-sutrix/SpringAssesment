package com.example.springassessmentmaven;


import com.example.springassessmentmaven.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }


    public List<Person> getAllUsers(int age) {
        List<Person> persons = (List<Person>) personRepository.findAll();
        return persons.stream().filter(person -> person.getAge() > age).collect(Collectors.toList());
    }

    public List<Person> getAllUsers() {
        List<Person> persons = (List<Person>) personRepository.findAll();
        return persons;
    }


    public Person getUserById(Long id) {
        return personRepository.findById(id).orElse(null);
    }


}
