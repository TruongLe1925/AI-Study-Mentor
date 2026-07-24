package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_UserSubjects", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"UserID", "SubjectID"}, name = "UQ_User_Subject"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserSubjectID")
    private Integer userSubjectId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false, foreignKey = @ForeignKey(name = "FK_UserSubjects_Users"))
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID", nullable = false, foreignKey = @ForeignKey(name = "FK_UserSubjects_Subjects"))
    private Subject subject;
}
