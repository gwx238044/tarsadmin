package clj.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import clj.entity.BookEntity;
import clj.entity.RecordEntity;


@Repository
public class RentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<BookEntity> query(String bookName, String bookAuthor) {
		Session session=sessionFactory.openSession();
		try {
			//利用sql语句查询
//			NativeQuery<BookEntity> createNativeQuery = session.createNativeQuery("select * from tb_book_info where book_name='"+bookName+"' and book_auther='"+bookAuthor+"'and isactive=1;", BookEntity.class);
//			BookEntity uniqueResult = createNativeQuery.uniqueResult();
//			return uniqueResult;
			//利用hql语句查询
			Query<BookEntity> createQuery = session.createQuery("from BookEntity where bookName=:bookName and bookAuther=:bookAuthor and isactive=1",BookEntity.class);
			createQuery.setParameter("bookName", bookName);
			createQuery.setParameter("bookAuthor", bookAuthor);
			 List<BookEntity> resultList = createQuery.getResultList();
			return resultList;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		
		return null;
	}

	public List<BookEntity> list() {
		Session session=sessionFactory.openSession();
		try {
			List<BookEntity> list = session.createQuery("from BookEntity",BookEntity.class).list();
			return list;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return null;	
	}

	public Boolean rent(String userName,Integer id) {
		Session session=sessionFactory.openSession();
		Transaction tr=null;
		try {
			tr=session.beginTransaction();
			String sql="update tb_book_info set surplus_num= surplus_num-1 where id='"+id+"'and isactive=1 and surplus_num-"+1+">=0";
			NativeQuery createNativeQuery = session.createNativeQuery(sql);
			int executeUpdate = createNativeQuery.executeUpdate();
			Boolean rentFlag=true;
			if(executeUpdate<1) {
				rentFlag=false;
				System.out.println("库存不足");
				tr.commit();
				return rentFlag;
				
			}
			BookEntity bookEntity = session.get(BookEntity.class, id);
			String bookName = bookEntity.getBookName();
			
			RecordEntity record=new RecordEntity();
			record.setBookName(bookEntity.getBookName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.now();
			String formattedDateTime = dateTime.format(formatter);
			record.setInsertTime(formattedDateTime);
			record.setIsactive(true);
			record.setName(userName);
			//LocalDateTime的toString方法是带T的，不是格式化的时间，但他还是时间格式，数据库的outtime是datetime格式的他能解析到带T的时间格式，所以会把
			//这个带T的时间格式转化成datetime的时间格式。当查出的实体类的outtime为String的时候，会直接展示数据库的格式。
			
			record.setOutTime(formattedDateTime);
			record.setStatus(2);
			record.setUpdateTime(formattedDateTime);
			session.save(record);
			
			tr.commit();
			return rentFlag;
			
		}catch(Exception e) {
			if(tr!=null) {
				tr.rollback();
			}
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return false;
		
	}

	public List<RecordEntity> queryRecord(String userName) {
		Session session=sessionFactory.openSession();
		try {
			Query<RecordEntity> createQuery = session.createQuery("from RecordEntity where name=:name ",RecordEntity.class);
			createQuery.setParameter("name", userName);
			List<RecordEntity> list = createQuery.list();
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return null;
		
	}
	

}
