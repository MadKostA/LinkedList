package com.company;

public class Main {

    public static void main(String[] args) {
        int functionCountZeros = 0; // количество нулей, посчитанных встроенными функциями
        int functionCountOnes = 0; // количество удиниц, посчитанных встроенными функциями


        LinkedList<Integer> list = new LinkedList<>(); // создание двусвязного списка
        // заполнение двусвязного списка
        for (int i = 0; i < 10000000; i++){
            int b = (int)(Math.random()*2000000 - 1000000);
            int bitCount = Integer.bitCount(b);
            functionCountOnes += bitCount;
            functionCountZeros += Integer.toBinaryString(b).length() - bitCount;
            list.add(b);
        }

        // вывод размера и количества нулей и единиц
        System.out.println("------------------");
        System.out.println("Size before: " + list.size());
        System.out.println("------------------");
        System.out.println("Ones: " + functionCountOnes);
        System.out.println("Zeros: " + functionCountZeros);
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
        System.out.println("Total bits: " + count.getTotal());
        System.out.println("Size after: " + list.size());

//        LinkedList<Integer> lol = new LinkedList<>();
//        for (int i = 0; i < 1000; i++){
//            lol.add(i);
//            System.out.print(lol.getElementByIndex(i) + " ");
//        }
//        System.out.println();
//        System.out.println(lol.getElementByIndex(0));
//        System.out.println(lol.getElementByIndex(1));
//        System.out.println(lol.getElementByIndex(24));
//        System.out.println(lol.getElementByIndex(25));
//        System.out.println(lol.getElementByIndex(26));
//        System.out.println(lol.getElementByIndex(48));
//        System.out.println(lol.getElementByIndex(49));
//        lol.remove(49);
//        lol.size();
//        System.out.println(lol.getElementByIndex(48));

    }
}