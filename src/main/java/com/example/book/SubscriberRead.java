package com.example.book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberRead {
    public static void readSubscribersFromFile(String fileName,ObservableList<Subscriber> list) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        list.clear();

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            Subscriber subscriber = new Subscriber();

            subscriber.setTitle(fields[0]);
            subscriber.setFirstName(fields[1]);
            subscriber.setLastName(fields[2]);
            subscriber.setPhoneOfficial(fields[3]);
            subscriber.setPhoneMobile(fields[4]);
            subscriber.setEmail(fields[5]);
            subscriber.setWebsite(fields[6]);

            list.add(subscriber);
        }

        reader.close();
    }
}
