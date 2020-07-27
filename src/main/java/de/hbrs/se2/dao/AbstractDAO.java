package de.hbrs.se2.dao;

import java.sql.Connection;

public abstract  class AbstractDAO {
    public Connection conn ;

    public AbstractDAO(){
        conn = JDBCConnection.getInstance().openConnection();
    }


}
