package de.hbrs.se2.dao;


import de.hbrs.se2.dao.entities.Role;
import de.hbrs.se2.exception.DataBaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO extends AbstractDAO {

    public RoleDAO() throws DataBaseException {
        super();

    }

    public void create(Role role){

        try{
            String sql = "INSERT INTO carlooksystem.role( name) VALUES(?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, role.getName());
            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Role readById(int id){
        try{
            String sql ="SELECT * FROM carlooksystem.role WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){

                Role role = new Role();
                role.setId(result.getInt("id"));
                role.setName(result.getString("name"));

                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void update(Role role){

        try{
            String sql = "UPDATE carlooksystem.role SET name=? WHERE id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, role.getName());
            statement.setInt(2, role.getId());

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (int id){
        try {

            String sql = "DELETE FROM carlooksystem.role WHERE id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            boolean success = statement.execute();
            if (!success) {
                //TODO EXception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
