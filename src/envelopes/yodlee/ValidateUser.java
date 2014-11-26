package envelopes.yodlee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		String cobrandSession = helper.loginCobrand("sbCobtravisbaker2009", "ef196540-48ea-43ef-aad5-9f9cac051384");
		request.getSession().setAttribute("cobrandSessionToken", cobrandSession);
		
		if (request.getParameter("action").equals("validate")) {
			String userSession = helper.loginUser(cobrandSession, request.getParameter("username"), 
					request.getParameter("password"));
			if (userSession == null) {
				System.out.println("User could not get signed in. Invalid credentials.");
			}
			request.getSession().setAttribute("userSessionToken", userSession);
			
			// TODO: Add validation here in case errors get returned.
		} else {
			String userSession = helper.registerUser(cobrandSession, 
					request.getParameter("username"), request.getParameter("password"), 
					null, request.getParameter("email"), request.getParameter("firstName"), 
					request.getParameter("lastName"));
			request.getSession().setAttribute("userSessionToken", userSession);
		}
		//helper.searchSite(cobrandSession, userSession, "asdf");
	}

}
