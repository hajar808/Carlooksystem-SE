package de.hbrs.se2.dao;


import de.hbrs.se2.dao.entities.Reservierung;
import de.hbrs.se2.exception.DataBaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservierungsDAO extends AbstractDAO {


    public ReservierungsDAO() throws DataBaseException {
        super();
    }

    public void create(Reservierung reservierung){

        try{
            String sql ="INSERT INTO carlooksystem.reservierung (email, auto_id) VALUES(?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, reservierung.getEmail());
            statement.setInt(2, reservierung.getAuto_id());

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Reservierung readById(int id){

        try{

            String sql ="SELECT * FROM carlooksystem.reservierung WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                Reservierung reservierung = new Reservierung();

                reservierung.setId(result.getInt("id"));
                reservierung.setAuto_id(result.getInt("auto_id"));
                reservierung.setEmail(result.getString("email"));

                return reservierung;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Reservierung> readByEmail(String email){
        List<Reservierung> list = new ArrayList<>();
        try{

            String sql ="SELECT * FROM carlooksystem.reservierung WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                Reservierung reservierung = new Reservierung();

                reservierung.setId(result.getInt("id"));
                reservierung.setAuto_id(result.getInt("auto_id"));
                reservierung.setEmail(result.getString("email"));

                list.add(reservierung);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;


    }
    public List<Reservierung> readByVertriebler(String email){
        List<Reservierung> list = new ArrayList<>();
        try{

            String sql ="SELECT r.id, r.auto_id, r.email FROM carlooksystem.reservierung As r, carlooksystem.auto As a WHERE a.email =? AND r.auto_id = a.id";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                Reservierung reservierung = new Reservierung();

                reservierung.setId(result.getInt("id"));
                reservierung.setAuto_id(result.getInt("auto_id"));
                reservierung.setEmail(result.getString("email"));

                list.add(reservierung);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public List<Reservierung> readAll(){
        List<Reservierung> list = new ArrayList<>();
        try{

            String sql ="SELECT * FROM carlooksystem.reservierung";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                Reservierung reservierung = new Reservierung();

                reservierung.setId(result.getInt("id"));
                reservierung.setAuto_id(result.getInt("auto_id"));
                reservierung.setEmail(result.getString("email"));

                list.add(reservierung);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;


    }


    public void update(Reservierung reservierung){

        try{
            String sql = "UPDATE carlooksystem.reservierung SET auto_id=?, email=? WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, reservierung.getAuto_id());
            statement.setString(2, reservierung.getEmail());
            statement.setInt(3,reservierung.getId());

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id){
        try{

            String sql = "DELETE FROM carlooksystem.reservierung WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            boolean success = statement.execute();
            if(!success){
                //TODO Exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
