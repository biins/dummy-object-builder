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

## Full example
```
public class Animal {
    private String speciesName;
    private String genusName;
    private int weight;
    private int length;
    private List<String> areas;
    /* get & set */
}
```
```
List<Animal> animals = new ObjectBuilder()
    .onObject()
        .ignoreProperty("id")
        // String speciesName;
        .onProperty("speciesName", "ant", "bat", "cobra", "donkey", "eagle", "fox", "gorilla", "hyena", "jaguar", "kangaroo")
        // String familyName;
        .onProperty("genusName", "pharaoh", "big", "indian", "domestic", "sea", "vulpes", "mountain", "black-headed", "panther", "australian")
        // int weight;
        .onProperty("weight", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        // int length;
        .onProperty("length", 10, 9, 9, 7, 6, 5, 4, 3, 2, 1)
        // List<String> areas;
        .onProperty("areas", Arrays.asList("forrest ground"),
                Arrays.asList("cave"),
                Arrays.asList("desert", "India"),
                Arrays.asList("America", "steppe"),
                Arrays.asList("Europe east", "Asia"),
                Arrays.asList("forrest", "Europe", "North America"),
                Arrays.asList("Africa central", "rain forrest"),
                Arrays.asList("Africa", "Asia south"),
                Arrays.asList("South America"),
                Arrays.asList("Australia")
        )
    .build(Animal.class, 10);
```
