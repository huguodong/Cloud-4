package upgrades.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import upgrades.db.DBPoolUtils;
import upgrades.entity.PatchInfoEntity;

public class PatchInfoService {
	private static Logger logger = Logger.getLogger(PatchInfoService.class);
	public List<PatchInfoEntity> selPatchInfo(Connection con) {
		String sql = "SELECT `patch_idx`, `patch_version`, `patch_desc`, `patch_type`,`restrict_info`, `patch_directory`,";
		  sql+="`create_time` FROM `ssitcloud_device`.`patch_info` pin,`ssitcloud_device`.`table_change_tri` tct ";
		  sql+="where pin.patch_idx=tct.data_idx and tct.table_name='patch_info' and tct.requestTime is null and (pin.patch_directory is null or LENGTH(trim(pin.patch_directory))<1 )";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PatchInfoEntity> myList=new ArrayList<PatchInfoEntity>();
		PatchInfoEntity patchInfoEntity=new PatchInfoEntity();
		try {
			// 根据sql语句创建预处理对象
			pstmt = con.prepareStatement(sql);
			// 为占位符赋值

			// 执行更新
			rs = pstmt.executeQuery();
			while(rs.next()) {
				patchInfoEntity=new PatchInfoEntity();
				patchInfoEntity.setCreate_time(rs.getString(7));
				patchInfoEntity.setPatch_desc(rs.getString(3));
				patchInfoEntity.setPatch_idx(rs.getInt(1));
				patchInfoEntity.setPatch_type(rs.getString(4));
				patchInfoEntity.setPatch_version(rs.getString(2));
				patchInfoEntity.setRestrict_info(rs.getString(5));
				myList.add(patchInfoEntity);
			}
			return myList;
		} catch (Exception e) {
			logger.error("selPatchInfo出错", e);
			throw new RuntimeException(e);
		}finally{
			DBPoolUtils.closeAll(null, pstmt, rs);
		}
	}

	public int updPatchInfo(PatchInfoEntity patchInfoEntity,Connection con) {
		String sql = "UPDATE `ssitcloud_device`.`patch_info` set	`patch_directory` = ? where `patch_idx` = ?";
		PreparedStatement pstmt = null;
		int nRet=0;
		try {
			// 根据sql语句创建预处理对象
			pstmt = con.prepareStatement(sql);
			// 为占位符赋值
			pstmt.setString(1, patchInfoEntity.getPatch_directory());
			pstmt.setInt(2, patchInfoEntity.getPatch_idx());
			// 执行更新
			nRet=pstmt.executeUpdate();

		} catch (Exception e) {
			logger.error("updPatchInfo出错", e);
			throw new RuntimeException(e);
		}finally{
			DBPoolUtils.closeAll(null, pstmt, null);
		}
		return nRet;
	}

}
