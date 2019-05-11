package utilities;

public class Culture {
	
	private String cultureName;
	
	public Culture(String cultureName) {
		this.cultureName = cultureName;
	}
	
	@Override
	public String toString() {
		return cultureName;
	}
	
	public String getCultureName() {
		return cultureName;
	}

}
