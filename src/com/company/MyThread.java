package com.company;

public class MyThread implements Runnable{

    LinkedList list; // двусвязный список
    CountBits count; // класс, в котором выполняется подсчет нулей и единиц
    int mode; // модификатор определяет, что будет считать поток - 0 или 1

    MyThread(CountBits count, int mode, LinkedList list){
        this.count = count;
        this.mode = mode;
        this.list = list;
    }

    public void run(){
        // запуск функцию подсчета
        count.bitCount(this.list, this.mode);

        // вывод на результатов в консоль
        if (this.mode == 1){
            System.out.println("ThreadOne count ones: " + count.getCountOne());
            System.out.println("ThreadOne number of processed items: " + count.getCountThread1());
        } else {
            System.out.println("ThreadZero count zeros: " + count.getCountZero());
            System.out.println("ThreadZero number of processed items: " + count.getCountThread0());
        }

    }

}
