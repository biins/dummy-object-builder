# generated-object-builder

## Maven
```
<dependency>
    <groupId>org.biins</groupId>
    <artifactId>generated-object-builder</artifactId>
    <version>1.0</version>
</dependency>
```

## About

generated-object-builder:
* creates dummy object for testing for example
* creates and fill object properties using reflection
* you can define several strategies for each type (string, primitives, collections, enums, etc.)
* collection types is inferred from generic (Collection of Strings - Collection<String>)
* supported is (recursively):
  * primitives
  * wrapper
  * arrays
  * strings
  * collections
  * enums
  * maps
  * common objects (and fields)

## Example
```
Page page = new ObjectBuilder()
        .onString().setGeneratorStrategy(StringGeneratorStrategy.EMPTY)
        .onObject().ignoreProperty("title")
        .build(Page.class);
        
// page.title is null
// all strings (recursively) are ""
```
```
List<Page> pages = new ObjectBuilder()
        .setStrategyForAll("NULL")
        .onObject().onProperty("title", new CyclicValuesGenerator<Object>("A", "B", "C"))
        .build(Page.class, 3);

// pages.size() == 3

// pages.get(0).getTitle() == "A"
// pages.get(1).getTitle() == "B"
// pages.get(2).getTitle() == "C"
```
