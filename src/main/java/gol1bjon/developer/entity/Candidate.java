package gol1bjon.developer.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Candidate {
    private String id;
    private String fileId;
    private String fullName;
    private int age;
    private String description;

    private int countVotes;

    public Candidate(String id, String fileId, String fullName, int age, String description) {
        this.id = id;
        this.fileId = fileId;
        this.fullName = fullName;
        this.age = age;
        this.description = description;
    }
}
