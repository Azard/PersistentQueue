`PersistentQueue<E>` is a Queue class write in Java that

* For Android platform
* Persistent save data, that means close and start Application, the data remain unchanged
* Based on file
* The `<E>` must implement `Serializable`

# Usage

``` Java
Queue<SomeClass> queue = new PersistentQueue<>(context, "fileName");
queue.add(someObject);
queue.poll(someObject);
```

There are two kinds of initial function

``` Java
public PersistentQueue(java.io.File fileDir, String fileName)
```

Save the serialize data to `fileName` in `fileDir`.

``` Java
public PersistentQueue(android.content.Context context, String fileName)
```

Easy to used in `Activity`, `Service` and `Application`, default save the serialize data to `fileName` in `contex.getFilesDir()`.
