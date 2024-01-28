package com.example.restapidemos.rest;

import com.example.restapidemos.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        Student student1 = new Student("Max", "Colonqo");

        theStudents.add(new Student("John", "Grey"));
        theStudents.add(student1);
        theStudents.add(new Student("Angello", "Cabron"));

    }


    @GetMapping("/students")
    public List<Student> getStudent() {
        return theStudents;

    }

    @GetMapping("/students/{studentID}")
    public Student getStudenByID(@PathVariable int studentID){

//        if(studentID>=theStudents.size() || (studentID<0)){
//            throw new StudentNotFoundException("student id not found: " + studentID);
//        }

        return theStudents.get(studentID);
    }

     @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());


        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){
         StudentErrorResponse error = new StudentErrorResponse();

         error.setStatus(HttpStatus.BAD_REQUEST.value());
         error.setMessage(exc.getMessage());
         error.setTimeStamp(System.currentTimeMillis());


         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

     }

}
