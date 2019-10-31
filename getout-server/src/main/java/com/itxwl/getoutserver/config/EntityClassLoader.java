package com.itxwl.getoutserver.config;

public class EntityClassLoader extends ClassLoader {

    private ClassLoader parent;

    EntityClassLoader(ClassLoader parent) {
        this.parent = parent;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.loadClass(name, false);
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = this.findLoadedClass(name);
        if (null != parent)
            clazz = parent.loadClass(name);
        if (null == clazz)
            this.findSystemClass(name);
        if (null == clazz)
            throw new ClassNotFoundException();
        if (resolve)
            this.resolveClass(clazz);
        return clazz;
    }
}