package JMeter.plugins.functional.samplers.websocket.custom.convert;

import java.nio.ByteBuffer;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseRequest;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseResponse;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.LoginRequest;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.LoginResponse;

public class LoginConvert implements BaseConvert{
	
	private static final Logger log = LoggingManager.getLoggerForClass();

	@Override
	public ByteBuffer JsonToPB(JSONObject jsonObject, BaseRequest.Builder request) {
        //the request json in jmeter like this:
        //{"requestType":1, "version":"1.0","content":{"userName":"helloworld","password":"12345678"}}
        JSONObject contentObj = jsonObject.getJSONObject("content");
        String userName = contentObj.getString("userName");
        String password = contentObj.getString("password");
        
        LoginRequest.Builder login = LoginRequest.newBuilder();
        login.setUserName(userName);
        login.setPassword(password);
        
        request.setRequestType(BaseRequest.RequestType.LoginRequest);
        request.setExtension(LoginRequest.request, login.build());
		return ByteBuffer.wrap(request.build().toByteArray());
	}

	@Override
	public String PBToJson(BaseResponse response, JSONObject basejson) {
		ByteString bytes = response.getUnknownFields().getField(LoginResponse.RESPONSE_FIELD_NUMBER).getLengthDelimitedList().get(0);
		LoginResponse loginResponse;
        try {
        	loginResponse = LoginResponse.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
        	log.error("parse has error:", e);
            return basejson.toString();
        }
        if (loginResponse.getResult() == null || loginResponse.getResult().getUserInfo() == null) {
        	return basejson.toString();
        }
        
        String realName = loginResponse.getResult().getUserInfo().getRealName();
        String token = loginResponse.getResult().getUserInfo().getToken();
        JSONObject userInfoJson = new JSONObject();
        userInfoJson.put("token", token);
        userInfoJson.put("realName", realName);
        JSONObject resultJson = new JSONObject();
        resultJson.put("userInfo", userInfoJson);
        basejson.put("result", resultJson);
        return basejson.toJSONString();
        //the response json in jmeter like this:
        //{"msg":"OK","code":0,"responseType":1,"result":{"userInfo":{"token":"123456","realName":"helloworld"}}}
	}

}
