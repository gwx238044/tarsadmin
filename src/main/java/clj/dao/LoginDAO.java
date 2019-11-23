package clj.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import clj.entity.CustomerLoginEntity;

@Repository
public class LoginDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public CustomerLoginEntity login(String name, String passWord) {
		Session session = sessionFactory.openSession(); 
		try {
			Query<CustomerLoginEntity> createQuery = session.createQuery("from CustomerLoginEntity where name=:name and passWord=:password ",CustomerLoginEntity.class);
			createQuery.setParameter("name", name);
			createQuery.setParameter("password", passWord);
			CustomerLoginEntity uniqueResult = createQuery.uniqueResult();
			
			return uniqueResult;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return null;
		
	}

	public void updateToken(String token, Long id) {
		Transaction beginTransaction = null;
		Session session=sessionFactory.openSession();
		try {
			beginTransaction = session.beginTransaction();
			Query createQuery = session.createQuery("update CustomerLoginEntity set token=:token where id=:id").setParameter("token", token).setParameter("id", id);
			int executeUpdate = createQuery.executeUpdate();
			beginTransaction.commit();
			System.out.println(executeUpdate);
			//第二种更新方法sql语句
			
//		NativeQuery createNativeQuery = session.createNativeQuery("update tb_customer_info set token="+token+"where id="+id);
//		int executeUpdate2 = createNativeQuery.executeUpdate();//保存成功executeUpdate2>=1;
			
		}catch(Exception e) {
			e.printStackTrace();
			if (beginTransaction != null)
			{
				beginTransaction.rollback();
			}
			
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		
	}

	public CustomerLoginEntity tokenTest(String token) {
		Session session=sessionFactory.openSession();
		try {
			Query<CustomerLoginEntity> createQuery = session.createQuery("from CustomerLoginEntity where token=:token and isActive=1",CustomerLoginEntity.class);
			createQuery.setParameter("token", token);
			CustomerLoginEntity uniqueResult = createQuery.uniqueResult();
			return uniqueResult;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				
			}
		}
		return null;

		
	}

}
