package com.ssitcloud.view.statistics.service.impl;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.service.StatisticsTemplateService;

@Service
public class StatisticsTemplateServiceImpl extends BasicServiceImpl implements
		StatisticsTemplateService {
	private static final String URL_TAKEDATASOURCE = "takeDataSource";
	private static final String URL_STATICSTYPE = "selectStaticsType";
	private static final String URL_BOOKLOCATION = "selectBookLocations";
	private static final String URL_BOOKCIRTYPE = "selectBookCirtypes";
	private static final String URL_BOOKMEDIATYPE = "selectBookMediatypes";
	private static final String URL_SELECTREADERTYPE = "selectReadertype";
	private static final String URL_SELECTALLDEVICETYPE = "selectAllDeviceType";
	private static final String URL_SELECTDEVICE = "selectDeviceByCondition";
	private static final String URL_STATICSMAINTYPE = "queryStatisticsMaintypeList";
	private static final String URL_SELECTMAINFUNS = "selectFunMaindatas"; 
	private static final String URL_SELECTSUBFUNS = "selectFunSubdatas";

	private static final String URL_INSERTSTATISTICSTEMPLATE = "insertStatisticsTemplate";
	private static final String URL_UPDATESTATISTICSTEMPLATE = "updateStatisticsTemplate"; 
	private static final String URL_DELETESTATISTICSTEMPLATE = "deleteStatisticsTemplate"; 
	private static final String URL_SELECTSTATISTICSTEMPLATE = "selectStatisticsTemplate"; 
	private static final String URL_SELECTSTATISTICSTEMPLATES = "selectStatisticsTemplates"; 
	private static final String URL_SELECTSTATISTICSTEMPLATEPAGE = "selectStatisticsTemplatePage";
	private static final String URL_SELECTBYSQL = "selectBySql";
    private static final String URL_SELECTAUTBYSQL = "selectAutBySql";
	private static final String URL_RELTYPE = "queryReltype";
	private static final String URL_RELTYPELIST = "queryReltypeList";
    private static final String URL_SELECTTEMPLATEMENUBYOPERIDX="selectTemplateMenuByOperidx";
	@Override
	public ResultEntity takeDataSource(String req) {
		return postUrl(URL_TAKEDATASOURCE, req);
	}

	@Override
	public ResultEntity selectStaticsType(String req) {
		return postUrl(URL_STATICSTYPE, req);
	}
	
	@Override
	public ResultEntity selectBookLocations(String req) {
		return postUrl(URL_BOOKLOCATION, req);
	}

	@Override
	public ResultEntity selectBookCirtypes(String req) {
		return postUrl(URL_BOOKCIRTYPE, req);
	}

	@Override
	public ResultEntity selectBookMediatypes(String req) {
		return postUrl(URL_BOOKMEDIATYPE, req);
	}
	
	@Override
	public ResultEntity selectReadertype(String req) {
		return postUrl(URL_SELECTREADERTYPE, req);
	}

	@Override
	public ResultEntity selectAllDeviceType(String req) {
		return postUrl(URL_SELECTALLDEVICETYPE, req);
	}

	@Override
	public ResultEntity selectDeviceByCondition(String req) {
		return postUrl(URL_SELECTDEVICE, req);
	}

	@Override
	public ResultEntity queryStatisticsMaintypeList(String req) {
		return postUrl(URL_STATICSMAINTYPE, req);
	}
	
	@Override
	public ResultEntity selectFunMaindatas(String req) {
		return postUrl(URL_SELECTMAINFUNS, req);
	}
	@Override
	public ResultEntity selectFunSubdatas(String req) {
		return postUrl(URL_SELECTSUBFUNS, req);
	}
	@Override
	public ResultEntity insertStatisticsTemplate(String req) {
		return postUrl(URL_INSERTSTATISTICSTEMPLATE, req);
	}
	@Override
	public ResultEntity updateStatisticsTemplate(String req) {
		return postUrl(URL_UPDATESTATISTICSTEMPLATE, req);
	}
	@Override
	public ResultEntity deleteStatisticsTemplate(String req) {
		return postUrl(URL_DELETESTATISTICSTEMPLATE, req);
	}
	@Override
	public ResultEntity queryOneStatisticsTemplate(String req) {
		return postUrl(URL_SELECTSTATISTICSTEMPLATE, req);
	}
	@Override
	public ResultEntity queryStatisticsTemplates(String req) {
		return postUrl(URL_SELECTSTATISTICSTEMPLATES, req);
	}
	@Override
	public ResultEntity selectStatisticsTemplatePage(String req) {
		return postUrl(URL_SELECTSTATISTICSTEMPLATEPAGE, req);
	}

	@Override
	public ResultEntity selectBySql(String req) {
		return postUrl(URL_SELECTBYSQL, req);
	}

    @Override
    public ResultEntity selectAutBySql(String req) {
        return postUrl(URL_SELECTAUTBYSQL, req);
    }

    @Override
	public ResultEntity queryReltype(String req) {
		return postUrl(URL_RELTYPE, req);
	}

	@Override
	public ResultEntity queryReltypeList(String req) {
		return postUrl(URL_RELTYPELIST, req);
	}

    @Override
    public ResultEntity selectTemplateMenuByOperidx(String req) {
        return postUrl(URL_SELECTTEMPLATEMENUBYOPERIDX, req);
    }

}
