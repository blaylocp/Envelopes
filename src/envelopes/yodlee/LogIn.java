package envelopes.yodlee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import envelopes.data.Bank;
import envelopes.data.BankEntity;
import envelopes.data.LoginForm;
import envelopes.data.Transaction;

public class LogIn {
	public static String HOST_URI = "https://rest.developer.yodlee.com/services/srest/restserver/";

	private static String COB_LOGIN_URL = "v1.0/authenticate/coblogin";
	private static String USER_LOGIN_URL = "v1.0/authenticate/login";
	private static String ITEM_MTMT_URL = "v1.0/jsonsdk/SiteAccountManagement/getSiteLoginForm";
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
	private static String paramNameCobSessionToken = "cobSessionToken";
	private static String paramNameUserSessionToken = "userSessionToken";
	
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
		
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String url = HOST_URI + COB_LOGIN_URL;

		System.out.println("Validating Cobrand by Connecting to URL " + url);
		String cobrandSessionToken = null;

		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());

			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(paramNameCobrandLogin, cobrandLoginValue));
			params.add(new BasicNameValuePair(paramNameCobrandPassword, cobrandPasswordValue));
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }
			
			System.out.println(source);

			JSONObject jsonObject = new JSONObject(source);
			JSONObject cobConvCreds = jsonObject
					.getJSONObject("cobrandConversationCredentials");
			cobrandSessionToken = (String) cobConvCreds.get("sessionToken");

			System.out.println("\n\n");

			System.out.println("Cobrand Session " + cobrandSessionToken);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return cobrandSessionToken;
	}

	public String loginUser(String cobrandSessionToken, String usernameValue,
			String passwordValue) {
		String userSessionToken = null;
		
		
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		String url = HOST_URI + USER_LOGIN_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(paramNameUserLogin, usernameValue));
			params.add(new BasicNameValuePair(paramNameUserPassword, passwordValue));
			params.add(new BasicNameValuePair(paramNameCobSessionToken, cobrandSessionToken));
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }

			JSONObject jsonObject = new JSONObject(source);
			JSONObject userContext = jsonObject.getJSONObject("userContext");
			JSONObject userConvCreds = userContext
					.getJSONObject("conversationCredentials");
			userSessionToken = (String) userConvCreds.get("sessionToken");

			System.out.println(source);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userSessionToken;
	}

	public int getLoginFormDetails(String cobrandSessionToken,
			String userSessionToken, String siteID, String username, String password) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		int userSiteId = 0;

		String url = HOST_URI + ITEM_MTMT_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(paramNameCobSessionToken, cobrandSessionToken));
			params.add(new BasicNameValuePair("siteId", siteID));
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }
			
			LoginForm login = new LoginForm(new JSONObject(source), siteID, username, password);
			
			userSiteId = addSiteAccount(cobrandSessionToken, userSessionToken, login);

			System.out.println(source);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userSiteId;
	}

	public String registerUser(String cobrandSessionToken, String newUsername,
			String newPassword, String instanceType, String newEmail, String firstName, String lastName) {
		String userSessionToken = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String url = HOST_URI + USER_REGISTER_URL;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(paramNameNewUserLogin, newUsername));
			params.add(new BasicNameValuePair(paramNameNewUserPassword, newPassword));
			params.add(new BasicNameValuePair(paramNameInstanceType, "com.yodlee.ext.login.PasswordCredentials"));
			params.add(new BasicNameValuePair(paramNameUserEmail, newEmail));
			params.add(new BasicNameValuePair(paramNameFirstName, firstName));
			params.add(new BasicNameValuePair(paramNameLastName, lastName));
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }

			System.out.println(source);
			JSONObject jsonObject = new JSONObject(source);
			JSONObject userContext = jsonObject.getJSONObject("userContext");
			JSONObject userConvCreds = userContext
					.getJSONObject("conversationCredentials");
			userSessionToken = (String) userConvCreds.get("sessionToken");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userSessionToken;
	}

	public List<Transaction> transactionSearchService(String cobrandSessionToken,
			String userSessionToken) {
		// String userSessionToken=null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		List<Transaction> myTransactions = new ArrayList<Transaction>();

		String url = HOST_URI + USER_TRANSAC_SERVICE;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(paramNameCobSessionToken, cobrandSessionToken));
			params.add(new BasicNameValuePair(paramNameUserSessionToken, userSessionToken));
			params.add(new BasicNameValuePair(paramNamecontainerType, "all"));
			params.add(new BasicNameValuePair(paramNamelowerFetchLimit, "1"));
			params.add(new BasicNameValuePair(paramNamehigherFetchLimit, "500"));
			params.add(new BasicNameValuePair(paramNameendNumber, "500"));
			params.add(new BasicNameValuePair(paramNamestartNumber, "1"));
			params.add(new BasicNameValuePair(paramNameclientId, "1"));
			params.add(new BasicNameValuePair(paramNameclientName, "DataSearchService"));
			params.add(new BasicNameValuePair(paramNamecurrencyCode, "USD"));
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			String lastMonthBegin = calendar.get(Calendar.MONTH) + "-" + "1" 
					+ "-" + calendar.get(Calendar.YEAR);
			
			String lastMonthEnd = calendar.get(Calendar.MONTH) + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) 
					+ "-" + calendar.get(Calendar.YEAR);			
			
			params.add(new BasicNameValuePair(paramNamefromDate, lastMonthBegin)); //TODO change to date
			params.add(new BasicNameValuePair(paramNametoDate, lastMonthEnd)); //TODO change to date
			params.add(new BasicNameValuePair(paramNameignoreUserInput, "true"));
			params.add(new BasicNameValuePair(paramNametransactionSplitType, "ALL_TRANSACTION"));
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }

			System.out.println(source);
			
			JSONObject json = new JSONObject(source);
			
			JSONArray transactions = json.getJSONObject("searchResult").getJSONArray("transactions");
			
			for (int i = 0; i < transactions.length(); i++) {
				myTransactions.add(new Transaction(transactions.getJSONObject(i)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return myTransactions;
	}
//
//	public String getItemSummaries(String cobrandSessionToken,
//			String userSessionToken) {
//		// String userSessionToken=null;
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		String url = HOST_URI + DATA_SERVICE;
//		try {
//			HttpsURLConnection
//					.setDefaultHostnameVerifier(new NullHostnameVerifier());
//
//			PostMethod pm = new PostMethod(url);
//			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
//			pm.addParameter(paramNameUserSessionToken, userSessionToken);
//
//			HttpClient hc = new HttpClient();
//			hc.executeMethod(pm);
//
//			/*
//			 * String source=pm.getResponseBodyAsString(); JSONObject
//			 * jsonObject= new JSONObject(source); JSONObject userContext=
//			 * jsonObject.getJSONObject("userContext"); JSONObject
//			 * userConvCreds=
//			 * userContext.getJSONObject("conversationCredentials");
//			 * userSessionToken= (String) userConvCreds.get("sessionToken");
//			 */
//
//			System.out.println(pm.getResponseBodyAsString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			httpclient.getConnectionManager().shutdown();
//		}
//
//		return null;
//	}
//
//	public String getItemSummariesForSite(String cobrandSessionToken,
//			String userSessionToken) {
//		// String userSessionToken=null;
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		String url = HOST_URI + ITEM_SUMM_FOR_SITE;
//		try {
//			HttpsURLConnection
//					.setDefaultHostnameVerifier(new NullHostnameVerifier());
//
//			PostMethod pm = new PostMethod(url);
//			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
//			pm.addParameter(paramNameUserSessionToken, userSessionToken);
//			pm.addParameter("memSiteAccId", "10268903");
//			pm.addParameter("memSiteAccId.objectInstanceType", "java.lang.Long");
//
//			HttpClient hc = new HttpClient();
//			hc.executeMethod(pm);
//
//			/*
//			 * String source=pm.getResponseBodyAsString(); JSONObject
//			 * jsonObject= new JSONObject(source); JSONObject userContext=
//			 * jsonObject.getJSONObject("userContext"); JSONObject
//			 * userConvCreds=
//			 * userContext.getJSONObject("conversationCredentials");
//			 * userSessionToken= (String) userConvCreds.get("sessionToken");
//			 */
//
//			System.out.println(pm.getResponseBodyAsString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			httpclient.getConnectionManager().shutdown();
//		}
//
//		return null;
//	}
//
	public static List<Bank> searchSite(String cobrandSessionToken, String userSessionToken, String searchString) {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String url = HOST_URI + SEARCH_SITE_URL;
		List<Bank> banks = new ArrayList<Bank>();
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(paramNameCobSessionToken, cobrandSessionToken));
			params.add(new BasicNameValuePair(paramNameUserSessionToken, userSessionToken));
			params.add(new BasicNameValuePair("siteSearchString", searchString));
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }

			
			JSONArray jsonArray = new JSONArray(source);
			System.out.println(jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				Bank temp = new Bank();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				temp.setDisplayName(jsonObject.getString("defaultDisplayName"));
				temp.setOrgName(jsonObject.getString("defaultOrgDisplayName"));
				temp.setSiteID(jsonObject.getInt("siteId"));
				temp.setOrgID(jsonObject.getInt("orgId"));
				
				JSONArray innerArray = jsonObject.getJSONArray("contentServiceInfos");
				
				for (int j = 0; i < innerArray.length(); i ++) {
					BankEntity entity = new BankEntity();
					JSONObject innerObject = innerArray.getJSONObject(j);
					entity.setContentServiceId(innerObject.getInt("contentServiceId"));
					entity.setSiteId(innerObject.getInt("siteId"));
					JSONObject innerInnerObject = innerObject.getJSONObject("containerInfo");
					entity.setContainerName(innerInnerObject.getString("containerName"));
					entity.setAssetTypeID(innerInnerObject.getInt("assetType"));
				}
				
				banks.add(temp);
			}

			System.out.println("Search Results: " + source);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO: Return a list of Bank objects.
		return banks;
	}
