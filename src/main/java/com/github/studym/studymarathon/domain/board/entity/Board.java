package com.github.studym.studymarathon.domain.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 15, nullable = false)
    private String author;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String content;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
