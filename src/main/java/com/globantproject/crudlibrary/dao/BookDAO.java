package com.globantproject.crudlibrary.dao;

import com.globantproject.crudlibrary.database.IDBConnection;
import com.globantproject.crudlibrary.model.Book;
import com.globantproject.crudlibrary.model.ReservationInfo;

import static com.globantproject.crudlibrary.database.DataBase.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDAO extends IDBConnection {

    default ArrayList<Book> read() {
        ArrayList<Book> books = new ArrayList();
        try (Connection connection = connectToDB()) {
            String query = "SELECT * FROM " + TBOOKS;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString(TBOOKS_TITLE),
                        rs.getString(TBOOKS_AUTHOR),
                        Integer.valueOf(rs.getString(TBOOKS_EDITORIAL)),
                        Boolean.valueOf(rs.getString(TBOOKS_STATE))
                        );
                
                book.setId(Integer.valueOf(rs.getString(TBOOKS_ID)));
                books.add(book);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return books;
    }

    default void create() {
        try (Connection connection = connectToDB()) {

            String queryCreate = "INSERT INTO libros(Title, Author, EditorialYear, State)" +
                    "VALUES (?,?,?,?);";
            PreparedStatement sentence = connection.prepareStatement(queryCreate);

            sentence.setString(1, "Title2");
            sentence.setString(2, "Author2");
            sentence.setInt(3,2021);
            sentence.setBoolean(4,true);
            sentence.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

}
