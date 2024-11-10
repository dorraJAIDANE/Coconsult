package com.test.COCONSULT.Entity;


import com.test.COCONSULT.DTO.RoleName;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.NaturalId;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;




}
