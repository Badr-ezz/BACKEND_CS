package com.example.springMongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    private String id;

    private String name;
    private String logo;

    @DBRef
    private Users captain ;

    @DBRef
    private List<Users> members;
}