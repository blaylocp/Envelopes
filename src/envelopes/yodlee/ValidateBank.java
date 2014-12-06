package envelopes.yodlee;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import envelopes.data.Bank;
import envelopes.data.Constants;

/**
 * Servlet implementation class ValidateBank
 */
@WebServlet("/ValidateBank")
public class ValidateBank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateBank() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.getRequestDispatcher("banks.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int index = Integer.parseInt(request.getParameter("index"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		List<Bank> banks = (List<Bank>)request.getSession().getAttribute("banks");
		
		LogIn helper = new LogIn();
		
		int userSiteId = helper.getLoginFormDetails((String) request.getSession().getAttribute(Constants.COB_SESSION_TOKEN), 
				(String) request.getSession().getAttribute(Constants.USER_SESSION_TOKEN), banks.get(index).getSiteID() + "", 
				username, password);
		
		if (userSiteId != 0) {
			request.getSession().setAttribute("bankAddResponse", "Successfully added your account at " + banks.get(index).getDisplayName());
		} else {
			request.getSession().setAttribute("bankAddResponse", "Error adding your account at " + banks.get(index).getDisplayName() + ". There might have been an issue with credentials provided.");
		}
		
		response.sendRedirect("ValidateBank");
	}

}
