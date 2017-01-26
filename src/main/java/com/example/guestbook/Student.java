package com.example.guestbook;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;

@Entity
public class Student {
	  @Parent Key<Guestbook> theBook=Key.create(Guestbook.class, "ASE");
	  //@Id public Long id;
	  @Id String Matrikelnummer;
	  String GroupId;
	  String Presented;
	  int AttendanceCtr;
	  @Index public Date Date;
	
	public Student(String matNr) {
	// TODO Auto-generated constructor stub
		Matrikelnummer = matNr;
	}
	
	public Student() {
		// TODO Auto-generated constructor stub
			Matrikelnummer = "-1";
		}
	
	public void setGroupId(String groupId) {
		GroupId = groupId;
	}
	
	public String getMatrikelnummer() {
		return Matrikelnummer;
	}
	
	public void setPresented(String presented) {
		Presented = presented;
	}
	
	public String getPresented() {
		return Presented;
	}
	
	public void setDate(Date date) {
		Date = date;
	}
	
	public void setAttendanceCtr(int attendanceCtr) {
		AttendanceCtr = attendanceCtr;
	}
	
}
