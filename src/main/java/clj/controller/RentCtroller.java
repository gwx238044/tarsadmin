package clj.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import clj.dto.BookRentDTO;
import clj.dto.QueryDTO;
import clj.entity.CustomerLoginEntity;
import clj.service.LoginService;
import clj.service.RentService;
import clj.utils.HttpResponse;
import clj.vo.BookVO;
import clj.vo.RecordVO;

@RequestMapping("rent")
@RestController
public class RentCtroller {
	@Autowired
	private RentService rentservice;
	@Autowired
	private LoginService loginService;

	@GetMapping("query")
	@ResponseBody
	public HttpResponse<List<BookVO>> getDetailById(@RequestHeader(name = "token") String token, QueryDTO dto) {
		String bookAuthor = dto.getBookAuthor();
		String bookName = dto.getBookName();
		Boolean tokenFlag = loginService.token(token);
		if (!tokenFlag) {
			return new HttpResponse<>("token失效");
		}
		if (StringUtils.isBlank(bookName)) {
			return new HttpResponse<>("书名不能为空");
		}
		if (StringUtils.isBlank(bookAuthor)) {
			return new HttpResponse<>("作者不能为空");
		}
		HttpResponse<List<BookVO>> query = rentservice.query(bookName, bookAuthor);
		return query;

	}

	@GetMapping("list")
	@ResponseBody
	public HttpResponse<List<BookVO>> getBookList(@RequestHeader(name = "token") String token) {
		Boolean tokenFlag = loginService.token(token);
		if (!tokenFlag) {
			return new HttpResponse<>("token失效");
		}

		return rentservice.list();

	}

	@PostMapping("bookRent")
	@ResponseBody
	public HttpResponse<Boolean> rent(@RequestHeader(name = "token") String token, @RequestBody BookRentDTO dto) {
		Integer id = dto.getId();
		CustomerLoginEntity userByToken = loginService.getUserByToken(token);
		if (userByToken == null) {
			return new HttpResponse<>("token失效");
		}

		return rentservice.rent(userByToken.getName(), id);

	}

	@GetMapping("record")
	@ResponseBody
	public HttpResponse<List<RecordVO>> queryRecord(@RequestHeader(name = "token") String token) {
		CustomerLoginEntity userByToken = loginService.getUserByToken(token);
		if (userByToken == null) {
			return new HttpResponse<>("token失效");
		}

		return rentservice.queryRecord(userByToken.getName());
	}

}
