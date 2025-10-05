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

            System.out.println("=== Все издательства ===");
            publisherDAO.getAll().forEach(System.out::println);

            System.out.println("\n=== Все авторы ===");
            authorDAO.getAll().forEach(System.out::println);

            System.out.println("\n=== Все книги ===");
            bookDAO.getAll().forEach(System.out::println);

            // ✅ 1. Найти все книги, изданные за последние 5 лет
            System.out.println("\n=== Книги за последние 5 лет ===");
            bookDAO.getBooksLast5Years().forEach(System.out::println);

            // ✅ 2. Вывести список книг заданного автора (Stephen King, id=5)
            System.out.println("\n=== Книги автора Stephen King (id=5) ===");
            bookDAO.getBooksByAuthor(5).forEach(System.out::println);

            // ✅ 3. Найти авторов, у которых более N книг
            System.out.println("\n=== Авторы с более чем 2 книгами ===");
            authorDAO.getAuthorsWithMoreThanNBooks(2).forEach(System.out::println);

            // ✅ 4. Издательства, выпустившие книги более чем 2 авторов
            System.out.println("\n=== Издательства, выпустившие более 2 авторов ===");
            publisherDAO.getPublishersWithMoreThanNAuthors(2).forEach(System.out::println);

            // ✅ 5. Удалить книги, изданные ранее заданного года (например, 2000)
            System.out.println("\n=== Удаляем книги старше 2000 года ===");
            bookDAO.deleteBooksBeforeYear(2000);

            System.out.println("\n=== Список книг после удаления ===");
            bookDAO.getAll().forEach(System.out::println);

            // ✅ 6. Добавление новой книги + обновление автора
            System.out.println("\n=== Добавляем нового автора и книгу ===");
            Author newAuthor = new Author(0, "Haruki Murakami", LocalDate.of(1949, 1, 12));
            authorDAO.create(newAuthor);

            Book newBook = new Book(0, "Kafka on the Shore", 6, 1, 2002);
            bookDAO.create(newBook);

            System.out.println("\n=== Обновляем страну издательства Penguin Books ===");
            Publisher penguin = publisherDAO.getById(1);
            penguin.setCountry("United Kingdom");
            publisherDAO.update(penguin);

            System.out.println("\n=== Проверяем обновлённые данные ===");
            publisherDAO.getAll().forEach(System.out::println);

            // ✅ 7. Удаление автора
            System.out.println("\n=== Удаляем автора Leo Tolstoy (id=4) ===");
            authorDAO.delete(4);
            System.out.println("Автор удалён.");

            System.out.println("\n=== Итоговые авторы ===");
            authorDAO.getAll().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
