package com.example.springassesment;

import com.example.springassesment.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Long> {}
