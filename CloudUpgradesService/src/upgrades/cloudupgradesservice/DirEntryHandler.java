package upgrades.cloudupgradesservice;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;

/**
 * 文件下载文件夹路径实例化
 * @author dell
 *
 */

public class DirEntryHandler implements ISVNDirEntryHandler {
	// @Override
	public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
		System.out.println(dirEntry.getRelativePath() + "/"
				+ dirEntry.getName());
	}
}
