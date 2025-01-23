<%@ include file="init.jsp"%>
<portlet:defineObjects/>
<portlet:actionURL name="updateStudent" var="updateStudentActionURL"/>
<aui:form action="<%=updateStudentActionURL%>" name="studentForm" method="POST"/>

<%
	String studentId = renderRequest.getParameter("studentId");
	String enrollmentNo = renderRequest.getParameter("enrollmentNo");
	String firstName = renderRequest.getParameter("firstName");
	String lastName = renderRequest.getParameter("lastName");
	String contactNo = renderRequest.getParameter("contactNo");
	String city = renderRequest.getParameter("city");
%>
<aui:form action="<%= updateStudentActionURL %>" method="post" >
	<aui:input name="studentId" type="hidden" value="<%=Long.parseLong(studentId)%>"/>
	<aui:input name="enrollmentNo" type="text" value="${student.enrollmentNo}"/>
	<aui:input name="firstName" type="text" value="${student.firstName}"/>
	<aui:input name="lastName" type="text" value="${student.lastName}" />
	<aui:input name="contactNo" type="text" value="${student.contactNo}" />
	<aui:input name="city" type="text" value="${student.city}"/>
	<aui:input type="submit" value="Update" name="update"></aui:input>
</aui:form>