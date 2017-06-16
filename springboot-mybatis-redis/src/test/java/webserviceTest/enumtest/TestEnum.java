package webserviceTest.enumtest;

public enum TestEnum {
	
	red("红色"),
	bule("蓝色"),
	yellow("黄色");
	
	private String name;

	TestEnum(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