//
//	public String getAllSites(String cobrandSessionToken,
//			String userSessionToken) {
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		String url = HOST_URI + GET_ALL_SITES;
//		try {
//			HttpsURLConnection
//					.setDefaultHostnameVerifier(new NullHostnameVerifier());
//
//			PostMethod pm = new PostMethod(url);
//			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
//			pm.addParameter(paramNameUserSessionToken, userSessionToken);
//
//			HttpClient hc = new HttpClient();
//			hc.executeMethod(pm);
//
//			String source = pm.getResponseBodyAsString();
//
//			System.out.println(pm.getResponseBodyAsString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			httpclient.getConnectionManager().shutdown();
//		}
//
//		return userSessionToken;
//	}
//
	public int addSiteAccount(String cobrandSessionToken,
			String userSessionToken, LoginForm loginForm) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		int siteAccountId = 0;

		String url = HOST_URI + ADD_SITE_ACC;
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			HttpPost pm = new HttpPost(url);
			List<NameValuePair> params = loginForm.getFromJsonResult(cobrandSessionToken, userSessionToken);
			
			pm.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = httpclient.execute(pm);
			
			int status = response.getStatusLine().getStatusCode();
			
			String source = null;
			
			if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                 source = entity != null ? EntityUtils.toString(entity) : null;
            }
			
			JSONObject jsonObject = new JSONObject(source);
			// TODO: get the userSiteObject from this.
			siteAccountId = jsonObject.getInt("siteAccountId");
			System.out.println(jsonObject);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return siteAccountId;
	}
