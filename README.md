### Python-online

A simple wrapper for python interpreter, and relevant implementation for building web socket service.

#### 1. pythonutil
`pythonutil` is a simple wrapper for python interpretrt using C API provided by `Python3`. (So the current `pythonutil` is only for `Python3`)

In `pythonutil`, we try to redirect python's `stdout`/`stderr` into C level stdio. The solution is found on [Python sys.stdout redirection in C++](http://mateusz.loskot.net/posts/2011/12/01/python-sys-stdout-redirection-in-cpp/). The relevant StackOverflow question is [How To catch python stdout in c++ code
](https://stackoverflow.com/questions/4307187/how-to-catch-python-stdout-in-c-code)

Because of future extension, We use the `C++ solution` instead of the `simple python solution`. In `C++ solution`, we could register callback functions for stdout and stderr.

#### 2. service

`servie` is a java web application based on the `SpringMVC` framework. Additional, we deploy an optinal database for user data persistence based on `hibernate`. We built a simple combination for `SpringMVC` and `hibernate`.

##### 2.1 User Authentication
It is just a common user module for authentication.

##### 2.2 Python Service
`Python Service` is the most important module for multi-user python interpreter online. We define `ProcessStreamRunnale` to process single user interpreter requests. The `Runnalbes` are submited to a `CachedThreadPool`.

Moreover, `AsyncInpuReader` and `AsyncOutputWriter` are built for pythonutil IO. The former receives an Inputstream and a `StreamDelegate` as parameters, if the InputStream is not null, the reader would invoke the delegate and pass the data to delegate target. The later receives an InputStream and a PipedOutputStream as parameters, if the PipOutputStream is accessible, it would write the piped data to the InputStream. The reader and writer work in independent polling thread.

#### 3 Tips
##### 3.1 properties
There are some properties for web appliction.
1. `hibernate.properties`: `url`/`usename`/`password` for mysql in jdbc
2. `python-config`: `path` for pythonutil

##### 3.2 I am not sure...
