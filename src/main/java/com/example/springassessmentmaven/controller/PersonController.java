package com.example.springassessmentmaven.controller;

import com.example.springassessmentmaven.PersonService;
import com.example.springassessmentmaven.model.Person;
import com.example.springassessmentmaven.utils.CSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@RestController
@RequestMapping("/person")
public class PersonController {


    @Autowired
    private PersonService personService;

    @RequestMapping(value="list",method = RequestMethod.GET)
    public List<Person> getAll() {
        return personService.getAllUsers();
    }

    @RequestMapping(value = "/listAboveAge/{age}",method = RequestMethod.GET)
    public List<Person> getAll(@PathVariable String age) {
        return personService.getAllUsers(Integer.parseInt(age));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable String id) {
        return ResponseEntity.ok(personService.getUserById(Long.parseLong(id)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person) {
            Person person1 = personService.createPerson(person);
            return ResponseEntity.ok(person1);
    }

    @RequestMapping(value="csv",method = RequestMethod.GET)
    public Map < String, List<Map< String, String >> > loadFromCSV() {
        try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/file.csv"));) {
            CSV csv = new CSV(true, ',', in );
            List < String > fieldNames = null;
            if (csv.hasNext()) fieldNames = new ArrayList< >(csv.next());
            List <Map< String, String >> list = new ArrayList < > ();
            Properties prop = new Properties();
            try {
                prop.load(Files.newInputStream(Paths.get("src/main/resources/config.properties")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (csv.hasNext()) {
                List < String > x = csv.next();
                Map < String, String > obj = new LinkedHashMap< >();
                for (int i = 0; i < fieldNames.size(); i++) {
                    obj.put(prop.getProperty("configs."+i+".json_attr"), x.get(i));
                }
                list.add(obj);
            }
            Map < String, List<Map< String, String >> > personObject = new LinkedHashMap< >();
            personObject.put("person",list);
          //  ObjectMapper mapper = new ObjectMapper();
          //  mapper.enable(SerializationFeature.INDENT_OUTPUT);
          //  mapper.writeValue(System.out, personObject);
            return personObject;
        }catch (Exception exception){
            System.out.println(exception.toString());
            return null;
        }

    }


}
