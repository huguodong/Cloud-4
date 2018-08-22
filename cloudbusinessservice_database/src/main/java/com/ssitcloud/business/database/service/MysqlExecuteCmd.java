package com.ssitcloud.business.database.service;

import java.io.IOException;

public interface MysqlExecuteCmd {
	Process executeCmd() throws IOException;
}
