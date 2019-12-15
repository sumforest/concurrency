package com.sen.concurrency3.juc.atomic;

import com.sen.UnsafeLoaderClassTest;
import com.sun.deploy.util.UpdateCheck;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: Sen
 * @Date: 2019/12/15 17:06
 * @Description:
 */
public class UnsafeFooTest {

    public static void main(String[] args) throws Exception {
       /* Simple simple = new Simple();
        System.out.println(simple.getI());*/

        /*Unsafe unsafe = getUnsafe();*/
        /*Simple simple = (Simple) unsafe.allocateInstance(Simple.class);
        System.out.println(simple.getI());
        System.out.println(simple.getClass());
        System.out.println(simple.getClass().getClassLoader());*/

        /*Guard guard = new Guard();
        Field field = Guard.class.getDeclaredField("allowToWork");
        unsafe.putInt(guard, unsafe.objectFieldOffset(field), 42);
        guard.work();*/

        /*byte[] bytes = loadClass();
        Class<?> aClass = unsafe.defineClass(null, bytes, 0, bytes.length, null, null);
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());
        UnsafeLoaderClassTest test = (UnsafeLoaderClassTest) aClass.newInstance();
        System.out.println(test.getName());*/

        System.out.println(sizeOf(new Simple()));
    }

    private static long sizeOf(Object object) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        Set<Field> fieldSet = new HashSet<>();
        Class c = object.getClass();
        while (c != Object.class) {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) == 0)
                    fieldSet.add(field);
            }
            c = c.getSuperclass();
        }

        long maxOffset = 0;
        for (Field field : fieldSet) {
            long offSet = unsafe.objectFieldOffset(field);
            if (offSet > maxOffset)
                maxOffset = offSet;
        }
        return (maxOffset / 8 + 1) * 8;
    }

    private static byte[] loadClass() {
        File file = new File("C:\\Users\\Sen\\OneDrive\\桌面\\UnsafeLoaderClassTest.class");
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Guard {
        private int allowToWork = 1;

        private boolean allow() {
            return 42 == allowToWork;
        }

        public void work() {
            if (allow())
                System.out.println("I am allowed to work");
        }
    }

    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe) field.get(null);
    }

    private static class Simple {

        private long i = 0;

        public Simple() {
            this.i = 1;
            System.out.println("Simple is be new");
        }

        public long getI() {
            return i;
        }
    }
}
