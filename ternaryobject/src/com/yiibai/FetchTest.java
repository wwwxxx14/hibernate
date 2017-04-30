package com.yiibai;

import java.util.*;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;

public class FetchTest {
	public static void main(String[] args) {
		// ��5.1.0�汾�У�hibernate����������·�ʽ��ȡ��
		// 1. �������Ͱ�ȫ��׼����ע���࣬���ǵ�ǰӦ�õĵ������󣬲����޸ģ���������Ϊfinal
		// ��configure("cfg/hibernate.cfg.xml")�����У������ָ����Դ·����Ĭ������·����Ѱ����Ϊhibernate.cfg.xml���ļ�
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml").build();
		// 2. ���ݷ���ע���ഴ��һ��Ԫ������Դ����ͬʱ����Ԫ���ݲ�����Ӧ��һ��Ψһ�ĵ�session����
		SessionFactory sessionFactory = new MetadataSources(registry)
				.buildMetadata().buildSessionFactory();

		/**** ����������׼�������濪ʼ���ǵ����ݿ���� ******/
		Session session = sessionFactory.openSession();// �ӻỰ������ȡһ��session

		// creating transaction object
		Transaction t = session.beginTransaction();

		Query query = session.createQuery("from Question ");
		List<Question> list = query.list();

		Iterator<Question> iterator = list.iterator();
		while (iterator.hasNext()) {
			Question question = iterator.next();
			System.out.println("question id:" + question.getId());
			System.out.println("question name:" + question.getName());
			System.out.println("answers.....");
			Map<String, User> map = question.getAnswers();
			Set<Map.Entry<String, User>> set = map.entrySet();

			Iterator<Map.Entry<String, User>> iteratoranswer = set.iterator();
			while (iteratoranswer.hasNext()) {
				Map.Entry<String, User> entry = (Map.Entry<String, User>) iteratoranswer
						.next();
				System.out.println("answer name:" + entry.getKey());
				System.out.println("answer posted by.........");
				User user = entry.getValue();
				System.out.println("username:" + user.getUsername());
				System.out.println("user emailid:" + user.getEmail());
				System.out.println("user country:" + user.getCountry());
			}
		}
		session.close();
	}
}