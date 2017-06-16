package webserviceTest.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import webserviceTest.entity.User;
import webserviceTest.service.MathService;

@WebService(endpointInterface="webserviceTest.service.MathService")
public class IMathServiceImp implements MathService{

	public static List<User> users = new ArrayList<User>();
	
	public IMathServiceImp() {
		users.add(new User("admin", "man", 1));
	}
	
	@Override
	public int add(int a, int b) {
		System.out.println("加法运算："+a+"+"+b+"="+(a+b));
		return (a+b);
	}

	@Override
	public int minus(int a, int b) {
		System.out.println("减法运算："+a+"-"+b+"="+(a-b));
		return (a-b);
	}

	@Override
	public User addUser(User user) {
		users.add(user);
		return user;
	}

	@Override
	public List<User> list() {
		return users;
	}

}
