package com.company;

public class MyThread implements Runnable{

    LinkedList list; // двусвязный список
    CountBits count; // класс, в котором выполняется подсчет нулей и единиц
    int mode; // модификатор определяет, что будет считать поток - 0 или 1ж

    MyThread(CountBits count, int mode, LinkedList list){
        this.count = count;
        this.mode = mode;
        this.list = list;
    }

    public void run(){
        //запуская функцию подсчета
        count.bitCount(this.list, this.mode);
        // вывод на результатов в консоль
        if (this.mode == 1){
            System.out.println("ThreadOne count ones: " + count.countOne);
            System.out.println("ThreadOne count iterations: " + count.countThread1);
            System.out.println(Thread.currentThread().getName() + " finished");
        } else {
            System.out.println("ThreadZero count zeros: " + count.countZero);
            System.out.println("ThreadZero count iterations: " + count.countThread0);
            System.out.println(Thread.currentThread().getName() + " finished");
        }

    }

}
