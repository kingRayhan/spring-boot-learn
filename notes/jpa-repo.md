# JPA Repository Reference Guide

## Table of Contents
1. [Repository Types](#repository-types)
2. [Basic Repository Operations](#basic-repository-operations)
3. [Custom Query Methods](#custom-query-methods)
4. [Entity Relationships](#entity-relationships)
5. [Orphan Removal](#orphan-removal)
6. [Best Practices](#best-practices)

## Repository Types

### 1. CrudRepository
Basic CRUD operations for simple entities.

```java
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, UUID> {
    // Provides: save(), findById(), findAll(), deleteById(), etc.
}
```

### 2. JpaRepository
Extended functionality with JPA-specific operations.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Includes CrudRepository methods plus:
    // saveAndFlush(), findAll(Sort), findAll(Pageable), etc.
}
```

### 3. PagingAndSortingRepository
For pagination and sorting support.

```java
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {
    // Provides findAll(Pageable) and findAll(Sort)
}
```

### 4. JpaSpecificationExecutor
For dynamic queries and specifications.

```java
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, UUID>, 
                                         JpaSpecificationExecutor<Product> {
    // Enables dynamic query building with Specifications
}
```

## Basic Repository Operations

```java
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    // Create
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    // Read
    public Optional<User> findUser(UUID id) {
        return userRepository.findById(id);
    }
    
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    // Update
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    // Delete
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
```

## Custom Query Methods

### Query by Method Name
```java
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByName(String name);
    List<User> findByEmailContaining(String email);
    List<User> findByNameAndEmail(String name, String email);
    List<User> findByNameOrderByEmailAsc(String name);
}
```

### @Query Annotation
```java
public interface UserRepository extends JpaRepository<User, UUID> {
    
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    List<User> findByCustomName(String name);
    
    @Query("SELECT u FROM User u JOIN u.addresses a WHERE a.city = :city")
    List<User> findUsersByCity(@Param("city") String city);
    
    @Modifying
    @Query("UPDATE User u SET u.name = :name WHERE u.id = :id")
    void updateUserName(@Param("id") UUID id, @Param("name") String name);
}
```

## Entity Relationships

JPA relationships define how entities connect to each other in the database. Each annotation has specific impacts on database schema, query performance, and memory usage.

### Owning vs Non-Owning Side of Relationships

**Owning Side (Controls Foreign Key):**
- Contains the actual foreign key column in the database
- Uses `@JoinColumn` to specify foreign key column name
- Changes to this side directly affect the database relationship
- Responsible for maintaining the relationship in the database

**Non-Owning Side (References Owning Side):**
- Does not contain foreign key column
- Uses `mappedBy` attribute to reference the property on the owning side
- Changes to this side alone do not affect database until owning side is updated
- Provides convenience for bidirectional navigation

**Key Rule:** The side with the foreign key is always the owning side.

**Examples:**
- `@OneToOne`: Either side can be owning, but the side with `@JoinColumn` owns the FK
- `@OneToMany/@ManyToOne`: The `@ManyToOne` side always owns the FK (has the foreign key column)
- `@ManyToMany`: The side without `mappedBy` owns the relationship (defines the join table)

### One-to-One Relationship

**Key Annotations:**
- `@OneToOne`: Defines a one-to-one relationship
- `mappedBy`: Indicates which side owns the relationship (non-owning side)
- `@JoinColumn`: Specifies the foreign key column name (owning side)
- `cascade`: Defines operations that cascade to related entities
- `orphanRemoval`: Automatically deletes orphaned entities

**Database Impact:**
- Creates a foreign key constraint on the owning side
- Ensures referential integrity
- May create unique constraints

**Entities:**
```java
@Entity
public class User extends BaseEntity {
    // Non-owning side - no foreign key column in users table
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;
    
    // Helper method to maintain bidirectional consistency
    public void setProfile(Profile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setUser(this);
        }
    }
}

@Entity
public class Profile extends BaseEntity {
    // Owning side - creates 'user_id' foreign key column in profiles table
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    // Prevents infinite recursion in JSON serialization
    @JsonIgnore
    public void setUser(User user) {
        this.user = user;
    }
}
```

**Performance Impact:**
- Lazy loading by default prevents unnecessary queries
- Can cause N+1 problem if not handled properly
- Use `@EntityGraph` for eager loading when needed

**Database Table Structure:**
```
users table:
- id: UUID (Primary Key)
- name: VARCHAR(255)
- email: VARCHAR(255) UNIQUE
- created_at: TIMESTAMP
- updated_at: TIMESTAMP

profiles table:
- id: UUID (Primary Key)
- user_id: UUID (Foreign Key to users.id, UNIQUE)
- bio: TEXT
- avatar_url: VARCHAR(500)
- created_at: TIMESTAMP
- updated_at: TIMESTAMP
```

**Repository & Service:**
```java
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserId(UUID userId);
}

@Service
public class ProfileService {
    
    public Profile createProfileForUser(UUID userId, Profile profile) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setProfile(profile);
        return userRepository.save(user).getProfile();
    }
}
```

### One-to-Many Relationship

**Key Annotations:**
- `@OneToMany`: Parent entity can have multiple child entities
- `@ManyToOne`: Child entity belongs to one parent
- `fetch = FetchType.LAZY`: Delays loading until accessed (default)
- `fetch = FetchType.EAGER`: Loads immediately with parent entity

**Database Impact:**
- Foreign key column created on the "many" side (child table)
- Index automatically created on foreign key for performance
- Can use `@JoinColumn` to specify foreign key name

**Entities:**
```java
@Entity
public class User extends BaseEntity {
    // Non-owning side - User doesn't have foreign key
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC") // Optional: order addresses by creation date
    private List<Address> addresses = new ArrayList<>();
    
    // Helper methods maintain bidirectional relationship
    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }
    
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null); // Important for orphan removal
    }
    
    // Bulk operation for better performance
    public void clearAddresses() {
        addresses.forEach(address -> address.setUser(null));
        addresses.clear();
    }
}

