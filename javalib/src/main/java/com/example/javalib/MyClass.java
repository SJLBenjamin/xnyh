package com.example.javalib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyClass {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            list.add("123");
        }


     /*   //因为ArrayList底层也是创建数组,当删除一个元素后,就把后一个元素的放到此元素的位置,所以长度删除一个就会减少一个

        //从最后一个开始删除,不需要移动位置,效率高,能删除完
        int size = list.size();
        for (int j=0;j<size;j++){
//            System.out.println("size==="+list.size());
//            System.out.println("j=="+list.get(j));
            list.remove(list.size()-1);
        }*/



  /*      //每次都删除第一个元素,因为删除第一个元素后,后面的就会移动一个位置,可以删除完成,但是因为每次需要copy后面的元素,所以效率低
       int size2 = list.size();
        for (int j=0;j<size2;j++){
//            System.out.println("size==="+list.size());
//            System.out.println("j=="+list.get(j));
            list.remove(0);
        }*/



        //删除不完,因为list.size()会慢慢减少,导致不能执行足够的循环
   /*     for (int j=0;j<list.size();j++){
            System.out.println("size==="+list.size());
            System.out.println("j=="+list.get(j));
            list.remove(j);
        }*/


      //下标越界错误删除,因为删除的时候设置的长度是10,而实际上集合的元素的下标会依次减少
   /*     int size3 = list.size();
        for (int j=0;j<size3;j++){
            System.out.println("size==="+list.size());
            System.out.println("j=="+list.get(j));
            list.remove(j);
        }*/

        //
        /*
        * hashMap原理:hashMap是数组链表结构,每个数组元素储存着一条链表,通过key的hash值和map集合长度取余,hash值%hashMap.length()得到储存的位置,每个链表的每个节点会储存hash值和对应的value,
        * 如果有算出重复的地址,那么通过hash值去找对应的value
        * 优化点:hashMap扩容会重新计算hash值,导致效率慢,优化点,new HashMap的时候就指向大小,此大小应该大于你存储数据的1.25倍,因为剩余空间小于0.25他就会去扩容
        * */
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(100);


        /*
        * SparseArray,Android建议用这个替换hashMap
        * 采用稀疏数组,减少内存
        * Android提供的key value集合,数据结构为两个数组,一个数组存key,一个数组存value,不会像HashMap中每个节点还得存hash值,也减少了内存
        * 在查找的时候通过二分法查找值,然后通过值得到数组下标,因为key和value的数组下标一致,key和value就快,费时的地方在二分查找,但是不需要计算hash值,省了一部分时间
        * 在删除的时候,只会将这个位置设置为已删除,不会真正删除这个节点,所以不需要移动操作
        * 在增加的时候,如果有删除标识节点,那么可以直接去替换这个位置,不需要去执行移动操作,所以越用增加的速度越快
        *   SparseArray<String> stringSparseArray = new SparseArray<>();
        * */


    }
}
