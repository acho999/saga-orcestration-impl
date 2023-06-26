package com.angel.orderservice.models;

import java.util.ArrayList;

public class Testing {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("pesho");
        Object[] a = list.toArray();

        System.out.println(a.length);
    }
}
