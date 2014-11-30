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
 * Servlet implementation class FindBanks
 */
@WebServlet("/FindBanks")
public class FindBanks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FindBanks() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("banks.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String bank = request.getParameter("bank");

		List<Bank> banks = LogIn.searchSite(
				(String) request.getSession().getAttribute(
						Constants.COB_SESSION_TOKEN), (String) request
						.getSession()
						.getAttribute(Constants.USER_SESSION_TOKEN), bank);
		
		request.getSession().setAttribute("banks", banks);
		
		response.sendRedirect("FindBanks");
	}

}
