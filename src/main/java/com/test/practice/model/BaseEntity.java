package com.test.practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "createdBy", "updatedAt", "updatedBy"})
public class BaseEntity {

    @CreatedDate
    @Column(updatable=false)
    //@JsonIgnore
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable=false)
    //@JsonIgnore
    //The @JsonIgnore annotation is used to mark a property or a method that should be ignored by
    // Jackson when serializing or deserializing a Java object to or from JSON. This annotation belongs
    // to the Jackson library, which is a popular library for working with JSON in Java.
    //For example sending Json response in rest api call we don't want the properties of this class as these are sensitive data
    private String createdBy;

    @LastModifiedDate
    @Column(insertable =false)
    //@JsonIgnore
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable=false)
    //@JsonIgnore
    private String updatedBy;
}
