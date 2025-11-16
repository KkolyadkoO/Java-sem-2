package org.lab4;

import org.lab4.dao.*;
import org.lab4.db.DatabaseConnection;
import org.lab4.entity.*;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            AuthorDAO authorDAO = new AuthorDAO(connection);
            BookDAO bookDAO = new BookDAO(connection);
            PublisherDAO publisherDAO = new PublisherDAO(connection);

            System.out.println("Все издательства");
            publisherDAO.getAll().forEach(System.out::println);

            System.out.println("\nВсе авторы");
            authorDAO.getAll().forEach(System.out::println);

            System.out.println("\nВсе книги");
            bookDAO.getAll().forEach(System.out::println);

            System.out.println("\nКниги за последние 5 лет");
            bookDAO.getBooksLast5Years().forEach(System.out::println);

            System.out.println("\nКниги автора Stephen King (id=5)");
            bookDAO.getBooksByAuthor(5).forEach(System.out::println);

            System.out.println("\nАвторы с более чем 2 книгами");
            authorDAO.getAuthorsWithMoreThanNBooks(2).forEach(System.out::println);

            System.out.println("\nИздательства, выпустившие более 2 авторов");
            publisherDAO.getPublishersWithMoreThanNAuthors(1).forEach(System.out::println);

            System.out.println("\nУдаляем книги старше 2000 года");
            bookDAO.deleteBooksBeforeYear(2000);

            System.out.println("\nСписок книг после удаления");
            bookDAO.getAll().forEach(System.out::println);

            System.out.println("\nДобавляем нового автора и книгу");
            Author newAuthor = new Author(0, "Haruki Murakami", LocalDate.of(1949, 1, 12));
            authorDAO.create(newAuthor);

            Book newBook = new Book(0, "Kafka on the Shore", 6, 1, 2002);
            bookDAO.create(newBook);

            System.out.println("\nОбновляем страну издательства Penguin Books");
            Publisher penguin = publisherDAO.getById(1);
            penguin.setCountry("United Kingdom");
            publisherDAO.update(penguin);

            System.out.println("\nПроверяем обновлённые данные");
            publisherDAO.getAll().forEach(System.out::println);

            System.out.println("\nУдаляем автора Leo Tolstoy (id=4)");
            authorDAO.delete(4);
            System.out.println("Автор удалён.");

            System.out.println("\nИтоговые авторы");
            authorDAO.getAll().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
