package com.ketu.test.junit;

import java.beans.XMLDecoder;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import sun.misc.Lock;

import com.ssitcloud.operlog.bookenum.EnumClass;

public class Test {
	public static void main2(String[] args) {
		Query q = new Query();

		q.addCriteria(Criteria.where("1"));
		q.addCriteria(Criteria.where("2"));
		q.addCriteria(Criteria.where("3"));
		q.addCriteria(Criteria.where("4"));
		q.addCriteria(Criteria.where("5"));
		System.out.println(q.toString());

	}

	public static void main(String[] args) {

	}

	public static void main1(String[] args) {
		Manager m = new Manager();
		// new XMLDecoder();
		System.out.println(System.currentTimeMillis());
		new Thread(m).start();
		new Thread(m).start();
		new Thread(m).start();
		new Thread(m).start();

	}
}

class Manager extends Thread {
	long time = System.currentTimeMillis();
	Lock l = new Lock();

	int dosomething(int x) {

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().toString() + " " + x--);

		return x;
	}

	@Override
	public void run() {
		int i = 50;
		while (i > 0) {
			i = dosomething(i);
		}
		if (i <= 0) {
			System.out.println(System.currentTimeMillis() - time);
		}
	}

}