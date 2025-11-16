package org.example;



public class Main {
    public static void main(String[] args) {

        Task.printAllCities();
        System.out.println("--------------------");
        Task.addTwoCities();
        Task.printAllCities();
        System.out.println("--------------------");
        Task.addCitiesWithPreparedStatement();
        System.out.println("--------------------");
        Task.addCitiesWithBatch();
        System.out.println("--------------------");
        Task.printMetaData();
        System.out.println("--------------------");
        Task.printFrenchSpeakingCountries();
    }
}