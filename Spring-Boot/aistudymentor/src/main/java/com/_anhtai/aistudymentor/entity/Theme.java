package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_Themes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ThemeID")
    private Integer themeId;
    
    @Column(name = "ThemeName", nullable = false, length = 100)
    private String themeName;
    
    @Column(name = "ColorCode", nullable = false, length = 20)
    private String colorCode;
    
    @Column(name = "RequiredXP")
    private Integer requiredXp = 0;
}
