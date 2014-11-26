package envelopes.yodlee;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.security.auth.login.LoginContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class LogIn {
	public static String HOST_URI = "https://rest.developer.yodlee.com/services/srest/restserver/";

	private static String COB_LOGIN_URL = "v1.0/authenticate/coblogin";
	private static String USER_LOGIN_URL = "v1.0/authenticate/login";
	private static String ITEM_MTMT_URL = "v1.0/jsonsdk/ItemManagement/getLoginFormForContentService";
	private static String SEARCH_SITE_URL = "v1.0/jsonsdk/SiteTraversal/searchSite";
	private static String USER_REGISTER_URL = "v1.0/jsonsdk/UserRegistration/register3";
	private static String USER_TRANSAC_SERVICE = "v1.0/jsonsdk/TransactionSearchService/executeUserSearchRequest";
	private static String DATA_SERVICE = "v1.0/jsonsdk/DataService/getItemSummaries";
	private static String GET_SITE_INFO = "v1.0/jsonsdk/SiteTraversal/getSiteInfo";
	private static String GET_ALL_SITES = "v1.0/jsonsdk/SiteTraversal/getAllSites";
	private static String GET_POPULAR_SITES = "v1.0/jsonsdk/SiteTraversal/getPopularSites";
	private static String ITEM_SUMM_FOR_SITE = "v1.0/jsonsdk/DataService/getItemSummariesForSite";
	private static String ADD_SITE_ACC = "v1.0/jsonsdk/SiteAccountManagement/addSiteAccount1";

	//Common parameters for all APIs except for cobrand login or cobrand creation APIs
	private String paramNameCobSessionToken = "cobSessionToken";
	private String paramNameUserSessionToken = "userSessionToken";
	
	//Cobrand login API parameters
	private String paramNameCobrandLogin = "cobrandLogin";
	private String paramNameCobrandPassword = "cobrandPassword";

	//User login API parameters
	private String paramNameUserLogin = "login";
	private String paramNameUserPassword = "password";

	//Create cobrand credentials API parameters
	private String paramNameNewUserLogin = "userCredentials.loginName";
	private String paramNameNewUserPassword = "userCredentials.password";
	private String paramNameInstanceType = "userCredentials.objectInstanceType";
	private String paramNameUserEmail = "userProfile.emailAddress";
	private String paramNameFirstName = "userProfile.firstName";
	private String paramNameLastName = "userProfile.lastName";

	private String paramNamecontainerType = "transactionSearchRequest.containerType";
	private String paramNamehigherFetchLimit = "transactionSearchRequest.higherFetchLimit";
	private String paramNamelowerFetchLimit = "transactionSearchRequest.lowerFetchLimit";
	private String paramNameendNumber = "transactionSearchRequest.resultRange.endNumber";
	private String paramNamestartNumber = "transactionSearchRequest.resultRange.startNumber";
	private String paramNameclientId = "transactionSearchRequest.searchClients.clientId";
	private String paramNameclientName = "transactionSearchRequest.searchClients.clientName";
	private String paramNamecurrencyCode = "transactionSearchRequest.searchFilter.currencyCode";
	private String paramNamefromDate = "transactionSearchRequest.searchFilter.postDateRange.fromDate";
	private String paramNametoDate = "transactionSearchRequest.searchFilter.postDateRange.toDate";
	private String paramNametransactionSplitType = "transactionSearchRequest.searchFilter.transactionSplitType";
	private String paramNameignoreUserInput = "transactionSearchRequest.ignoreUserInput";

	public String loginCobrand(String cobrandLoginValue,
			String cobrandPasswordValue) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + COB_LOGIN_URL;

		System.out.println("Validating Cobrand by Connecting to URL " + url);
		String cobrandSessionToken = null;

		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobrandLogin, cobrandLoginValue);
			pm.addParameter(paramNameCobrandPassword, cobrandPasswordValue);

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);
			System.out.println(pm.getResponseBodyAsString());

			String source = pm.getResponseBodyAsString();

			JSONObject jsonObject = new JSONObject(source);
			JSONObject cobConvCreds = jsonObject
					.getJSONObject("cobrandConversationCredentials");
			cobrandSessionToken = (String) cobConvCreds.get("sessionToken");

			System.out.println("\n\n");

			System.out.println("Cobrand Session " + cobrandSessionToken);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return cobrandSessionToken;
	}

	public String loginUser(String cobrandSessionToken, String usernameValue,
			String passwordValue) {
		String userSessionToken = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		String url = HOST_URI + USER_LOGIN_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameUserLogin, usernameValue);
			pm.addParameter(paramNameUserPassword, passwordValue);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(source);
			JSONObject userContext = jsonObject.getJSONObject("userContext");
			JSONObject userConvCreds = userContext
					.getJSONObject("conversationCredentials");
			userSessionToken = (String) userConvCreds.get("sessionToken");

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String getLoginFormDetails(String cobrandSessionToken,
			String userSessionToken) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + ITEM_MTMT_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);
			pm.addParameter("contentServiceId", "11175");
			pm.addParameter("contentServiceId.objectInstanceType",
					"java.lang.Long");

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String registerUser(String cobrandSessionToken, String newUsername,
			String newPassword, String instanceType, String newEmail, String firstName, String lastName) {
		String userSessionToken = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + USER_REGISTER_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			//Cobrand session token
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			
			//New cobrand credentials parameters
			pm.addParameter(paramNameNewUserLogin, newUsername);
			pm.addParameter(paramNameNewUserPassword, newPassword);
			pm.addParameter(paramNameInstanceType, "com.yodlee.ext.login.PasswordCredentials");
			pm.addParameter(paramNameUserEmail, newEmail);
			pm.addParameter(paramNameFirstName, firstName);
			pm.addParameter(paramNameLastName, lastName);

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			System.out.println(pm.getResponseBodyAsString());
			
			String source = pm.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(source);
			JSONObject userContext = jsonObject.getJSONObject("userContext");
			JSONObject userConvCreds = userContext
					.getJSONObject("conversationCredentials");
			userSessionToken = (String) userConvCreds.get("sessionToken");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String transactionSearchService(String cobrandSessionToken,
			String userSessionToken, String containerType,
			String higherFetchLimit, String lowerFetchLimit, String endNumber,
			String startNumber, String clientId, String clientName,
			String currencyCode, String fromDate, String toDate,
			String transactionSplitType, String ignoreUserInput) {
		// String userSessionToken=null;
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + USER_TRANSAC_SERVICE;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);
			pm.addParameter(paramNamecontainerType, containerType);
			pm.addParameter(paramNamehigherFetchLimit, higherFetchLimit);
			pm.addParameter(paramNamelowerFetchLimit, lowerFetchLimit);
			pm.addParameter(paramNameendNumber, endNumber);
			pm.addParameter(paramNamestartNumber, startNumber);
			pm.addParameter(paramNameclientId, clientId);
			pm.addParameter(paramNameclientName, clientName);
			pm.addParameter(paramNamecurrencyCode, currencyCode);
			pm.addParameter(paramNamefromDate, fromDate);
			pm.addParameter(paramNametoDate, toDate);
			pm.addParameter(paramNametransactionSplitType, transactionSplitType);
			pm.addParameter(paramNameignoreUserInput, ignoreUserInput);

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			// String source=pm.getResponseBodyAsString();
			// JSONObject jsonObject= new JSONObject(source);
			// JSONObject userContext= jsonObject.getJSONObject("userContext");
			// JSONObject userConvCreds=
			// userContext.getJSONObject("conversationCredentials");
			// userSessionToken= (String) userConvCreds.get("sessionToken");

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	public String getItemSummaries(String cobrandSessionToken,
			String userSessionToken) {
		// String userSessionToken=null;
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + DATA_SERVICE;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			/*
			 * String source=pm.getResponseBodyAsString(); JSONObject
			 * jsonObject= new JSONObject(source); JSONObject userContext=
			 * jsonObject.getJSONObject("userContext"); JSONObject
			 * userConvCreds=
			 * userContext.getJSONObject("conversationCredentials");
			 * userSessionToken= (String) userConvCreds.get("sessionToken");
			 */

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	public String getItemSummariesForSite(String cobrandSessionToken,
			String userSessionToken) {
		// String userSessionToken=null;
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + ITEM_SUMM_FOR_SITE;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);
			pm.addParameter("memSiteAccId", "10268903");
			pm.addParameter("memSiteAccId.objectInstanceType", "java.lang.Long");

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			/*
			 * String source=pm.getResponseBodyAsString(); JSONObject
			 * jsonObject= new JSONObject(source); JSONObject userContext=
			 * jsonObject.getJSONObject("userContext"); JSONObject
			 * userConvCreds=
			 * userContext.getJSONObject("conversationCredentials");
			 * userSessionToken= (String) userConvCreds.get("sessionToken");
			 */

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	public String searchSite(String cobrandSessionToken, String userSessionToken, String searchString) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + SEARCH_SITE_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);
			pm.addParameter("siteSearchString", searchString);
			// pm.addParameter("siteSearchString.objectInstanceType",
			// "java.lang.String");

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();

			System.out.println("Search Results: " + pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String getAllSites(String cobrandSessionToken,
			String userSessionToken) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + GET_ALL_SITES;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String addSiteAccount(String cobrandSessionToken,
			String userSessionToken) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = HOST_URI + ADD_SITE_ACC;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);

			pm.addParameter("credentialFields[0].name", "LOGIN");
			pm.addParameter("credentialFields[0].displayName", "Username");
			pm.addParameter("credentialFields[0].isEditable", "true");
			pm.addParameter("credentialFields[0].isOptional", "false");
			pm.addParameter("credentialFields[0].helpText", "22059");
			pm.addParameter("credentialFields[0].valuePattern", "null");
			pm.addParameter("credentialFields[0].defaultValue", "null");
			pm.addParameter("credentialFields[0].value", "test1");
			pm.addParameter("credentialFields[0].validValues", "test1");
			pm.addParameter("credentialFields[0].displayValidValues", "null");
			pm.addParameter("credentialFields[0].valueIdentifier", "LOGIN");
			pm.addParameter("credentialFields[0].valueMask", "LOGIN_FIELD");
			pm.addParameter("credentialFields[0].fieldType", "LOGIN");
			pm.addParameter("credentialFields[0].validationRules", "null");
			pm.addParameter("credentialFields[0].size", "20");
			pm.addParameter("credentialFields[0].maxlength", "40");
			pm.addParameter("credentialFields[0].userProfileMappingExpression",
					"null");
			pm.addParameter("credentialFields[0].fieldErrorCode", "1");
			pm.addParameter("credentialFields[0].fieldErrorMessage", "null");

			pm.addParameter("credentialFields[1].name", "PASSWORD");
			pm.addParameter("credentialFields[1].displayName", "Password");
			pm.addParameter("credentialFields[1].isEditable", "true");
			pm.addParameter("credentialFields[1].isOptional", "false");
			pm.addParameter("credentialFields[1].helpText", "AUS_Row_Name");
			pm.addParameter("credentialFields[1].valuePattern", "null");
			pm.addParameter("credentialFields[1].defaultValue", "null");
			pm.addParameter("credentialFields[1].value", "test2");
			pm.addParameter("credentialFields[1].validValues", "test2");
			pm.addParameter("credentialFields[1].displayValidValues", "null");
			pm.addParameter("credentialFields[1].valueIdentifier", "PASSWORD");
			pm.addParameter("credentialFields[1].valueMask", "LOGIN_FIELD");
			pm.addParameter("credentialFields[1].fieldType", "PASSWORD");
			pm.addParameter("credentialFields[1].validationRules", "null");
			pm.addParameter("credentialFields[1].size", "20");
			pm.addParameter("credentialFields[1].maxlength", "40");
			pm.addParameter("credentialFields[1].userProfileMappingExpression",
					"null");
			pm.addParameter("credentialFields[1].fieldErrorCode", "1");
			pm.addParameter("credentialFields[1].fieldErrorMessage", "null");
			pm.addParameter("credentialFields.objectInstanceType",
					"[Lcom.yodlee.common.FieldInfoSingle;");

			pm.addParameter("siteId", "8995");
			// pm.addParameter("siteId.objectInstanceType", "long");

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String getSiteInfo(String cobrandSessionToken,
			String userSessionToken) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		// String excludeContentServiceInfo = "false";
		String reqSpecifier = "128";
		String siteId = "16441";

		String url = HOST_URI + GET_SITE_INFO;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);

			// spm.addParameter("siteFilter.excludeContentServiceInfo" ,
			// excludeContentServiceInfo);
			// pm.addParameter("siteFilter.excludeContentServiceInfo.objectInstanceType","java.lang.Boolean");
			pm.addParameter("siteFilter.reqSpecifier", reqSpecifier);
			// pm.addParameter("siteFilter.reqSpecifier.objectInstanceType" ,
			// "java.lang.Integer");
			pm.addParameter("siteFilter.siteId", siteId);
			// pm.addParameter("siteFilter.siteId.objectInstanceType" ,
			// "java.lang.Long");

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	public String getPopularSites(String cobrandSessionToken,
			String userSessionToken) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		// String excludeContentServiceInfo = "false";
		/*
		 * String reqSpecifier = "128"; String siteId = "16441";
		 */

		String url = HOST_URI + GET_POPULAR_SITES;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);

			// spm.addParameter("siteFilter.excludeContentServiceInfo" ,
			// excludeContentServiceInfo);
			// pm.addParameter("siteFilter.excludeContentServiceInfo.objectInstanceType","java.lang.Boolean");
			pm.addParameter("siteFilter.siteLevel", "POPULAR_STATE");
			// pm.addParameter("siteFilter.siteLevel" , "ZIP");
			
//			pm.addParameter("siteFilter.siteLevel.CODE_CITY" , "CA");
			pm.addParameter("siteFilter.siteLevel.CODE_STATE" , "TX");
//			pm.addParameter("siteFilter.siteLevel.CODE_COUNTRY" , "4");
			
			// pm.addParameter("siteFilter.reqSpecifier.objectInstanceType" ,
			// "java.lang.Integer");
			// pm.addParameter("siteFilter.siteId" , siteId);
			// pm.addParameter("siteFilter.siteId.objectInstanceType" ,
			// "java.lang.Long");

			HttpClient hc = new HttpClient();
			hc.executeMethod(pm);

			String source = pm.getResponseBodyAsString();

			System.out.println(pm.getResponseBodyAsString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return userSessionToken;
	}

	private static class NullHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

}