//
//	public String getSiteInfo(String cobrandSessionToken,
//			String userSessionToken) {
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		// String excludeContentServiceInfo = "false";
//		String reqSpecifier = "128";
//		String siteId = "16441";
//
//		String url = HOST_URI + GET_SITE_INFO;
//		try {
//			HttpsURLConnection
//					.setDefaultHostnameVerifier(new NullHostnameVerifier());
//
//			PostMethod pm = new PostMethod(url);
//			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
//			pm.addParameter(paramNameUserSessionToken, userSessionToken);
//
//			// spm.addParameter("siteFilter.excludeContentServiceInfo" ,
//			// excludeContentServiceInfo);
//			// pm.addParameter("siteFilter.excludeContentServiceInfo.objectInstanceType","java.lang.Boolean");
//			pm.addParameter("siteFilter.reqSpecifier", reqSpecifier);
//			// pm.addParameter("siteFilter.reqSpecifier.objectInstanceType" ,
//			// "java.lang.Integer");
//			pm.addParameter("siteFilter.siteId", siteId);
//			// pm.addParameter("siteFilter.siteId.objectInstanceType" ,
//			// "java.lang.Long");
//
//			HttpClient hc = new HttpClient();
//			hc.executeMethod(pm);
//
//			String source = pm.getResponseBodyAsString();
//
//			System.out.println(pm.getResponseBodyAsString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			httpclient.getConnectionManager().shutdown();
//		}
//
//		return userSessionToken;
//	}
//
//	public String getPopularSites(String cobrandSessionToken,
//			String userSessionToken) {
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		// String excludeContentServiceInfo = "false";
//		/*
//		 * String reqSpecifier = "128"; String siteId = "16441";
//		 */
//
//		String url = HOST_URI + GET_POPULAR_SITES;
//		try {
//			HttpsURLConnection
//					.setDefaultHostnameVerifier(new NullHostnameVerifier());
//
//			PostMethod pm = new PostMethod(url);
//			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
//			pm.addParameter(paramNameUserSessionToken, userSessionToken);
//
//			// spm.addParameter("siteFilter.excludeContentServiceInfo" ,
//			// excludeContentServiceInfo);
//			// pm.addParameter("siteFilter.excludeContentServiceInfo.objectInstanceType","java.lang.Boolean");
//			pm.addParameter("siteFilter.siteLevel", "POPULAR_STATE");
//			// pm.addParameter("siteFilter.siteLevel" , "ZIP");
//			
////			pm.addParameter("siteFilter.siteLevel.CODE_CITY" , "CA");
//			pm.addParameter("siteFilter.siteLevel.CODE_STATE" , "TX");
////			pm.addParameter("siteFilter.siteLevel.CODE_COUNTRY" , "4");
//			
//			// pm.addParameter("siteFilter.reqSpecifier.objectInstanceType" ,
//			// "java.lang.Integer");
//			// pm.addParameter("siteFilter.siteId" , siteId);
//			// pm.addParameter("siteFilter.siteId.objectInstanceType" ,
//			// "java.lang.Long");
//
//			HttpClient hc = new HttpClient();
//			hc.executeMethod(pm);
//
//			String source = pm.getResponseBodyAsString();
//
//			System.out.println(pm.getResponseBodyAsString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			httpclient.getConnectionManager().shutdown();
//		}
//
//		return userSessionToken;
//	}

	private static class NullHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

}
