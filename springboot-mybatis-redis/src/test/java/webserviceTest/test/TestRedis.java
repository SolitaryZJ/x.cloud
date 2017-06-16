package webserviceTest.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {

	private Jedis jedis;
	
	@Before
	public void setup(){
		jedis = new Jedis("192.168.66.128", 6379);
		try {
			//服务器密码验证
			jedis.auth("zhangjie"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试redis添加字符串
	 */
	@Test
	public void testString(){
		//将字符串添加
		jedis.set("name", "孙瑞");
		System.out.println(jedis.get("name"));
		
		//拼接
		jedis.append("name", "is sb");
		System.out.println(jedis.get("name"));
		
		//删除
		jedis.del("name");
		System.out.println(jedis.get("name"));
		
		//添加多组键值
		jedis.mset("name","zhnagsa","age","12","passwd","1234");
		jedis.incr("age");
		System.out.println(jedis.get("name")+"-"+jedis.get("age")+"-"+jedis.get("passwd"));
		
	}
	
	/**
	 * 测试redis添加map
	 */
	@Test
	public void testMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "zhangjie");
		map.put("sex", "男");
		map.put("nickname", "jie");
		String hmset = jedis.hmset("user", map);
		System.out.println(hmset);
		List<String> rsmap = jedis.hmget("user","name", "sex", "nickname");
		System.out.println(rsmap);
		
		//删除map中的某个键值
		jedis.hdel("user", "sex");
		System.out.println(jedis.hget("user", "age"));//因为删除了所以返回的是null
		System.out.println(jedis.hlen("user"));//返回key为user的键中值的个数
		System.out.println(jedis.exists("user"));//是否存在key为user的记录，返回boolean
		System.out.println(jedis.hkeys("user"));//返回map对象中所有的key
		System.out.println(jedis.hvals("user"));//返回map对象中所有的value
		
	}
	
	/**
	 * 测试redis添加list
	 */
	@Test
	public void testList(){
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		//存放数据
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		//再取出所有数据jedis.lrange是按范围取值
		//第一个是key，第二个是其实位置，第三个是结束位置，jedis.llen,-1表示获取所有
		System.out.println(jedis.lrange("java framework", 0, -1));
		
		jedis.del("java framework");
		jedis.rpush("java framework","spring");
		jedis.rpush("java framework","struts");
		jedis.rpush("java framework","hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
	}
	
	/**
	 * 测试redis添加set
	 */
	@Test
	public void testSet(){
		try {
			//添加set
			jedis.sadd("account", "zhangjei");
			jedis.sadd("account", "sunrui");
			jedis.sadd("account", "xxxx");
			
			//移除
			jedis.srem("account", "xxxx");
			System.out.println(jedis.smembers("account"));//获取所有加入的value
			System.out.println(jedis.sismember("account", "xxxx"));//判断xxxx是否是user集合的元素
			System.out.println(jedis.srandmember("account"));//获取user中随机一个元素
			System.out.println(jedis.scard("account"));//返回集合元素的个数
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试redis中的排序
	 */
	@Test
	public void testSort(){
		jedis.del("a");
		//注意这里jedis.rpush和lpush都是对list的操作，是一个双向链表
		jedis.rpush("a", "1");
		jedis.lpush("a", "5");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		jedis.lpush("a", "8");
		System.out.println(jedis.lrange("a",0,-1));// [8, 9, 3, 5, 1]  
		System.out.println(jedis.sort("a")); //[1, 3, 5, 8, 9]  //输入排序后结果  
		System.out.println(jedis.lrange("a",0,-1));  
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
