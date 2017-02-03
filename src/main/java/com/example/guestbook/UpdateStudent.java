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
 * Form Handling Servlet Most of the action for this sample is in
 * webapp/guestbook.jsp, which displays the {@link Greeting}'s. This servlet has
 * one method {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse
 * resp#>)} which takes the form data and saves it.
 */
public class UpdateStudent extends HttpServlet {

	// Process the http POST of the form
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

		int attendanceCounter = 0;
		String groupId = "";
		String matrikelNr = req.getParameter("matrikelnummer");
		String hasPresented = req.getParameter("presented");
		// check if student is already registerd: load from DB to check if the
		// MatNr is already registered.
		Key<Guestbook> theBook = Key.create(Guestbook.class, "ASE");
		//get students from DB
		List<Student> students = ObjectifyService.ofy().load().type(Student.class).ancestor(theBook).list();

		boolean studentFound = false;
		for (Student s : students) {
			if (s.getMatrikelnummer().equals(matrikelNr)) {
				// student schon da
				studentFound = true;
				attendanceCounter = s.getAttendanceCtr();
				groupId = s.getGroupId();
				if(hasPresented == null){
					hasPresented = s.getPresented();
				}
			} else {
				studentFound = false;
			}
		}

		// update student
		if (studentFound) {

			Student student = new Student(matrikelNr);
			student.setPresented(hasPresented);
			student.setDate(new Date());
			attendanceCounter++;
			student.setAttendanceCtr(attendanceCounter);
			student.setGroupId(groupId);
			ObjectifyService.ofy().delete().key(Key.create(Student.class, student.getMatrikelnummer())).now();

			ObjectifyService.ofy().save().entity(student).now();
			res.setStatus(202);
		} else {
			res.setStatus(204);
			// student was not registered!!
		}

	}
}
// [END all]
