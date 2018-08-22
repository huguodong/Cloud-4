package upgrades.entity;
/**
 * 升级相关的参数信息
 * @author gcy
 * 20160508
 *
 */
public class CommonData {
	private String downloadPath;      //SVN下载路径
	private String svnUser;           //SVN用户名
	private String svnPwd;            //SVN密码
	private String pubPath;           //FTP发布路径 
	private String localPath;         //本地存放路径 
	
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getSvnUser() {
		return svnUser;
	}
	public void setSvnUser(String svnUser) {
		this.svnUser = svnUser;
	}
	public String getSvnPwd() {
		return svnPwd;
	}
	public void setSvnPwd(String svnPwd) {
		this.svnPwd = svnPwd;
	}
	public String getPubPath() {
		return pubPath;
	}
	public void setPubPath(String pubPath) {
		this.pubPath = pubPath;
	}
	
}
