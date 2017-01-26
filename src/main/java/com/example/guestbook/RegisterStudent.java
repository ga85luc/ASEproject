package com.example.guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
//import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Key;
import java.util.List;
import com.example.guestbook.Student;
import java.util.logging.Logger;
/**
 * Form Handling Servlet
 * Most of the action for this sample is in webapp/guestbook.jsp, which displays the
 * {@link Greeting}'s. This servlet has one method
 * {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse resp#>)} which takes the form
 * data and saves it.
 */
public class RegisterStudent extends HttpServlet {

	private static final Logger log = Logger.getLogger(RegisterStudent.class.getName());
  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
  }
  
  public void doGet(HttpServletRequest req, HttpServletResponse res)
		  throws IOException  {
	  	
	  	String matrikelNr = req.getParameter("matrikelnummer");
	  	String groupId = req.getParameter("id");
		Student student = new Student (matrikelNr);
		student.setGroupId(groupId);
		student.setDate(new Date());
		
		// check if student is already registerd: load from DB to check if the MatNr is already registered.
		Key<Guestbook> theBook=Key.create(Guestbook.class, "ASE");
    	List<Student> students = ObjectifyService.ofy()
    	          .load()
    	          .type(Student.class) // We want only Students
    	          .ancestor(theBook)    // Anyone in this book
    	          .list();
    	
    	//log.info("test123");
    	boolean studentFound = false;
    	for (Student s : students) {
    		//log.info(s.getMatrikelnummer());
    		if(s.getMatrikelnummer().equals(matrikelNr)){
				//student schon da
				studentFound = true;
				res.setStatus(208);
		    	//log.info("Found student");
    	    	//log.info(s.getMatrikelnummer() + " "+ matrikelNr);
			}
    		else{
    			studentFound = false;
    		}
    	}
		
		//store student
		if(!studentFound){
			ObjectifyService.ofy().save().entity(student).now();
			res.setStatus(201);
	    	log.info("Registered new student");
		}
  }
}
//[END all]
