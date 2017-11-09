package JMeter.plugins.functional.samplers.websocket.custom;

import java.nio.ByteBuffer;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.alibaba.fastjson.JSONObject;

import JMeter.plugins.functional.samplers.websocket.custom.convert.InitConvert;
import JMeter.plugins.functional.samplers.websocket.custom.convert.LoginConvert;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseRequest;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseResponse;

public class MessageHandler {
	
	private static final Logger log = LoggingManager.getLoggerForClass();
	
	/**
	 * convert the request json to pb
	 * @param message
	 * @return
	 */
	public static ByteBuffer getPBData(String message) {
		JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(message);
        } catch (Exception e) {
        	log.error("request json has error:",e);
            return null;
        }
        String version = jsonObject.getString("version");
        int requestType = jsonObject.getIntValue("requestType");
        
        BaseRequest.Builder request = BaseRequest.newBuilder();
        request.setVersion(version);
        
        if (requestType ==  BaseRequest.RequestType.InitRequest_VALUE) {
    		return new InitConvert().JsonToPB(jsonObject, request);
        } 
        if (requestType ==  BaseRequest.RequestType.LoginRequest_VALUE) {
    		return new LoginConvert().JsonToPB(jsonObject, request);
        } 

		return ByteBuffer.wrap(request.build().toByteArray());
	}
	
	/**
	 * convert the response pb to json
	 * @param buf
	 * @return
	 */
	public static String getJsonFromResult(byte[] buf) {
		BaseResponse response;
		try {
			response = BaseResponse.parseFrom(buf);
		} catch (Exception e) {
			log.error("response json has error:",e);
            return "";
		}
		int code = response.getCode();
		String msg = response.getMsg();
		int responseType = response.getResponseType().getNumber();
		
		JSONObject basejson = new JSONObject();
		basejson.put("code", code);
		basejson.put("msg", msg);
		basejson.put("responseType", responseType);
		
		if (responseType == BaseResponse.ResponseType.LoginResponse_VALUE) {
			return new LoginConvert().PBToJson(response, basejson);
		}
		return basejson.toJSONString();
	}
}
