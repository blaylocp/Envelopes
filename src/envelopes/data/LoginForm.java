package envelopes.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginForm {
	private String usernameName = null;
	private String usernameDisplayName = null;
	private Boolean usernameIsEditable = null;
	private String usernameHelpText = null;
	private String usernameValue = null;
	private String usernameValueIdentifier = null;
	private String usernameValueMask = null;
	private String usernameFieldTypeName = null;
	private String usernameSize = null;
	private String usernameMaxLength = null;
	
	private String siteID = null;
	
	private String passwordName = null;
	private String passwordDisplayName = null;
	private Boolean passwordIsEditable = null;
	private String passwordHelpText = null;
	private String passwordValue = null;
	private String passwordValueIdentifier = null;
	private String passwordValueMask = null;
	private String passwordFieldTypeName = null;
	private String passwordSize = null;
	private String passwordMaxLength = null;
	
	private final NameValuePair enclosedType = new BasicNameValuePair("credentialFields.enclosedType", "com.yodlee.common.FieldInfoSingle");

//	pm.addParameter("credentialFields[1].name", "PASSWORD");
//	pm.addParameter("credentialFields[1].displayName", "Password");
//	pm.addParameter("credentialFields[1].isEditable", "true");
//	pm.addParameter("credentialFields[1].helpText", "AUS_Row_Name");
//	pm.addParameter("credentialFields[1].value", "test2");
//	pm.addParameter("credentialFields[1].valueIdentifier", "PASSWORD");
//	pm.addParameter("credentialFields[1].valueMask", "LOGIN_FIELD");
//	pm.addParameter("credentialFields[1].fieldType.typeName", "PASSWORD");
//	pm.addParameter("credentialFields[1].size", "20");
//	pm.addParameter("credentialFields[1].maxlength", "40");
	
	public LoginForm(JSONObject result, String siteID, String pUsername, String pPassword) {
		try {
			JSONArray components = result.getJSONArray("componentList");
			JSONObject username = components.getJSONObject(0);
			
			this.siteID = siteID;
			this.usernameName = username.getString("name");
			this.usernameDisplayName = username.getString("displayName");
			this.usernameIsEditable = username.getBoolean("isEditable");
			this.usernameHelpText = username.getString("helpText");
			this.usernameValue = pUsername;
			this.usernameValueIdentifier = username.getString("valueIdentifier");
			this.usernameValueMask = username.getString("valueMask");
			this.usernameFieldTypeName = username.getJSONObject("fieldType").getString("typeName");
			this.usernameSize = username.getInt("size") + "";
			this.usernameMaxLength = username.getInt("maxlength") + "";
			
			JSONObject password = components.getJSONObject(1);
			
			this.passwordName = password.getString("name");
			this.passwordDisplayName = password.getString("displayName");
			this.passwordIsEditable = password.getBoolean("isEditable");
			this.passwordHelpText = password.getString("helpText");
			this.passwordValue = pPassword;
			this.passwordValueIdentifier = password.getString("valueIdentifier");
			this.passwordValueMask = password.getString("valueMask");
			this.passwordFieldTypeName = password.getJSONObject("fieldType").getString("typeName");
			this.passwordSize = password.getInt("size") + "";
			this.passwordMaxLength = password.getInt("maxlength") + "";
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<NameValuePair> getFromJsonResult(String cobrandSession, String userSession) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
			
		params.add(new BasicNameValuePair("cobSessionToken", cobrandSession));
		params.add(new BasicNameValuePair("userSessionToken", userSession));
		params.add(new BasicNameValuePair("credentialFields[0].name", this.usernameName));
		params.add(new BasicNameValuePair("credentialFields[0].displayName", this.usernameDisplayName));
		params.add(new BasicNameValuePair("credentialFields[0].isEditable", this.usernameIsEditable.toString()));
		params.add(new BasicNameValuePair("credentialFields[0].helpText", this.usernameHelpText));
		params.add(new BasicNameValuePair("credentialFields[0].value", this.usernameValue));
		params.add(new BasicNameValuePair("credentialFields[0].valueIdentifier", this.usernameValueIdentifier));
		params.add(new BasicNameValuePair("credentialFields[0].valueMask", this.usernameValueMask));
		params.add(new BasicNameValuePair("credentialFields[0].fieldType.typeName", this.usernameFieldTypeName));
		params.add(new BasicNameValuePair("credentialFields[0].size", this.usernameSize));
		params.add(new BasicNameValuePair("credentialFields[0].maxlength", this.usernameMaxLength));
		
		params.add(new BasicNameValuePair("siteId", this.siteID));
		params.add(this.enclosedType);
		
		params.add(new BasicNameValuePair("credentialFields[1].name", this.passwordName));
		params.add(new BasicNameValuePair("credentialFields[1].displayName", this.passwordDisplayName));
		params.add(new BasicNameValuePair("credentialFields[1].isEditable", this.passwordIsEditable.toString()));
		params.add(new BasicNameValuePair("credentialFields[1].helpText", this.passwordHelpText));
		params.add(new BasicNameValuePair("credentialFields[1].value", this.passwordValue));
		params.add(new BasicNameValuePair("credentialFields[1].valueIdentifier", this.passwordValueIdentifier));
		params.add(new BasicNameValuePair("credentialFields[1].valueMask", this.passwordValueMask));
		params.add(new BasicNameValuePair("credentialFields[1].fieldType.typeName", this.passwordFieldTypeName));
		params.add(new BasicNameValuePair("credentialFields[1].size", this.passwordSize));
		params.add(new BasicNameValuePair("credentialFields[1].maxlength", this.passwordMaxLength));
		
		return params;
	}

}
