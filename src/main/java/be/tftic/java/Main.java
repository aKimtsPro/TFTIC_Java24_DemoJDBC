package be.tftic.java;

import be.tftic.java.models.Section;
import be.tftic.java.utils.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String query = "SELECT * FROM section";

        try (
                Connection co = DBConfig.createConnection();
                Statement stmt = co.createStatement();
                ResultSet rs = stmt.executeQuery(query);
        ) {

            List<Section> sections = new ArrayList<>();
            while( rs.next() ){
                long section_id = rs.getLong("section_id");
                String section_name = rs.getString("section_name");
                long delegate_id = rs.getLong("delegate_id");

                Section section = new Section(section_id, section_name, delegate_id);
                sections.add(section);
            }
            sections.forEach(System.out::println);

        }
        catch (SQLException ex){
            System.out.println( ex.getMessage() );
        }

    }

    private static void baseJDBC(){
        String host = "localhost"; // "127.0.0.1";
        int port = 5432;
        String dbName = "db_slide";

        // jdbc:postgresql://localhost:5432/db_slide
        String url= "jdbc:postgresql://%s:%d/%s".formatted(host,port,dbName);
        String username = "postgres";
        String password = "root";

        try {
            Class.forName("org.postgresql.Driver");
            Connection co = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion avec db établie");

            String query = "SELECT * FROM section";
            Statement stmt = co.createStatement();

            // region EXPLICATION execute
            // execute: general -> boolean
            //                      true -> ResultSet
            //                      false -> int

            // executeQuery: SELECT -> ResultSet
            // executeUpdate: INSERT, DELETE, UPDATE -> int (nbr ligne affecté)
//            boolean isQuery = stmt.execute(query);
//            if( isQuery ){
//                ResultSet rs = stmt.getResultSet();
//            }else {
//                int affectedRows = stmt.getUpdateCount();
//            }
            // endregion

            ResultSet rs = stmt.executeQuery(query);

            while( rs.next() ){
                long section_id = rs.getLong("section_id");
                String section_name = rs.getString("section_name");
                long delegate_id = rs.getLong("delegate_id");

                System.out.printf(
                        "%d - %s - %d\n",
                        section_id, section_name, delegate_id
                );
            }

            co.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
