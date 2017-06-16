package webserviceTest.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String username;
	
	private String sex;
	
	private int age;

	public User(String username, String sex, int age) {
		this.username = username;
		this.sex = sex;
		this.age = age;
	}
	
	public User(){
		super();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
