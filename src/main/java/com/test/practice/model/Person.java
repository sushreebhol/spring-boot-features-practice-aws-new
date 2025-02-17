package com.test.practice.model;

import com.test.practice.annotation.FieldsValueMatch;
import com.test.practice.annotation.PasswordValidator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int personId;

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient //This Annotation indicates spring data jpa not to consider this field for any type of database operation
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient  //This Annotation indicates spring data jpa no to consider this field for any type of database operation
    private String confirmPwd;

    //OnetoOne relationship is where a record in one entity(table) is associated with exactly one record in another entity(table)
    /*@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Roles.class)*/
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId",nullable = false)
    private Roles roles;

    //@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE ,targetEntity = Address.class)
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL ,targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId",nullable = true)
    //@JoinColumn is used to specify the foreign key column details
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {
                    @JoinColumn(name = "person_id", referencedColumnName = "personId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "courseId")})
    private Set<Courses> courses = new HashSet<>();

    //fetch = FetchType.LAZY - is the default fetch type of ToMany relationships. like - OneToMany, ManyToMany
    //when retrieving an entity its relations or child property will not be loaded until unless we try referring
    //child entity using getter method. (Its good for performance wise when there is more relation records
    //and you don't want to load all at a time)

    //fetch = FetchType.EAGER - is the default fetch type of ToOne relationships.like - OneToOne, ManyToOne
    //It will load its relation entities as well


    //cascade = CascadeType.PERSIST - save() or persist() operation cascade to the child entities as well
    //For example :- when I save the Person data in person table by passing Person pojo object to spring data
    //then along with person data address id will be saved in Person table and all the address details will be
    //saved in address table

    //cascade = CascadeType.MERGE - when there is an update will be performed in parent table it will also be performed in
    //child table as well.

    //cascade = CascadeType.REFRESH - when parent table will be reloaded then child table will also be reloaded.

    //cascade = CascadeType.REMOVE - if any delete operation will be performed on parent then the corresponding
    //record will also be removed from child table.
    //In case of address it is fine to delete the corresponding record from Address table as every person has unique address
    //But In case of role we can not remove Corresponding record from Roles table as the same role may be assigned to other person
    //records as well.

    //cascade = CascadeType.DETACH - If the parent pojo is detached from the current session then the child will also be detached.

    //cascade = CascadeType.ALL - It will include all then cascade type (PERSIST, MERGE, REFRESH, REMOVE, DETACH)
}
