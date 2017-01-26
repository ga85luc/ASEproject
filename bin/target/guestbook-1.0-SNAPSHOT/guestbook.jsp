<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="com.example.guestbook.Greeting" %>
<%@ page import="com.example.guestbook.Guestbook" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

<%
    String courseName = request.getParameter("guestbookName");
    if (courseName == null) {
        courseName = "ASE";
    }
    pageContext.setAttribute("courseName", courseName);
%>

<%-- //[START datastore]--%>
<%
    // Create the correct Ancestor key
      Key<Guestbook> theBook = Key.create(Guestbook.class, courseName);

    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
      List<Greeting> greetings = ObjectifyService.ofy()
          .load()
          .type(Greeting.class) // We want only Greetings
          .ancestor(theBook)    // Anyone in this book
          .order("-date")       // Most recent first - date is indexed.
          .limit(5)             // Only show 5 of them.
          .list();

    if (greetings.isEmpty()) {
%>
<p>Course '${fn:escapeXml(courseName)}' has no groups.</p>
<%
    } else {
%>
<p>Groups in Course '${fn:escapeXml(courseName)}':</p>
<%
      // Look at all of our greetings
        for (Greeting greeting : greetings) {
            pageContext.setAttribute("output_id", greeting.course_id);
            pageContext.setAttribute("output_number", greeting.max_students);
%>
<p><b> Group-ID:</b>  ${fn:escapeXml(output_id)} <b>Number of students:</b> ${fn:escapeXml(output_number)}</p>
<%
        }
    }
%>
<p>Add new Group:</p>
<form action="/sign" method="post">
    ID: 
	<div><textarea name="id" rows="1" cols="5"></textarea></div>
    Max. number of students: 
	<div><textarea name="maxnumber" rows="1" cols="5"></textarea></div>
    <div><input type="submit" value="Add!"/></div>
    <input type="hidden" name="courseName" value="${fn:escapeXml(courseName)}"/>
</form>

</body>
</html>
<%-- //[END all]--%>
