package com.example.book;

import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class SubscriberWriter {
    private static final String FILENAME = "contacts.txt";

    /**
     * принимает объект типа ObservableList<Subscriber> (наблюдаемый список объектов типа Subscriber)
     * и записывает его содержимое в текстовый файл contacts.txt. Каждый элемент списка записывается как строка,
     * содержащая значения полей объекта типа Subscriber через запятую. В конце каждой строки добавляется
     * символ новой строки. Если при записи происходит ошибка ввода-вывода, то в консоль выводится сообщение
     * об ошибке и печатается трассировка стека исключения.
     */
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
