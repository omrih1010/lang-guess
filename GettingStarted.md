# Requirements #

  * Java 1.5

# Download #

Download its package(whose filename is 'lang-guess--.zip') from here and extract all files.
  * http://code.google.com/p/lang-guess/downloads/list

The package's content is the following.

  * lib/langdetect.jar : This library itself
  * profiles/: language profiles
  * doc/ : API references of this library
  * src/ : source files of this library


# Trial #

The library file 'langdetect.jar' is an executable jar.
You can try to detect languages of any text file on command line.

```
$ java -jar lib/langdetect.jar --detectlang -d profiles [text files to detect]

sample.txt:[de:0.999999863588238]
```

See [Tools](Tools.md) about Other commands of langdetect.jar.


# Tutorial #

  * [Tutorial](Tutorial.md)