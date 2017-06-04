## Python-online

A simple wrapper for python interpreter, and relevant implementation for building web socket service. 
The highlight is that We built a language bridge for Java and Python Using C++ API.

### 1. pythonutil
`pythonutil` is a simple wrapper for python interpreter using C API provided by `Python3`. 
(So the current `pythonutil` is only for `Python3`)

In `pythonutil`, we try to redirect python's `stdout`/`stderr` into C level stdio. 
The solution is found on [Python sys.stdout redirection in C++](http://mateusz.loskot.net/posts/2011/12/01/python-sys-stdout-redirection-in-cpp/). 
The relevant StackOverflow question is [How To catch python stdout in c++ code](https://stackoverflow.com/questions/4307187/how-to-catch-python-stdout-in-c-code)

Because of future extension, we use the `C++ solution` instead of the `simple python solution`. 
In this solution, we could register callback functions for stdout and stderr.

### 2. service

`service` is a java web application based on the `SpringMVC` framework. 
Additional, we deploy an optional database for user data persistence based on `hibernate`. 
We built a simple combination for `SpringMVC` and `hibernate`.

#### 2.1 User Authentication
It is just a common user module for authentication.

#### 2.2 Python Service
`Python Service` is the most important module for multi-user python interpreter online. 
We define `ProcessStreamRunnable` to process single user interpreter requests. The `Runnables` are submitted to a `CachedThreadPool`.

Moreover, `AsyncInputReader` and `AsyncOutputWriter` are built for pythonutil IO. 
The former accepts an InputStream and a `StreamDelegate` as parameters, 
if the InputStream is not null, the reader would invoke the delegate and pass the data to delegate target. 
The later accepts an InputStream and a PipedOutputStream as parameters, 
if the PipedOutputStream is accessible, it would write the piped data to the InputStream. 
The reader and writer work in independent polling thread.

### 3 Tips
There are some properties for web application.
1. `hibernate.properties`: `url`/`username`/`password` for mysql in jdbc
2. `python-config`: `path` for pythonutil

### 4 Bugs
#### 4.1 Efficiency
As mentioned above, we use three threads to support one online interpreter, 
the AsyncReader may not be efficient to catch output stream from pythonutil. 
In fact, we found that the buffer length is sensitive to both `C++ to Java` and `Java to Web` processes.

#### 4.2 Security
pythonutil is running at the host server currently, it could be harmful to server if some `bad` code is submitted. 
We could deploy sandbox for pythonutil for future uses.
