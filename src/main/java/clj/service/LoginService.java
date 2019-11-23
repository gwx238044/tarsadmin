package clj.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clj.dao.LoginDAO;
import clj.entity.CustomerLoginEntity;
import clj.utils.HttpResponse;
import clj.vo.BookVO;
import clj.vo.TokenVO;

@Service

public class LoginService {
	

	@Autowired
	private LoginDAO loginDAO;

	public HttpResponse<TokenVO> login(String name, String passWord) {
		CustomerLoginEntity login = loginDAO.login(name,passWord);
		if(login==null) {
		return new HttpResponse<>("用户名和密码不存在");
			
		}
		Long id = login.getId();
		String  token = UUID.randomUUID().toString();
		loginDAO.updateToken(token,id);
		TokenVO  tokenVO=new TokenVO();
		tokenVO.setToken(token);
		return new HttpResponse<TokenVO>(tokenVO);
		
	}

	public Boolean token(String token) {
		CustomerLoginEntity tokenTest = getUserByToken(token);
 		boolean tokenFlag=true;
		if(tokenTest==null) {
			return false;
		}else {
			return tokenFlag;
		}
		
		
	}
	
	
	public CustomerLoginEntity getUserByToken(String token) {
		return loginDAO.tokenTest(token);
		
	}
	
}
