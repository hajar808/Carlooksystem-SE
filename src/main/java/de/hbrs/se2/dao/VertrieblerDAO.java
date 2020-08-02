package de.hbrs.se2.dao;


import de.hbrs.se2.dao.entities.Vertriebler;
import de.hbrs.se2.exception.DataBaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VertrieblerDAO extends AbstractDAO {


    public VertrieblerDAO() throws DataBaseException {
        super();
    }

    public void create(Vertriebler vertriebler) {

        try {
            String sql = "INSERT INTO carlooksystem.vertriebler (email, role_id, passwort, name) VALUES(?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, vertriebler.getEmail());
            statement.setInt(2, 2);
            statement.setString(3, vertriebler.getPasswort());
            statement.setString(4, vertriebler.getName());

            boolean success = statement.execute();
            if (!success) {
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Vertriebler readById(String email) {

        try {
            String sql = "SELECT * FROM carlooksystem.vertriebler WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Vertriebler vertriebler = new Vertriebler();

                vertriebler.setEmail(result.getString("email"));
                vertriebler.setRole_id(result.getInt("role_id"));
                vertriebler.setPasswort(result.getString("passwort"));
                vertriebler.setName(result.getString("name"));

                return vertriebler;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Vertriebler vertriebler) {
        try {

            String sql = "UPDATE carlooksystem.vertriebler SET  role_id=?, passwort=?, name=? WHERE email=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, vertriebler.getRole_id());
            statement.setString(2, vertriebler.getPasswort());
            statement.setString(3, vertriebler.getName());
            statement.setString(4, vertriebler.getEmail());

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String email){
        try{

            String sql ="DELETE FROM carlooksystem.vertriebler WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,email);

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
