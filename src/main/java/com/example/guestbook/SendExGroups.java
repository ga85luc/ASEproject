package src.main.java.com.example.guestbook;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Key;

import com.example.guestbook.Guestbook;
import com.example.guestbook.Greeting;


import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;

/**
 * Resource which has only one representation.
 *
 */
public class SendExGroups extends ServerResource {

    @Get
    public String represent() {
    	String res;
    	res="<?xml version='1.0' encoding='UTF-8'?>\n";
    	res+="<exgroups>\n";
    	Key<Guestbook> theBook=Key.create(Guestbook.class, "ASE");
    	List<Greeting> greetings = ObjectifyService.ofy()
    	          .load()
    	          .type(Greeting.class) // We want only Greetings
    	          .ancestor(theBook)    // Anyone in this book
    	          .order("-date")       // Most recent first - date is indexed.
    	          .list();
    	for (Greeting greeting : greetings) {
        	res+="<exgroup>\n";
        	res+="<groupid>" + greeting.course_id +"</groupid>\n";
        	res+="<maxstudents>" + greeting.max_students + "</maxstudents>\n";

        	res+="</exgroup>\n";
    	}
    	res+="</exgroups>";
        return res;
    }

    
}