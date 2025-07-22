```java
 var userRepository = context.getBean(UserRepository.class);

List<User> users = List.of(
        new User(null, "user 1", "user1@example.com", "pass1"),
        new User(null, "user 2", "user2@example.com", "pass1"),
        new User(null, "user 3", "user3@example.com", "pass1"),
        new User(null, "user 4", "user4@example.com", "pass1")
);
var res = userRepository.saveAll(users);
res.forEach(System.out::println);

```