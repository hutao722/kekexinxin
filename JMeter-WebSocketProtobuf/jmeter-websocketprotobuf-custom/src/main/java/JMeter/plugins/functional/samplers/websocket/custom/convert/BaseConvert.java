package JMeter.plugins.functional.samplers.websocket.custom.convert;

import java.nio.ByteBuffer;

import com.alibaba.fastjson.JSONObject;

import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseRequest;
import JMeter.plugins.functional.samplers.websocket.custom.proto.Demo.BaseResponse;

public interface BaseConvert {
	
	ByteBuffer JsonToPB(JSONObject jsonObject, BaseRequest.Builder request);
	
	String PBToJson(BaseResponse response, JSONObject basejson);
}
