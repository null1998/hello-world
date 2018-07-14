package server;

public class ToJson {

}
class Users{
	private String id;
	private String username;
	private String password;
	private String nickname;
	private String longitude;
	private String latitude;
	private String intro;
	private String sex;
	
	public String getId(){
		return id;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public String getNickame(){
		return nickname;
	}
	public String getLongitude(){
		return longitude;
	}
	public String getLatitude(){
		return latitude;
	}
	public String getIntro(){
		return intro;
	}
	public String getSex(){
		return sex;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public void setUsername(String username){
		this.username=username;
	}
    public void setPassword(String password){
		this.password=password;
	}
    public void setNickname(String nickname){
		this.nickname=nickname;
	}
    public void setLongitude(String longitude){
    	this.longitude=longitude;
    }
    public void setLatitude(String latitude){
    	this.latitude=latitude;
    }
    public void setIntro(String intro){
    	this.intro=intro;
    }
    public void setSex(String sex){
    	this.sex=sex;
    }
}