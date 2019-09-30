package com.itxwl.danlie;

public class Simglen {
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SimgLenTest {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Simglen simglen = new Simglen();
    }

    private Simglen() {
    }

    public static Simglen getInstance() {
        return SimgLenTest.simglen;
    }
}
