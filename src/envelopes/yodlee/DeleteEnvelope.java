package envelopes.yodlee;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import envelopes.data.EnvelopeHandler;

/**
 * Servlet implementation class DeleteEnvelope
 */
@WebServlet("/DeleteEnvelope")
public class DeleteEnvelope extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEnvelope() {
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
		EnvelopeHandler eh = new EnvelopeHandler(); 
		String[] envelope = request.getParameterValues("category_list");
		
		for(int i = 0; i < envelope.length; i++){
			eh.deleteEnvelope(envelope[i]);
		}
		
		response.sendRedirect("ShowEnvelopes");

	}

}
