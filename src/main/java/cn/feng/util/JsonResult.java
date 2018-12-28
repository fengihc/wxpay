package cn.feng.util;

import lombok.Data;

/**
 * `自定义返回的json对象
 */
@Data
public class JsonResult {
	
	private String message;
	private Boolean result;
	private Object object;
	private int total;

	/**
	 * @param message		返回的信息
	 * @param result		返回成功或失败
	 * @param object
	 * @param total		后台管理需要的集合总条数
	 */
	public JsonResult(String message, Boolean result, Object object, int total) {
		super();
		this.message = message;
		this.result = result;
		this.object = object;
		this.total = total;
	}

	public static JsonResult buildSuccessJsonResult(String message){
		return new JsonResult(message,true,null,0);
	}

	public static JsonResult buildSuccessJsonResult(String message,Object object,int total){
		return new JsonResult(message,true,object, total);
	}

	public static JsonResult buildSuccessJsonResult(String message,Object object){
		return new JsonResult(message,true,object,0);
	}

	public static JsonResult buildFailedJsonResult(String message){
		return new JsonResult(message,false,null,0);
	}

	public static JsonResult buildFailedJsonResult(String message,Object object){
		return new JsonResult(message,false,object,0);
	}

}
