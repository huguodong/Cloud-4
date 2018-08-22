package com.ssitcloud.dbstatistics.service;


import com.ssitcloud.common.entity.ResultEntity;


public interface MainPageService {

	ResultEntity countCardissueLog(String req);

	ResultEntity countLoanLog(String req);

	ResultEntity countFinanceLog(String req);

	ResultEntity countVisitorLog(String req);
   
}
