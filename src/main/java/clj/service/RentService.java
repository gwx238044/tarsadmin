package clj.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clj.dao.RentDAO;
import clj.entity.BookEntity;
import clj.entity.RecordEntity;
import clj.utils.HttpResponse;
import clj.vo.BookVO;
import clj.vo.RecordVO;

@Service
public class RentService {

	
	@Autowired
	private RentDAO rentDAO;

	

	public HttpResponse<List<BookVO>> query(String bookName, String bookAuthor) {
		List<BookEntity> query = rentDAO.query(bookName,bookAuthor);
		if(query==null) {
			return new HttpResponse<> ("书名或作者名不正确");
		}
		List<BookVO> list=new ArrayList();
		
		
		query.stream().forEach(entity->{
			BookVO book=new BookVO();
			book.setBookAuthor(entity.getBookAuther());
			book.setBookName(entity.getBookName());
			book.setId(entity.getId());
			book.setType(entity.getType());
			book.setNum(entity.getNum());
			book.setSurplusNum(entity.getSurplusNum());
			list.add(book);
		});
		
		return new HttpResponse<List<BookVO>> (list);
	}



	public HttpResponse<List<BookVO>> list() {
		List<BookEntity> list = rentDAO.list();
		if(list==null) {
			return new HttpResponse<> ("数据库无数据");
		}
		
		List<BookVO> bookList=new ArrayList();
		 
		list.stream().forEach(entity->{
			BookVO	 book=new BookVO();
			book.setBookName(entity.getBookName());
			book.setBookAuthor(entity.getBookAuther());
			book.setId(entity.getId());
			book.setNum(entity.getNum());
			book.setSurplusNum(entity.getSurplusNum());
			book.setType(entity.getType());
			bookList.add(book);
			
		});
		return new HttpResponse<List<BookVO>> (bookList);
	}



	public HttpResponse<Boolean> rent(String userName,Integer id) {
		Boolean rent = rentDAO.rent(userName,id);
		if(!rent) {
			return new HttpResponse<>("借书失败");
		}
		return new HttpResponse<Boolean>(rent);
	}



	public HttpResponse<List<RecordVO>>  queryRecord(String userName) {
		List<RecordEntity> queryRecord = rentDAO.queryRecord(userName);
		if(queryRecord==null) {
			return new HttpResponse<>("此人无记录");
		}
		List<RecordVO> list=new ArrayList();
		queryRecord.stream().forEach(entity->{
			RecordVO vo=new RecordVO();
			  vo.setId(entity.getId());
			  vo.setBookName(entity.getBookName());
			  vo.setName(entity.getName());
			  vo.setOutTime(entity.getOutTime());
			  vo.setStatus(entity.getStatus());
			  list.add(vo);
			
		});
	  
	  return new HttpResponse<List<RecordVO>> (list);
		
	}

}
