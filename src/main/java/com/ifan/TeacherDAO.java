package com.ifan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class TeacherDAO {
    ExecutorService executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
        AtomicInteger atomicInteger = new AtomicInteger(10); //AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减。
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(false);
            thread.setName("TeacherDAO--" + atomicInteger.getAndDecrement());
            return thread;
        }
    });

    public void save(List<Integer> list)
    {
        for(final int i : list)
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    doSave(i);
                }
            });
        }

    }

    public void doSave(int i)
    {
        System.out.println(Thread.currentThread().getName()+"============" + i);
        try {
            Thread.sleep(50);
        }catch (InterruptedException e)
        {
            System.out.println(e.getStackTrace());
        }
    }

    public static void main(String args[])
    {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<100;i++)
        {
            list.add(i);
        }

        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.save(list);



    }


}
