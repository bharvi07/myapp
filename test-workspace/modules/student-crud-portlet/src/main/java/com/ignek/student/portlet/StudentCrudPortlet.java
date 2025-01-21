package com.ignek.student.portlet;

import com.ignek.student.constants.StudentCrudPortletKeys;
import com.ignek.student.model.Student;
import com.ignek.student.service.StudentLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bharavi
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=StudentCrud",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + StudentCrudPortletKeys.STUDENTCRUD,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class StudentCrudPortlet extends MVCPortlet {
	private Log log = LogFactoryUtil.getLog(this.getClass().getName());

	@Reference
	CounterLocalService counterLocalService;
	@Reference
	StudentLocalService studentLocalService;

	@ProcessAction(name = "addStudent")
	public void addStudent(ActionRequest actionRequest,ActionResponse actionResponse) {
		long studentId = counterLocalService.increment(Student.class.getName());
		String enrollmentNo = ParamUtil.getString(actionRequest, "enrollmentNo");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		String contactNo = ParamUtil.getString(actionRequest, "contactNo");
		String city = ParamUtil.getString(actionRequest, "city");

		log.info("enrollmentno printed: "+enrollmentNo);
		Student student = studentLocalService.createStudent(studentId);
		student.setStudentId(studentId);
		student.setEnrollmentNo(enrollmentNo);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setContactNo(contactNo);
		student.setCity(city);

		studentLocalService.addStudent(student);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user =themeDisplay.getUser();
		
		log.info("this is fullname:" +user.getFullName());
		
		List<Student> studentList = studentLocalService.getStudents(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		renderRequest.setAttribute("studentList", studentList);
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name = "updateStudent")
    public void updateStudent(ActionRequest actionRequest,  ActionResponse actionResponse) {
        long studentId = ParamUtil.getLong(actionRequest,"studentId", GetterUtil.DEFAULT_LONG);
        String enrollmentNo = ParamUtil.getString(actionRequest, "enrollmentNo", GetterUtil.DEFAULT_STRING);
        String firstName = ParamUtil.getString(actionRequest, "firstName", GetterUtil.DEFAULT_STRING);
        String lastName = ParamUtil.getString(actionRequest, "lastName", GetterUtil.DEFAULT_STRING);
        String contactNo = ParamUtil.getString(actionRequest, "contactNo", GetterUtil.DEFAULT_STRING);
        String city = ParamUtil.getString(actionRequest, "city", GetterUtil.DEFAULT_STRING);

        Student student = null;
        try {
            student = studentLocalService.getStudent(studentId);
        } catch (Exception e) {
            log.error(e.getCause(), e);
        }

        if(Validator.isNotNull(student)) {
            student.setEnrollmentNo(enrollmentNo);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setContactNo(contactNo);
            student.setCity(city);
            studentLocalService.updateStudent(student);
        }
    }
	
	@ProcessAction(name = "deleteStudent")
    public void deleteStudent(ActionRequest actionRequest, ActionResponse actionResponse){
        long studentId = ParamUtil.getLong(actionRequest, "studentId", GetterUtil.DEFAULT_LONG);
        try {
            studentLocalService.deleteStudent(studentId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}