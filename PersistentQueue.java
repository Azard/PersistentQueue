package me.azard.android.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class PersistentQueue<E> extends AbstractQueue<E> {

    private final File fileDir;
    private final String fileName;
    private File file;
    private LinkedList<E> list;

    public PersistentQueue(File fileDir, String fileName) {
        this.fileDir = fileDir;
        this.fileName = fileName;
        try {
            if (!read()) {
                create();
            }
        } catch (IOException e) {
            Log.e("PersistentQueue create error: " + e.toString());
        }
    }

    public PersistentQueue(Context contex, String fileName) {
        this.fileDir = contex.getFilesDir();
        this.fileName = fileName;
        try {
            if (!read()) {
                create();
            }
        } catch (IOException e) {
            Log.e("PersistentQueue create error: " + e.toString());
        }
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(E e) {
        boolean ret = list.add(e);
        write();
        return ret;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean ret = list.addAll(c);
        write();
        return ret;
    }

    @Override
    public boolean offer(E e) {
        boolean ret = list.offer(e);
        write();
        return ret;
    }

    @Override
    public E remove() {
        E ret = list.remove();
        write();
        return ret;
    }

    @Override
    public E poll() {
        E ret = list.poll();
        write();
        return ret;
    }

    @Override
    public E element() {
        return list.element();
    }

    @Override
    public E peek() {
        return list.peek();
    }

    @Override
    public void clear() {
        list.clear();
        write();
    }

    private void create() throws IOException {
        list = new LinkedList<>();
        file = new File(fileDir, fileName);
        if (!file.createNewFile()) {
            throw new IOException("Unable create new file");
        }
    }

    private void write() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e("PersistentQueue write error: " + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private boolean read() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (LinkedList<E>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            Log.e("PersistentQueue read error: " + e.toString());
            return false;
        }
    }
}
