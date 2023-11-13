package cn.scu.imc;


import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public  class Test1 {

    public static void main(String[] args){
        Dept dept = new Dept();
        dept.setId(1);
        dept.setDeptName("成都分行");
        dept.setDeptDesc("XXX");
        List<User> list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list.add(getUser(i));
        }

        dept.setUserList(list);

        Dept t =(Dept) deepClone(dept);
        System.out.println(t.getId());
        List<User> userList = t.getUserList();
        for(User user:userList){
            System.out.println(user);
        }
        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        

    }

    private static  User getUser(int i){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,3,3,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        try {
            Integer integer = executor.submit(new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    return null;
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.execute(()->{
          //  System.out.println(1);
        });
        ExecutorService executorService = Executors.newCachedThreadPool();
        SynchronousQueue<Runnable> runnables = new SynchronousQueue<>();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        Callable callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };
        FutureTask<String> futureTask = new FutureTask<String>(callable);
        CountDownLatch countDownLatch = new CountDownLatch(9);
        countDownLatch.countDown();

        new Thread(futureTask).run();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
        AtomicReference<Dept> deptAtomicReference = new AtomicReference<>();
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();



        Set<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>(5);
        list.add("123");
        LinkedHashSet hashSet = new LinkedHashSet();
        User user = new User();
        user.setId(i);
        user.setName("cd"+i);
        user.setPhoneNumber("1510833459"+i);
        return user;
    }



    public static <T>  T deepClone(T object){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            return (T) objectInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
