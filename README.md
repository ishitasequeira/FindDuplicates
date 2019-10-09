# FindDuplicates
Finding Duplicates in a CSV file

The application demonstartes a Spring MVC + Spring Boot application to find all potential duplicate entries in the given CSV file. It utilizes Levenshtein Distance and Double Metaphone Library.

##Assumptions:

1. We have considered 60% record header threshold for 2 values to be considered duplicate, that is, 2 records are potential duplicates if atleast 60% vlaues of all the record hreader match.

2. We have also considered individual field value length match threshold as 40%, that is, if any value's length matches 40% of any other record, then there is a possibility of it being a duplicate record.


##Steps to run the application:
1. You can run the application locally by using the [Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) from command line using the below command

```shell
mvn spring-boot:run
```

2. You can also use the `main` method `com.validity.exercise.potentialDuplicates.PotentialDuplicatesApplication` by opening it into any IDE like `STS` or `IntelliJ IDEA`

