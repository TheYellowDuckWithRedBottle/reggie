package com.itheima.regiie.common;

public class BaseContext {
    private static ThreadLocal<Long> _threadLocal = new ThreadLocal<>();

     public static void setCurrentId(Long id){
         if(id != null){
             _threadLocal.set(id);
         }
    }

    public static Long getCurrentId(){
        return _threadLocal.get();
    }
}
