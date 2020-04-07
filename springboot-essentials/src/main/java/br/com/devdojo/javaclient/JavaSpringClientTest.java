package br.com.devdojo.javaclient;

import br.com.devdojo.model.Student;

public class JavaSpringClientTest {

    public static void main (String[] args) {

        Student student = new Student();
        student.setEmail("jose.wilquer@gmail.com");
        student.setId(44L);

        JavaClientDAO dao = new JavaClientDAO();
//        dao.update(student);

        dao.save(student);

       // dao.delete(10);
//        System.out.println(dao.findById(5555));
//        System.out.println(dao.listAll());
//        System.out.println(dao.save(studentPost));


    }


}
