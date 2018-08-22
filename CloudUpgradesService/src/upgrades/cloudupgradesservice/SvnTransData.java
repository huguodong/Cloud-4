package upgrades.cloudupgradesservice;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import upgrades.db.DBPoolUtils;
import upgrades.entity.CommonData;
import upgrades.entity.PatchInfoEntity;
import upgrades.service.PatchInfoService;
import upgrades.utils.FtpUtils;

/**
 * SVN登录及数据下载
 * 
 */
public class SvnTransData {
	static {
		DAVRepositoryFactory.setup();
	}

	private SVNClientManager manager;
	private SVNURL repositoryBaseUrl;
	private SVNURL nowUrl;

	public SvnTransData(CommonData _comData) {
		DefaultSVNOptions options = new DefaultSVNOptions();
		manager = SVNClientManager.newInstance(options);
		// manager =
		// SVNClientManager.newInstance(options,"2453878148@qq.com","guochangyue");
		// //如果需要用户名密码
		manager = SVNClientManager.newInstance(options, _comData.getSvnUser(),
				_comData.getSvnPwd());
		try {
			// repositoryBaseUrl = SVNURL
			// .parseURIDecoded("http://172.16.0.28/repos/repo_software/DEV-Center");
			// // 传入svn地址

			repositoryBaseUrl = SVNURL.parseURIDecoded(_comData
					.getDownloadPath()); // 传入svn地址
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int downLoadFile(String strPubPath, String strLocalPath,
			String strVersionPath) {
		int nRet = -1;
		try {
			nowUrl = SVNURL.parseURIDecoded(repositoryBaseUrl + "/"
					+ strVersionPath); // 传入svn地址
			SVNLogClient logClient = manager.getLogClient();
			strLocalPath += "/" + strVersionPath;
			File localPath = new File(strLocalPath); // co出來的文件存放目錄
			if (!localPath.exists() && !localPath.isDirectory()) {
				try {
					localPath.mkdir();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// svn list
			DirEntryHandler handler = new DirEntryHandler();
			// 在svn时对每个文件目录的处理，实现ISVNDirEntryHandler接口
			logClient.doList(nowUrl, SVNRevision.HEAD, SVNRevision.HEAD, false,
					true, handler); // 列出当前svn地址的目录，对每个文件进行处理

			// svn co
			UpdateEventHandler svnEventHandler = new UpdateEventHandler(); // svn
																			// co时对每个文件的处理
			SVNUpdateClient client = manager.getUpdateClient();
			client.setIgnoreExternals(true);

			client.setEventHandler(svnEventHandler);

			client.doCheckout(nowUrl, localPath, SVNRevision.HEAD,
					SVNRevision.HEAD, SVNDepth.INFINITY, false);

			// svn update
			client.setIgnoreExternals(true);
			client.setEventHandler((ISVNEventHandler) svnEventHandler);
			nRet = 0;
		} catch (SVNException ex) {
			System.out.println("路径异常" + ex.getMessage());
		}
		return nRet;

	}

	public static void main(String[] args) throws SVNException {
		DBPoolUtils myDBPool = DBPoolUtils.getInstance();
		Connection con = myDBPool.getConnection();
		CommonData _comData = DBPoolUtils.getCommonData();
		SvnTransData svntest = new SvnTransData(_comData);
		List<PatchInfoEntity> myData = new ArrayList<PatchInfoEntity>();
		PatchInfoService patchInfoService = new PatchInfoService();
		int nRet = 1;
		while (true) {
			try {
				Thread.sleep(5000);
				//System.out.println("~~~After Sleep~~~");
			} catch (InterruptedException e) {
				System.err.println("Sleep ERR:"+e);
			}
			// 取得新增或修改的升级包的信息
			myData = patchInfoService.selPatchInfo(con);
			
			for (PatchInfoEntity myEntity : myData) {
				System.out.println(myEntity.getPatch_version() + " ----> "+ myEntity.getPatch_desc());
				// 下载升级包内容
				nRet = svntest.downLoadFile(_comData.getPubPath(),
						_comData.getLocalPath(), myEntity.getPatch_version());
				if (nRet == 0) {
					//下载
					String path=_comData.getLocalPath()+myEntity.getPatch_version();
					System.out.println(path);
					File f=new File(path);
					if(!f.exists()){
						f.mkdirs();
					}
					File[] files=f.listFiles();
					FtpUtils.uploadFileUpgrades(files,myEntity.getPatch_version());
					// 更新升级路径
					myEntity.setPatch_directory(_comData.getPubPath() + "/"
							+ myEntity.getPatch_version());
					patchInfoService.updPatchInfo(myEntity, con);
				}
			}
			
		}

	}
}
