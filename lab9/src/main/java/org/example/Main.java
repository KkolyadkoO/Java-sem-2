package org.example;

import org.example.dao.CustomerDAO;
import org.example.dao.FilmDAO;
import org.example.dao.SaleDAO;
import org.example.models.Customer;
import org.example.models.Film;
import org.example.models.Sale;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        FilmDAO filmDAO = new FilmDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        SaleDAO saleDAO = new SaleDAO();

        Film film = new Film();
        film.setTitle("Интерстеллар");
        film.setCountry("США");
        film.setDirector("Кристофер Нолан");
        film.setTheme("Фантастика");
        film.setReleaseDate(LocalDate.of(2014, 11, 7));
        film.setTapeCost(300);
        film.setHasOscars(true);
        filmDAO.save(film);

        Customer customer = new Customer();
        customer.setAge(25);
        customer.setGender("М");
        customer.setSocialStatus("Студент");
        customerDAO.save(customer);

        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.now());
        sale.setFilm(film);
        sale.setCustomer(customer);
        saleDAO.save(sale);

        System.out.println("Продажа успешно добавлена!");
    }
}
