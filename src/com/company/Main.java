package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Main {

    // public static final String HOST = "192.168.3.126";
    public static final int PORT = 7000;

    public static void main(String[] args) {
        try {
            ServerSocket jugtis = new ServerSocket(PORT);
            boolean testi = true;
            Socket serveris = null;
            ObjectInputStream ivedimas = null;
            ObjectOutputStream isvedimas = null;
            serveris = jugtis.accept(); // padaryti, kad priimtu daugiau 1 clianta, bet to paties nepriimtu tol kol neatsijunge
            System.out.println("Prisijungta " + serveris.getInetAddress());
            isvedimas = new ObjectOutputStream(serveris.getOutputStream());
            ivedimas = new ObjectInputStream(serveris.getInputStream());
            while (testi) {
                String veiksmas = "";
                try {
                    if (ivedimas.available() > 0) {
                        veiksmas = ivedimas.readUTF();
                    }
                    if (veiksmas == null) {
                        serveris.close();
                        veiksmas = "";
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                switch (veiksmas) {
                    case "pabaiga":
                        System.out.println("Gautas nurodymas baigti daraba");
                        testi = false;

                        isvedimas.writeUTF("pabaiga");
                        isvedimas.flush();

                        jugtis.close();
                        serveris.close();
                        break;
                    case "":
                        break;
                    case "studentai":
                        parodykStudentus(isvedimas);
                        break;

                    case "adresai":
                        parodykAdresus(isvedimas);
                        break;

                    case "pazimiai":
                        parodykPazimius(isvedimas);
                        break;

                    default:
                        System.out.println(veiksmas);
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static void parodykStudentus(ObjectOutputStream isvedimas) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String HOST = "jdbc:mysql://localhost:3306/kcs";
            final String USER = "root";
            final String PASSWORD = "";
            Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `students`;";

            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
            String eilute = "";
            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    eilute += resultSet.getString(i) + "\t";

                }
                eilute += "\n";
            }
            isvedimas.writeUTF(eilute);
            isvedimas.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parodykAdresus(ObjectOutputStream isvedimas) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String HOST = "jdbc:mysql://localhost:3306/kcs";
            final String USER = "root";
            final String PASSWORD = "";
            Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `student_address`;";

            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
            String eilute = "";
            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    eilute += resultSet.getString(i) + "\t";

                }
                eilute += "\n";
            }
            isvedimas.writeUTF(eilute);
            isvedimas.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parodykPazimius(ObjectOutputStream isvedimas) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String HOST = "jdbc:mysql://localhost:3306/kcs";
            final String USER = "root";
            final String PASSWORD = "";
            Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `student_marks`;";

            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
            String eilute = "";
            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    eilute += resultSet.getString(i) + "\t";

                }
                eilute += "\n";
            }
            isvedimas.writeUTF(eilute);
            isvedimas.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}