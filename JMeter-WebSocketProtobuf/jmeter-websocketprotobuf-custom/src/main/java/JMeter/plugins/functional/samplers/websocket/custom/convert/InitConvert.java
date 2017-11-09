package JMeter.plugins.functional.samplers.websocket.custom.convert;

import java.nio.ByteBuffer;

import com.alibaba.fastjson.JSONObject;

import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseRequest;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseResponse;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.InitRequest;

public class InitConvert implements BaseConvert{

	@Override
	public ByteBuffer JsonToPB(JSONObject jsonObject, BaseRequest.Builder request) {
        //the request json in jmeter dialog like this:
        //{"requestType":0, "version":"2.5","content":{"appType":1}}
        JSONObject contentObj = jsonObject.getJSONObject("content");
        int appType = contentObj.getIntValue("appType");
        
        InitRequest.Builder init = InitRequest.newBuilder();
        init.setAppType(appType);
        
        request.setRequestType(BaseRequest.RequestType.InitRequest);
        request.setExtension(InitRequest.request, init.build());
		return ByteBuffer.wrap(request.build().toByteArray());
	}

	@Override
	public String PBToJson(BaseResponse response, JSONObject basejson) {
		return null;
	}

}
