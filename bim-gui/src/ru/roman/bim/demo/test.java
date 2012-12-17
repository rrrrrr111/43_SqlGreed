package ru.roman.bim.demo;

import java.io.IOException;
import java.util.TreeSet;

/** @author Roman 16.12.12 17:27 */
public class test {


    static class A {
        void process() throws Exception { throw new Exception(); }
    }
    static class B extends A {
        void process() { System.out.println("B"); }
    }
    //public static void main(String[] args) {
  //      new B().process();
  //  }


    void process() throws IOException { throw new IOException(); }



    public static void main(String[] args) {
        TreeSet<Integer> s = new TreeSet<Integer>();
        TreeSet<Integer> subs = new TreeSet<Integer>();
        for(int i = 606; i < 613; i++)
             if(i%2 == 0) s.add(i);
         subs = (TreeSet)s.subSet(608, true, 611, true);
         subs.add(609);
         System.out.println(s + " " + subs);
         }
}
