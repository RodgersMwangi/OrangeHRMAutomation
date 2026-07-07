package util;

import net.datafaker.Faker;

public class DataFaker {
    public static final Faker FAKER=new Faker();

    public static String firstName=FAKER.name().firstName();
    public static String lastName=FAKER.name().lastName();
    public static String id=FAKER.number().digits(6);
    public static String userPassword=DataFaker.FAKER.regexify("[A-Z]{1}[a-z]{5}[0-9]{2}[@#$%]{1}");


}
/*
 * How to use
 *
 * String employeeFirstName=DataFaker.firstName;
 *
 * */
