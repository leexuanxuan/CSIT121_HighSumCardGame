import java.io.Serializable;

abstract public class User implements Serializable{
	
	private String loginName;
	private String hashPassword; //plain password
	
	public User(String loginName, String password) {
		this.loginName = loginName;
		this.hashPassword = Utility.getHash(password);
	}

	public String getLoginName() {
		return loginName;
	}
	
	public String getPassword() {
		return hashPassword;		// originally return password.
	}
	
	// Change password 
	public void setPassword(String newPassword) {
		this.hashPassword = Utility.getHash(newPassword);
	}
	
	//should be done using HASH algorithm
	public boolean checkPassword(String hashPassword) {
		return this.hashPassword.equals(hashPassword);
	}
	
	public void display() {
		System.out.println(this.loginName+","+this.hashPassword);
	}
}
