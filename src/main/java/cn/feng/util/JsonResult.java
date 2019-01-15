package cn.feng.util;

import lombok.Data;

/**
 * `自定义返回的json对象
 */
@Data
public class JsonResult {
	
	private String message;
	private Boolean result;
	private Object list;
	private int total;
	
	public JsonResult(String message, Boolean result) {
		this.message = message;
		this.result = result;
	}

	public JsonResult(String message, Boolean result,Object list) {
		this.message = message;
		this.result = result;
		this.list = list;
	}
	/**
	 * @param message		返回的信息
	 * @param result		返回成功或失败
	 * @param list
	 * @param total		后台管理需要的集合总条数
	 */
	public JsonResult(String message, Boolean result, Object list, int total) {
		this.message = message;
		this.result = result;
		this.list = list;
		this.total = total;
	}

	public static JsonResult buildSuccessJsonResult(String message){
		return new JsonResult(message,true);
	}

	public static JsonResult buildSuccessJsonResult(String message, Object list){
		return new JsonResult(message,true ,list);
	}

	public static JsonResult buildSuccessJsonResult(String message, Object list, int total){
		return new JsonResult(message,true,list, total);
	}

	public static JsonResult buildFailedJsonResult(String message){
		return new JsonResult(message,false);
	}

}
