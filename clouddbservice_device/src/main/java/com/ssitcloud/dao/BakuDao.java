package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.BakupDataEntity;

public interface BakuDao {

	List<BakupDataEntity> queryBakDataInfo(Map<String, Object> m);

	BakupDataEntity queryBakDataInfoByIdx(Map<String, Object> m);

	int delBakDataInfoByIdx(Map<String, Object> m);

	int updBakDataInfoByIdx(Map<String, Object> m);

	int insertBakDataInfo(BakupDataEntity m);

	List<BakupDataEntity> queryBakDataInfoByPage(BakupDataEntity b);

	int updBakDataInfoToExist(List<Integer> needUpdToExistList);

	int updBakDataInfoToNotExist(List<Integer> needUpdToNotExistList);

	int delBakDataInfoByPath(List<String> list);

	Map<String, Object> getLastLibBakUpTime(Map<String, Object> map);

	int updBakDataInfoByFilePath(Map<String, Object> m);

}
