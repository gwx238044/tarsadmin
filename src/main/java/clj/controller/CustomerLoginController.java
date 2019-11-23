package clj.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import clj.dto.LoginDTO;
import clj.service.LoginService;
import clj.utils.HttpResponse;
import clj.vo.TokenVO;

/**
 * 登陆接口
 * @author ppd-03020342
 *
 */
@Controller
public class CustomerLoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("login")
	@ResponseBody
	/**
	 * @ResponseBody的作用其实是将java对象转为json格式的数据。

@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
注意：在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，
他的效果等同于通过response对象输出指定格式的数据。

@ResponseBody是作用在方法上的，@ResponseBody 表示该方法的返回结果直接写入 HTTP response body 中，
一般在异步获取数据时使用【也就是AJAX】。
注意：在使用 @RequestMapping后，返回值通常解析为跳转路径，但是加上 @ResponseBody 后返回结果不会被解析为跳转路径，
而是直接写入 HTTP response body 中。 比如异步获取 json 数据，加上 @ResponseBody 后，会直接返回 json 数据。
@RequestBody 将 HTTP 请求正文插入方法中，使用适合的 HttpMessageConverter 将请求体写入某个对象。

	 * @param name
	 * @param passWord
	 * @return
	 * post的接收参数要用实体类接收
	 */
	public HttpResponse<TokenVO> login(@RequestBody LoginDTO loginDTO){
		if(StringUtils.isBlank(loginDTO.getName())) {
			return new HttpResponse<>("用户名不能为空");
		}
		if(StringUtils.isBlank(loginDTO.getPassWord())) {
			return new HttpResponse<>("密码不能为空");
		}
		
		return loginService.login(loginDTO.getName(),loginDTO.getPassWord());
		
	}

}
