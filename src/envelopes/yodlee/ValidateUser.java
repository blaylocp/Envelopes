package envelopes.yodlee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import envelopes.data.Constants;

/**
 * Servlet implementation class ValidateUser
 */
@WebServlet("/ValidateUser")
public class ValidateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("doGet");
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LogIn helper = new LogIn();
		
		String cobrandSession = helper.loginCobrand(Constants.COB_LOGIN, Constants.COB_PASSWORD);
		request.getSession().setAttribute(Constants.COB_SESSION_TOKEN, cobrandSession);
		
		String userSession = null;
		String errorMsg = null;
		
		if (request.getParameter("action").equals("validate")) {
			userSession = helper.loginUser(cobrandSession, request.getParameter("username"), 
					request.getParameter("password"));
			if (userSession == null) {
				errorMsg = "Could not log in user. Invalid credentials.";
			}
			
			// TODO: Add validation here in case errors get returned.
		} else {
			userSession = helper.registerUser(cobrandSession, 
					request.getParameter("username"), request.getParameter("password"), 
					null, request.getParameter("email"), request.getParameter("firstName"), 
					request.getParameter("lastName"));
			
			if (userSession == null) {
				errorMsg = "Could not register user. Invalid credentials.";
			}
		}
		
		if (userSession == null) {
			request.getSession().setAttribute("errorMsg", errorMsg);
			response.sendRedirect("SignIn");
		} else {
			request.getSession().removeAttribute("errorMsg");
			request.getSession().setAttribute(Constants.USERNAME, request.getParameter("username"));
			request.getSession().setAttribute(Constants.USER_SESSION_TOKEN, userSession);
			response.sendRedirect("Home");
		}
		
	}

}