@Entity
public class Address extends BaseEntity {
    // Owning side - creates 'user_id' foreign key column
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String city;
    
    // Constructor for easy creation
    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }
}
```

**Performance Considerations:**
- Default LAZY loading prevents loading all addresses when fetching user
- Use `@BatchSize` to optimize N+1 queries: `@BatchSize(size = 10)`
- Consider pagination for large collections
- Use projections or DTOs for read-only operations

**Database Table Structure:**
```
users table:
- id: UUID (Primary Key)
- name: VARCHAR(255)
- email: VARCHAR(255) UNIQUE
- created_at: TIMESTAMP
- updated_at: TIMESTAMP

addresses table:
- id: UUID (Primary Key)
- user_id: UUID (Foreign Key to users.id)
- street: VARCHAR(255)
- city: VARCHAR(100)
- state: VARCHAR(50)
- country: VARCHAR(50)
- postal_code: VARCHAR(20)
- created_at: TIMESTAMP
- updated_at: TIMESTAMP
```

**Repository & Service:**
```java
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByUserId(UUID userId);
    List<Address> findByCity(String city);
    void deleteByUserIdAndId(UUID userId, UUID addressId);
}

@Service
public class AddressService {
    
    public Address addAddressToUser(UUID userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow();
        user.addAddress(address);
        userRepository.save(user);
        return address;
    }
    
    public void removeAddressFromUser(UUID userId, UUID addressId) {
        User user = userRepository.findById(userId).orElseThrow();
        Address address = addressRepository.findById(addressId).orElseThrow();
        user.removeAddress(address);
        userRepository.save(user);
    }
}
```

### Many-to-Many Relationship

**Key Annotations:**
- `@ManyToMany`: Multiple entities can relate to multiple other entities
- `@JoinTable`: Creates intermediate/junction table with foreign keys
- `mappedBy`: Non-owning side references the owning side property
- Using `Set` instead of `List` prevents duplicate relationships

**Database Impact:**
- Creates three tables: user, tag, and user_tags (junction table)
- Junction table has composite primary key (user_id, tag_id)
- Two foreign key constraints ensure referential integrity
- Indexes created on foreign key columns for performance

**Entities:**
```java
@Entity
public class User extends BaseEntity {
    // Owning side - defines the junction table structure
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_tags", // Junction table name
        joinColumns = @JoinColumn(name = "user_id"), // FK to users table
        inverseJoinColumns = @JoinColumn(name = "tag_id") // FK to tags table
    )
    private Set<Tag> tags = new HashSet<>();
    
    // Helper methods maintain both sides of relationship
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getUsers().add(this);
    }
    
    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getUsers().remove(this);
    }
    
    // Bulk operations for performance
    public void clearTags() {
        tags.forEach(tag -> tag.getUsers().remove(this));
        tags.clear();
    }
    
    public boolean hasTag(String tagName) {
        return tags.stream().anyMatch(tag -> tag.getName().equals(tagName));
    }
}

@Entity
public class Tag extends BaseEntity {
    // Non-owning side - references the owning side property
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore // Prevents infinite recursion in JSON serialization
    private Set<User> users = new HashSet<>();
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column
    private String description;
    
    // Constructor for easy creation
    public Tag(String name) {
        this.name = name;
    }
    
    // Utility method
    public int getUserCount() {
        return users.size();
    }
}
```

**Database Table Structure:**
```
users table:
- id: UUID (Primary Key)
- name: VARCHAR(255)
- email: VARCHAR(255) UNIQUE
- created_at: TIMESTAMP
- updated_at: TIMESTAMP

tags table:
- id: UUID (Primary Key)
- name: VARCHAR(100) UNIQUE
- description: VARCHAR(500)
- created_at: TIMESTAMP
- updated_at: TIMESTAMP

user_tags table (junction table):
- user_id: UUID (Foreign Key to users.id)
- tag_id: UUID (Foreign Key to tags.id)
- Composite Primary Key: (user_id, tag_id)
```

**Performance Optimization:**
- Use `@EntityGraph` to avoid N+1 queries
- Consider `@BatchSize` for collection loading
- For read-heavy operations, use custom queries with joins
- Avoid `CascadeType.ALL` to prevent accidental deletions

**Repository & Service:**
```java
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);
    List<Tag> findByUsersId(UUID userId);
}

