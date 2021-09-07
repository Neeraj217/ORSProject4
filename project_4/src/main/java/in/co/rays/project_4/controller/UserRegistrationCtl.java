package in.co.rays.project_4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * The Class UserRegistrationCtl.
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant OP_SIGN_UP. */
	public static final String OP_SIGN_UP = "SignUp";

	/** The log. */
	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	/**
	 * Validates input data entered by User.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		System.out.println("UserRegistrationCtl validate method started");
		log.debug("UserRegistrationCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName","Name must be character");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName","Last Name must be character");
			pass = false;
		}
		
		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login","enter valid email ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password","Password must contain Uppercase,lowercase,specialcharactor,digit");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobileNo"));
			pass = false;
			System.out.println("UserCtl Validate mobileno:"+(request.getParameter("mobileNo")));
		}
		else if(!DataValidator.isPhoneLength(request.getParameter("mobileNo"))){
	    	request.setAttribute("mobileNo","Mobile No must be 10 digits ");
			pass=false;
	    }
		else if(!DataValidator.isPhoneNo(request.getParameter("mobileNo"))){
		request.setAttribute("mobileNo", "Enter valid mobile no.");
		pass=false;
		
	    }
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}else if (!DataValidator.isValidAge(dob)) {
			request.setAttribute("dob", "Age Must be greater then 18 year");
			pass = false;
		}
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Conform Password Should be Match");
			pass = false;
		}
		log.debug("UserRegistrationCtl Method validate Ended");
		System.out.println("UserRegistration Validate method ended");
		return pass;
	}

	/**
	 * Populates bean object from request parameters.
	 *
	 * @param request the request
	 * @return the base bean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		System.out.println("UserRegistration populateBean started");
		log.debug("UserRegistrationCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRoleId(RoleBean.STUDENT);

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		
		bean.setMobileNo(request.getParameter("mobileNo"));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);

		log.debug("UserRegistrationCtl Method populatebean Ended");
		System.out.println("UserRegistrationCtl populateBean method ended");

		return bean;
	}

	/**
	 * Display concept of user registration.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistrationCtl Method doGet Started");
		
		System.out.println("UserRegistrationCtl doGet method ");
		
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submit concept of user registration.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserRegistrationCtl doPost method started");
		log.debug("UserRegistrationCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				long pk = model.registerUser(bean);
				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);
				ServletUtility.setSuccessMessage("you are registerd succefully", request);
				//ServletUtility.forward(ORSView.LOGIN_CTL, request, response);
				ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				e.printStackTrace();
				/*log.error(e);
				ServletUtility.handleException(e, request, response);*/
				return;
			}   catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				
				ServletUtility.setErrorMessage("User already exist", request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
			
			
			catch (Exception e) {
				e.printStackTrace();
				/*log.error(e);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);*/
			}
		}else if(OP_RESET.equalsIgnoreCase(op)){
	         
	          ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
	          return;
			}
		log.debug("UserRegistrationCtl Method doPost Ended");
		System.out.println("UserRegistrationCtl doPost method ended");
	}


    /**
	 * Returns the VIEW page of this Controller.
	 *
	 * @return the view
	 */
	@Override
	protected String getView() {
		System.out.println("UserRegistration getView()");
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
