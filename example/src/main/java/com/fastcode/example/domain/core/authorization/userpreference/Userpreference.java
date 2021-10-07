package com.fastcode.example.domain.core.authorization.userpreference;

import javax.persistence.*;
import java.time.*;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "userpreferenceEntity")
@Table(name = "userpreference")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Userpreference extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "language", nullable = false,length =256)
    private String language;

    @Basic
    @Column(name = "theme", nullable = false,length =256)
    private String theme;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;


}



