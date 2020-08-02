package de.hbrs.se2.dao;

import de.hbrs.se2.exception.DataBaseException;

import java.sql.Connection;

public abstract  class AbstractDAO {
    public Connection conn ;

    public AbstractDAO() throws DataBaseException {
        conn = JDBCConnection.getInstance().openConnection();
    }


}
