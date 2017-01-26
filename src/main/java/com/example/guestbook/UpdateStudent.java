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
/**
 * Form Handling Servlet
 * Most of the action for this sample is in webapp/guestbook.jsp, which displays the
 * {@link Greeting}'s. This servlet has one method
 * {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse resp#>)} which takes the form
 * data and saves it.
 */
public class UpdateStudent extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
  }
  
  public void doGet(HttpServletRequest req, HttpServletResponse res)
		  throws IOException  {
	  	
	  	Key<Guestbook> theBook=Key.create(Guestbook.class, "default");
	  	
	  	//check if student is in DB
	  	//List<Student> students = ObjectifyService.ofy().load().type(Student.class).ancestor(theBook).order("-date").list();
		boolean studentFound = false;
		//TODO
//		for(Student s : students){
//			if(s.getMatrikelnummer().equals(matrikelNr)){
//				//student schon da
//				studentFound = true;
				//get the number of attandences of the student
//		}
		
		//store student
		//TODO
		if(!studentFound){
			String matrikelNr = req.getParameter("matrikelnummer");
		  	String hasPresented = req.getParameter("presented");
		  	
		  //get the number of attandences of the student
		  	int attendanceCounter = 0;
		  	Student student = new Student(matrikelNr);
		  	
		  	//String matNr = ObjectifyService.ofy().load().key(Key.create(Student.class, matrikelNr)).now();
		  	
		  	
		  	student.setPresented(hasPresented);
			student.setDate(new Date());
			attendanceCounter++;
			student.setAttendanceCtr(attendanceCounter);
			//update student
			
			ObjectifyService.ofy().delete().key(Key.create(Student.class, student.getMatrikelnummer())).now();
			
			ObjectifyService.ofy().save().entity(student).now();
			res.setStatus(202);
		}else{
			res.setStatus(204);
			//student was not registered!!
		}
	  	
  }
}
//[END all]