@Service
public class TagService {
    
    public void addTagToUser(UUID userId, String tagName) {
        User user = userRepository.findById(userId).orElseThrow();
        Tag tag = tagRepository.findByName(tagName)
                 .orElse(Tag.builder().name(tagName).build());
        
        user.addTag(tag);
        userRepository.save(user);
    }
    
    public void removeTagFromUser(UUID userId, String tagName) {
        User user = userRepository.findById(userId).orElseThrow();
        Tag tag = tagRepository.findByName(tagName).orElseThrow();
        
        user.removeTag(tag);
        userRepository.save(user);
    }
}
```

## Cascade Types and Orphan Removal

### Cascade Types Explained
```java
// CascadeType options and their impacts:
public enum CascadeType {
    PERSIST,  // Save parent -> save children automatically
    MERGE,    // Update parent -> update children automatically  
    REMOVE,   // Delete parent -> delete children automatically
    REFRESH,  // Refresh parent -> refresh children from database
    DETACH,   // Detach parent -> detach children from persistence context
    ALL       // All of the above operations cascade
}
```

**Foreign Key Ownership Impact on Cascading:**
```java
@Entity
public class User extends BaseEntity {
    // Non-owning side: User table has no FK to profile
    // Cascade works because Profile (owning side) gets updated
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;
    
    // Non-owning side: User table has no FK to addresses
    // Addresses table has user_id FK (owning side)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Address> addresses = new ArrayList<>();
}

@Entity 
public class Address extends BaseEntity {
    // Owning side: Contains user_id foreign key column
    // This side controls the actual database relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
```

### Orphan Removal

**What is Orphan Removal:**
Orphan removal automatically deletes child entities when they're no longer referenced by their parent entity. Only works with `@OneToOne` and `@OneToMany` relationships where the parent is the non-owning side.

### Configuration
```java
@Entity
public class User extends BaseEntity {
    // Orphan removal for One-to-One
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;
    
    // Orphan removal for One-to-Many
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
}
```

### Service Examples
```java
@Service
@Transactional
public class OrphanRemovalService {
    
    // Remove profile - will be deleted from database
    public void removeUserProfile(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setProfile(null); // Profile becomes orphan and gets deleted
        userRepository.save(user);
    }
    
    // Remove address - will be deleted from database
    public void removeUserAddress(UUID userId, UUID addressId) {
        User user = userRepository.findById(userId).orElseThrow();
        Address address = user.getAddresses().stream()
                             .filter(a -> a.getId().equals(addressId))
                             .findFirst().orElseThrow();
        
        user.removeAddress(address); // Address becomes orphan and gets deleted
        userRepository.save(user);
    }
    
    // Clear all addresses - all will be deleted from database
    public void clearUserAddresses(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.getAddresses().clear(); // All addresses become orphans
        userRepository.save(user);
    }
}
```

## Advanced Repository Features

### Pagination Example
```java
@Service
public class ProductService {
    
    public Page<Product> getProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAll(pageable);
    }
    
    public Page<Product> getProductsByCategory(UUID categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }
}
```

### Specifications Example
```java
public class ProductSpecifications {
    
    public static Specification<Product> hasName(String name) {
        return (root, query, builder) -> 
            name == null ? null : builder.like(root.get("name"), "%" + name + "%");
    }
    
    public static Specification<Product> hasPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, builder) -> {
            if (minPrice != null && maxPrice != null) {
                return builder.between(root.get("price"), minPrice, maxPrice);
            }
            return null;
        };
    }
}

@Service
public class ProductSearchService {
    
    public List<Product> searchProducts(String name, Double minPrice, Double maxPrice) {
        Specification<Product> spec = Specification.where(null);
        
        if (name != null) {
            spec = spec.and(ProductSpecifications.hasName(name));
        }
        if (minPrice != null || maxPrice != null) {
            spec = spec.and(ProductSpecifications.hasPriceRange(minPrice, maxPrice));
        }
        
        return productRepository.findAll(spec);
    }
}
```

## Best Practices

### 1. Use @Transactional appropriately
```java
@Service
@Transactional(readOnly = true) // Default for read operations
public class UserService {
    
    @Transactional // Override for write operations
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    // Read operations inherit readOnly = true
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
```

### 2. Handle relationships properly
```java
// Always maintain both sides of bidirectional relationships
public void addAddress(Address address) {
    addresses.add(address);
    address.setUser(this); // Important!
}

public void removeAddress(Address address) {
    addresses.remove(address);
    address.setUser(null); // Important for orphan removal!
}
```

### 3. Use DTOs for API responses
```java
@Service
public class UserService {
    
    public UserDTO getUserWithAddresses(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return UserDTO.fromEntity(user);
    }
}
```

### 4. Optimize queries with @EntityGraph
```java
public interface UserRepository extends JpaRepository<User, UUID> {
    
    @EntityGraph(attributePaths = {"addresses", "profile"})
    Optional<User> findWithRelationsById(UUID id);
}
```

This guide covers the essential JPA repository patterns and relationship management techniques used in Spring Boot applications.