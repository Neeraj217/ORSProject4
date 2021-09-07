package in.co.rays.project_4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * The Class MyProfileCtl.
 */
@WebServlet(name="MyProfileCtl",urlPatterns={"/ctl/MyProfileCtl"})
public class MyProfileCtl  extends BaseCtl{

	/** The Constant OP_CHANGE_MY_PASSWORD. */
	public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

    /** The log. */
    private static Logger log = Logger.getLogger(MyProfileCtl.class);

    /**
	 * Validates input data entered by User.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("MyProfileCtl Method validate Started");

        boolean pass = true;

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

            return pass;
        }

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName","Name must be character");
			pass = false;
		}

        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName",
                    PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName","Last Name must be character");
			pass = false;
        }
        if (DataValidator.isNull(request.getParameter("gender"))) {
        	System.out.println("gender");
            request.setAttribute("gender",
                    PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo",
                    PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        }
        else if(!DataValidator.isPhoneLength(request.getParameter("mobileNo"))){
	    	request.setAttribute("mobileNo","Mobile No must be 10 digits ");
			pass = false;
	    }
		else if(!DataValidator.isPhoneNo(request.getParameter("mobileNo"))){
		request.setAttribute("mobileNo", "Enter valid Mobile no.");
		    pass = false;
		
	    }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }
        
        
        log.debug("MyProfileCtl Method validate Ended");

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
        log.debug("MyProfileCtl Method populatebean Started");
      
        
        UserBean bean = new UserBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setLogin(DataUtility.getString(request.getParameter("login")));

        bean.setFirstName(DataUtility.getString(request
                .getParameter("firstName")));

        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

        bean.setGender(DataUtility.getString(request.getParameter("gender")));

        bean.setDob(DataUtility.getDate(request.getParameter("dob")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Display Concept for viewing profile page view.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        log.debug("MyprofileCtl Method doGet Started");

        UserBean UserBean = (UserBean) session.getAttribute("user");
        long id = UserBean.getId();
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        UserModel model = new UserModel();
        if (id > 0 || op != null) {
            System.out.println("in id > 0  condition");
            UserBean bean;
            try {
                bean = model.findByPK(id);
                ServletUtility.setBean(bean, request);
            } catch (Exception e) {          //ApplicationException
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
       
        ServletUtility.forward(getView(), request, response);

        log.debug("MyProfileCtl Method doGet Ended");
    }

    /**
     * Submit Concept.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        log.debug("MyprofileCtl Method doPost Started");

        UserBean UserBean = (UserBean) session.getAttribute("user");
        long id = UserBean.getId();
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        UserModel model = new UserModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            UserBean bean = (UserBean) populateBean(request);
            try {
                if (id > 0) {
                    UserBean.setFirstName(bean.getFirstName());
                    UserBean.setLastName(bean.getLastName());
                    UserBean.setGender(bean.getGender());
                    UserBean.setMobileNo(bean.getMobileNo());
                    UserBean.setDob(bean.getDob());
                    model.update(UserBean);

                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Profile has been updated Successfully. ", request);
            } catch (Exception e) {              //ApplicationException 
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } /*catch (Exception e) {             //DuplicateRecordsException
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login id already exists",
                        request);
            }*/
        } else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request,
                    response);
            return;

        }

        ServletUtility.forward(getView(), request, response);

        log.debug("MyProfileCtl Method doPost Ended");
    }


    /**
	 * Returns the VIEW page of this Controller.
	 *
	 * @return the view
	 */
    @Override
    protected String getView() {
        return ORSView.MY_PROFILE_VIEW;
    }


}
