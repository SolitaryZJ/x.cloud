package webserviceTest.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import webserviceTest.enumtest.TestEnum;

public class TestEnum01 {

	public enum MyColor{red, bule,yellow};
	
	@Test
	public void test01(){
		System.out.println(TestEnum.bule.getName());
		System.out.println(MyColor.red);
	}
	
	private  Jedis jedis = null;  
	@Test
    public  void testJedis() {  
        jedis = new Jedis("192.168.66.128",6379);    
        jedis.set("username", "zhangjie");    
        System.out.println(jedis.get("username")); //IluckySi    
    }  
}
