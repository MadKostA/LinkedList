package com.company;

public class Main {

    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<>(); // создание двусвязного списка/**/
        // заполнение двусвязного списка
        for (int i = 0; i < 100; i++){
            int b = (int)(Math.random()*2000000 - 1000000);
            list.add(b);
        }

        // вывод размера и количества нулей и единиц
        System.out.println("------------------");
        System.out.println("Size before: " + list.size());
        System.out.println("------------------");

        CountBits count = new CountBits();
        // поток для счета нулей
        Thread thread0 = new Thread(new MyThread(count, 0, list), "ThreadZero");
        thread0.start();
        // поток для счета единиц
        Thread thread1 = new Thread(new MyThread(count, 1, list), "ThreadOne");
        thread1.start();


        try {
            thread0.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------");
        System.out.println("Total bits: " + count.getTotal());
        System.out.println("Size after: " + list.size());
    }
}