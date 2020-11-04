package com.company;

public class CountBits {

    private int countZero = 0; // количество нулей
    private int countOne = 0; // количество единиц
    private int countThread0 = 0; // количество итераций, совершенных нулевым потоком
    private int countThread1 = 0; // количество итераций, совершенных первым потоком
    private int total = 0; // сумма нулей и единиц
    private int size = -1; //первоначальный размер списка

    public int getTotal(){
        return this.total;
    }

    public  int getCountZero(){
        return this.countZero;
    }

    public int getCountOne(){
        return this.countOne;
    }

    public int getCountThread0(){
        return this.countThread0;
    }

    public int getCountThread1(){
        return this.countThread1;
    }

    // сеттер для размера списка
    synchronized private void setSize(LinkedList list){
        if (size < 0){
            this.size = list.size();
        }
    }

    // сумма количества нулей и единиц
    synchronized private void sumTotalCount(int count){
        total += count;
    }

    // возвращение значения элемента для потока
    synchronized private int getElementValue(int mode, LinkedList list) {
        if (mode == 1){
            int v;
            try {
                v = (int)list.getFirst();
            } catch (Exception e){
                return 0;
            }
            list.removeFirst();
            countThread1++;
            size--;
            return v;
        } else {
            int v;
            try {
                v = (int) list.getLast();
            } catch (Exception e){
                return 1;
            }
            list.removeLast();
            countThread0++;
            size--;
            return v;
        }
    }

    // функция для запуска подсчета одним из потоков;
    // на вход передается ссылка на двусвязный список и модификатор;
    public void bitCount(LinkedList list, int mode) {
        // установка размера
        setSize(list);
        // запускаем функцию счета нулей и единиц в элементе списка
        // считаем количество итераций, проведенных потоком
        // считаем количество завершенных потоков
        switch (mode){
            case 1:
                do {
                    int m = getElementValue(mode, list);
                    int ones = 0;
                    while (m != 0) {
                        ones++;
                        m &= m - 1;
                    }
                    countOne += ones;
                } while(size > 0);
                // суммируем количество нулей и единиц
                // выводим общее количество
                sumTotalCount(countOne);
                break;
            case 0:
                do{
                    int m = getElementValue(mode, list);
                    int length = Integer.toBinaryString(m).length();
                    int ones = 0;
                    while (m != 0) {
                        ones++;
                        m &= m-1;
                    }
                    countZero = countZero + length - ones;
                } while(size > 0);
                // суммируем количество нулей и единиц
                // выводим общее количество
                sumTotalCount(countZero);
                break;
        }
    }
}

