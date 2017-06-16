package webserviceTest.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import webserviceTest.entity.User;

@WebService
public interface MathService {

	@WebResult(name = "addResult")
	public int add(@WebParam(name = "a")int a, @WebParam(name = "b")int b);
	
	@WebResult(name = "minusResult")
	public int minus(@WebParam(name = "a")int a, @WebParam(name = "b")int b);
	
	@WebResult(name = "user")
	public User addUser(@WebParam(name = "user") User user);
	
	@WebResult(name = "user")
	public List<User> list();
}
