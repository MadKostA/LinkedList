package com.company;

public class CountBits {
    public int countZero = 0; // количество нулей
    public int countOne = 0; // количество единиц
    public int countThread0 = 0; // количество итераций, совершенных нулевым потоком
    public int countThread1 = 0; // количество итераций, совершенных первым потоком
    private int total = 0; // сумма нулей и единиц
    private int size = 0; //первоначальный размер списка
    private int finishedThreadsCounter = 0; // счетчик завершенных потоков
    private boolean isGetSize = false; // флаг для проверки установленного размера


    // сеттер для рамера списка
    synchronized private void setSize(LinkedList list){
        this.size = list.size();
    }

    // установка флага
    synchronized private void setIsGetSize(){
        isGetSize = !isGetSize;
    }

    public int getTotal(){
        return this.total;
    }

    // сумма количества нулей и единиц
    private void sumTotalCount(){
        total = total + countZero + countOne;
    }

    private void sumCountOne(int count){
        countOne += count;
    }

    private void sumCountZero(int length, int count){
        countZero = countZero + length - count;
    }

    synchronized private void incFinishedThreadsCounter(){
        finishedThreadsCounter++;
    }
    // подсчет количества единиц и нулей в двоичной записи чисел
    synchronized private boolean counting(int mode, LinkedList list){
        int m;
        if (mode == 1){
//            if (list.getElementByIndex(0) != null){
            if (list.size() != 0){
                m = (int) list.getElementByIndex(0);
                list.removeFirst();
            } else {
                return false;
            }
        } else {
//            if (list.getElementByIndex(size - countThread0 - countThread1) != null){
            if(list.size() != 0){
                m = (int) list.getElementByIndex(list.size() - 1);
                list.removeLast();
            } else {
                return false;
            }
        }
        int n = m;
        int ones = 0;
        while (n != 0) {
            ones++;
            n &= n-1;
        }
        sumCountOne(ones);
        sumCountZero(Integer.toBinaryString(m).length(), ones);
        return true;

    }

    // функция для запуска подсчета одним из потоков;
    // на вход передается ссылка на двусвязный список и модификатор;
    public void bitCount(LinkedList list, int mode) {
        // установка размера
        if (!isGetSize){
            setIsGetSize();
            setSize(list);
        }
        // запускаем функцию счета нулей и единиц в элементе списка
        // считаем количество итераций, проведенных потоком
        // считаем количество завершенных потоков
        switch (mode){
            case 1:
                while(counting(mode, list)){
                    countThread1++;
                }
                incFinishedThreadsCounter();
                break;
            case 0:
                while(counting(mode, list)){
                    countThread0++;
                }
                incFinishedThreadsCounter();
                break;
        }
        // суммируем количество нулей и единиц
        // выводим общее количество
        if (finishedThreadsCounter == 2){
            sumTotalCount();
        }
    }
}
