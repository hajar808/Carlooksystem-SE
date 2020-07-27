package de.hbrs.se2.dao;


import de.hbrs.se2.dao.entities.Auto;
import de.hbrs.se2.dao.entities.Reservierung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutosDAO  extends AbstractDAO {

    /*
    private JDBCConnection jdbc;
    private Connection conn;
    */

    public AutosDAO(){
        /*
        this.jdbc = JDBCConnection.getInstance();
        conn = jdbc.openConnection();
        */

    }


    public void create(Auto auto) {
        try {
            String sql = "INSERT INTO carlooksystem.auto (email,marke, beschreibung, baujahr) Values(?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,auto.getEmail());
            statement.setString(2, auto.getMarke());
            statement.setString(3,auto.getBeschreibung());
            statement.setString(4, auto.getBaujahr());

            boolean success = statement.execute();
            if(!success) {
                // TODO Exception werfen
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }






    public void update(Auto auto){

        String sql = "UPDATE carlooksystem.auto SET email=?, marke=?, beschreibung=?, baujahr=? WHERE id =?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, auto.getEmail());
            statement.setString(2, auto.getMarke());
            statement.setString(3, auto.getBeschreibung());
            statement.setString(4,auto.getBaujahr());
            statement.setInt(5,auto.getId());
            boolean success = statement.execute();
            if(!success) {
                // TODO Exception werfen
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void delete(int id){
        String sql = "DELETE FROM carlooksystem.auto WHERE id=?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            boolean success = statement.execute();
            if(!success) {
                // TODO Exception werfen
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public Auto readById(int id) {

        try {
            String sql = "SELECT * FROM carlooksystem.auto WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Auto auto = new Auto();

                auto.setId(result.getInt("id"));
                auto.setEmail(result.getString("email"));
                auto.setBeschreibung(result.getString("beschreibung"));
                auto.setMarke(result.getString("marke"));
                auto.setBaujahr(result.getString("baujahr"));
                return auto;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // TODO exception werfen

    }

    public List<Auto> readAll(){
        List<Auto> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM carlooksystem.auto";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Auto auto = new Auto();

                auto.setId(result.getInt("id"));
                auto.setEmail(result.getString("email"));
                auto.setBeschreibung(result.getString("beschreibung"));
                auto.setMarke(result.getString("marke"));
                auto.setBaujahr(result.getString("baujahr"));

                list.add(auto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; // TODO exception werfen




    }

    public List<Auto> readByEmail(String email){
        List<Auto> list = new ArrayList<>();
        try{

            String sql ="SELECT * FROM carlooksystem.auto WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();

            while(result.next()) {

                Auto auto = new Auto();

                auto.setId(result.getInt("id"));
                auto.setEmail(result.getString("email"));
                auto.setBeschreibung(result.getString("beschreibung"));
                auto.setMarke(result.getString("marke"));
                auto.setBaujahr(result.getString("baujahr"));

                list.add(auto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

