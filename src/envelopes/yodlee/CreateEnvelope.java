package envelopes.yodlee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import envelopes.data.Constants;
import envelopes.data.EnvelopeHandler;

/**
 * Servlet implementation class CreateEnvelope
 */
@WebServlet("/CreateEnvelope")
public class CreateEnvelope extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEnvelope() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String envelope = request.getParameter("enve");
		
		EnvelopeHandler eh = new EnvelopeHandler(); 
		eh.addEnvelope(envelope, (String) request.getSession().getAttribute(Constants.USERNAME));
		
		response.sendRedirect("ShowEnvelopes");
		
		
	}

}
