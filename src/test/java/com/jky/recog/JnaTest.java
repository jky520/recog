package com.jky.recog;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * Jna的测试类
 * Created by DT人 on 2017/8/15 17:27.
 */
public class JnaTest {

   /* public interface CLibrary extends Library {
        *//**
         * 获得dll实例
         *//*
        *//*CLibrary INSTANCE = (com.jky.recog.dll.api.CLibrary)
                Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);*//*

        *//**
         * dll中的方法复写
         *//*
        void printf(String format, Object... args);
    }*/

    // main函数的快捷键 psvm
    public static void main(String[] args) {
     /* CLibrary.INSTANCE.printf("Hello World/n");
      for (int i = 0; i < args.length; i++) {
        CLibrary.INSTANCE.printf("Argument %d: %s%n", i, args[i]);
      }*/
    }
}
