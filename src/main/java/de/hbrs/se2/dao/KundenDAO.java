package de.hbrs.se2.dao;


import de.hbrs.se2.dao.entities.Kunde;
import de.hbrs.se2.exception.DataBaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KundenDAO extends AbstractDAO{


    public KundenDAO() throws DataBaseException {
        super();

    }


    public void create(Kunde kunde){

        String sql ="INSERT INTO carlooksystem.kunde(email,role_id, passwort, name) VALUES(?,?,?,?)";

        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);

            statement.setString(1, kunde.getEmail());
            statement.setInt(2, 1);
            statement.setString(3, kunde.getPasswort());
            statement.setString(4, kunde.getName());

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public Kunde readByEmail(String email){

        try{
            String sql ="SELECT * FROM carlooksystem.kunde WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result =statement.executeQuery();

            if(result.next()){
                Kunde kunde = new Kunde();

                kunde.setEmail(result.getString("email"));
                kunde.setRole_id(result.getInt("role_id"));
                kunde.setPasswort(result.getString("passwort"));
                kunde.setName(result.getString("name"));

                return kunde;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void update(Kunde kunde){

        try{
            String sql ="UPDATE carlooksystem.kunde SET  role_id=?, passwort=?, name=? WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, kunde.getRole_id());
            statement.setString(2,kunde.getPasswort());
            statement.setString(3, kunde.getName());
            statement.setString(4, kunde.getEmail());

            boolean success = statement.execute();
            if(!success){

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String email){

        try{
            String sql = "DELETE FROM carlooksystem.kunde WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);

            boolean success = statement.execute();
            if(!success){
                //TODO EXception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
