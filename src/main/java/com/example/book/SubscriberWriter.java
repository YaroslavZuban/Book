package com.example.book;

import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SubscriberWriter {
    private static final String FILENAME = "contacts.txt";
    public static void writeToFile(ObservableList<Subscriber> subscribers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME));
            for (Subscriber subscriber : subscribers) {
                writer.write(subscriber.getTitle() + "," + subscriber.getFirstName() + "," + subscriber.getLastName() + ","
                        + subscriber.getPhoneOfficial() + "," + subscriber.getPhoneMobile() + "," + subscriber.getEmail() + ","
                        + subscriber.getWebsite());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }
}
