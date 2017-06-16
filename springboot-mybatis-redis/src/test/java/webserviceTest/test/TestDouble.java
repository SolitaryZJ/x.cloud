package webserviceTest.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class TestDouble {

	@Test
	public void test01(){
		double[][] d;
		String s = "1,2;3,4,5;6,7,8";
		String[] sFirst = s.split(";");
		d = new double[sFirst.length][];
		for (int i = 0; i < sFirst.length; i++) {
			String[] sSecond = sFirst[i].split(",");
			d[i] = new double[sSecond.length];
			for (int j = 0; j < sSecond.length; j++) {
				d[i][j] = Double.parseDouble(sSecond[j]);
			}
		}
		
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				System.out.print(d[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void test02() throws IOException{
		File f = new File("d:\\zhangjie","test.txt");
		if(f.exists()){
			System.out.println(f.getAbsolutePath());
			System.out.println(f.length());
		}else{
			f.createNewFile();
		}
	}
	
	@Test
	public void test03() throws IOException{
		File f = new File("d:/zhangjie");
		tree(f, 0);
	}
	
	public void tree(File f, int level){
		String preStr = "";
		for (int i = 0; i < level; i++) {
			preStr += "  ";
		}
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			System.out.println(preStr + files[i].getName());
			if(files[i].isDirectory()){
				tree(files[i], level+1);
			}
		}
	}
	
	
}
