package com.company;

public class CountBits {
    public int countZero = 0; // количество нулей
    public int countOne = 0; // количество единиц
    public int countThread0 = 0; // количество итераций, совершенных нулевым потоком
    public int countThread1 = 0; // количество итераций, совершенных первым потоком
    public int realCountZero = 0; // количество нулей, посчитанных средствами языка Java
    public int realCountOnes = 0; // количество единиц, посчитанных средствами языка Java
    private int total = 0; // сумма нулей и единиц
    private int size = 0; //первоначальный размер списка
    private int finishedThreadsCounter = 0; // счетчик завершенных потоков
    private boolean isGetSize = false; // флаг для проверки установленного размера
    private boolean continueCountFlag = true; // флаг для прекращения подсчета нулей и единиц

    public int getTotal(){
        return this.total;
    }

    private void sumCountOne(int count){
        countOne += count;
    }

    private void sumCountZero(int length, int count){
        countZero = countZero + length - count;
    }

    // сеттер для размера списка
    synchronized private void setSize(LinkedList list){
        if (!isGetSize){
            isGetSize = true;
            this.size = list.size();
        }
    }

    // сумма количества нулей и единиц
    synchronized private void sumTotalCount(){
        if (finishedThreadsCounter == 1){
            total = total + countZero + countOne;
        }
        else{
            finishedThreadsCounter++;
        }
    }

    // возвращение значения элемента для потока
    synchronized private int getElementValue(int mode, LinkedList list){
        if (mode == 1){
            if (list.getFirst() != null){
                size--;
                if (size == 0) {
                    continueCountFlag = false;
                }
                int e = (int)list.getFirst();
                list.removeFirst();
                countThread1++;
                return e;
            }
            continueCountFlag = false;
            return 0;
        } else {
            if (list.getLast() != null){
                size--;
                if (size == 0){
                    continueCountFlag = false;
                }
                int e = (int)list.getLast();
                list.removeLast();
                countThread0++;
                return e;
            }
            continueCountFlag = false;
            return 0;
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
                do{
                    int m = getElementValue(mode, list);
                    if (!continueCountFlag){
                        break;
                    }
                    realCountOnes += Integer.bitCount(m);
                    // остановка выполнения первого потока,
                    // чтобы оба потока обрабатывали примерно одинаковое количество элементов
                    // остановка не обязательна, ее можно убрать
                    int ones = 0;
                    while (m != 0) {
                        ones++;
                        m &= m - 1;
                    }
                    sumCountOne(ones);
                } while(continueCountFlag);
                // суммируем количество нулей и единиц
                // выводим общее количество
                sumTotalCount();
                break;
            case 0:
                do{
                    int m = getElementValue(mode, list);
                    if (!continueCountFlag){
                        break;
                    }
                    int length = Integer.toBinaryString(m).length();
                    realCountZero = realCountZero + length - Integer.bitCount(m);
                    int ones = 0;
                    while (m != 0) {
                        ones++;
                        m &= m-1;
                    }
                    sumCountZero(length, ones);
                }while(continueCountFlag);
                // суммируем количество нулей и единиц
                // выводим общее количество
                sumTotalCount();
                break;
        }
    }
}
