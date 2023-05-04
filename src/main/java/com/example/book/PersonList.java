package com.example.book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * описывает интерфейс PersonList, который содержит только одну константу - статическое поле PERSONLIST
 * типа ObservableList<Subscriber>. Это означает, что любой класс, который реализует этот интерфейс,
 * должен будет иметь доступ к PERSONLIST, который будет использоваться для хранения списка абонентов
 * (объектов класса Subscriber).
 */
public interface PersonList {
    ObservableList<Subscriber> PERSONLIST = FXCollections.observableArrayList();
}
