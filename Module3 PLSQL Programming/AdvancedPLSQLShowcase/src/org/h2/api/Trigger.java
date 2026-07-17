package org.h2.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface Trigger {

	void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type)
			throws SQLException;

	void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException;

	void close() throws SQLException;

	void remove() throws SQLException;

}
