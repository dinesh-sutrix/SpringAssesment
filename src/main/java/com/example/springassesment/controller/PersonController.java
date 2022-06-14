package com.example.springassesment.controller;

import com.example.springassesment.PersonService;
import com.example.springassesment.model.Person;
import com.example.springassesment.utils.CSV;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Person> getAll() {
        return personService.getAllUsers();
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public List<Person> getAll(@PathVariable int id) {
        return personService.getAllUsers(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person) {

     //   JsonSchemaValidator schemaValidator = JsonSchemaValidator.matchesJsonSchemaInClasspath("JSONSchema/person.schema.json");
      //  schemaValidator.matches(person);

    //    if(schemaValidator.matches(person)){
            Person person1 = personService.createPerson(person);
            return ResponseEntity.ok(person1);
     //   }else {
     //       return ResponseEntity.status(400).body(null);
     //   }

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
