package upgrades.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import upgrades.entity.CommonData;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 连接池
 * @author gcy
 * 2016-5-12
 */
public class DBPoolUtils {
	private static CommonData _comData;  //下载及发布相关信息
	private static DBPoolUtils dbPool;
	private ComboPooledDataSource dataSource;
	
	ResourceBundle bundle=ResourceBundle.getBundle("config");
	static {
		dbPool = new DBPoolUtils();
	}

	public DBPoolUtils() {
		try {
			dataSource = new ComboPooledDataSource();
			_comData=new CommonData();
			dataSource.setUser(bundle.getString("user"));
			dataSource.setPassword(bundle.getString("password"));
			dataSource.setJdbcUrl(bundle.getString("url"));
			dataSource.setDriverClass(bundle.getString("driver"));
			// 设置初始连接池的大小！
			dataSource.setInitialPoolSize(Integer.valueOf(bundle.getString("initialPoolSize")));
			// 设置连接池的最小值！
			dataSource.setMinPoolSize(Integer.valueOf(bundle.getString("minPoolSize")));
			// 设置连接池的最大值！
			dataSource.setMaxPoolSize(Integer.valueOf(bundle.getString("maxPoolSize")));
			// 设置连接池的最大空闲时间！
			dataSource.setMaxIdleTime(Integer.valueOf(bundle.getString("maxIdleTime")));
			
			//5秒之内获取不到连接抛异常
			dataSource.setCheckoutTimeout(5000);
			
			//回收不可用的连接IdleConnectionTestPeriod
			dataSource.setIdleConnectionTestPeriod(1800);
			
			//15秒之内不回收则强制关闭
			//dataSource.setUnreturnedConnectionTimeout(60);
			//升级路径
			_comData.setPubPath(bundle.getString("pubPath"));
			//下载路径
			_comData.setDownloadPath(bundle.getString("downloadPath"));
			//SVN用户名
			_comData.setSvnUser(bundle.getString("svnUser"));
			//SVN密码
			_comData.setSvnPwd(bundle.getString("svnPwd"));
			//本地存放路径
			_comData.setLocalPath(bundle.getString("localPath"));
		} catch (PropertyVetoException e) {			
			throw new RuntimeException(e);
		}
	}

	public final static DBPoolUtils getInstance() {
		return dbPool;
	}
	public final static CommonData getCommonData(){
		return _comData;
	}
	public final Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("无法从数据源获取连接 ", e);
		}
	}
	
	/**
	 * 释放连接池
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @throws SQLException
	 */
	public static void closeAll(Connection conn,PreparedStatement pstmt,ResultSet rs) 
	{
		try {
			if(rs!=null)
			{
				rs.close();
			}
			if(pstmt!=null)
			{
				pstmt.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
