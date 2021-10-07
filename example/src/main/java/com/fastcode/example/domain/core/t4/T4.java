package com.fastcode.example.domain.core.t4;

import javax.persistence.*;
import java.time.*;
import java.util.List;
import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

@Entity
@Config(defaultVariableName = "t4Entity")
@Table(name = "t4")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
   	@TypeDef(
		name = "list-array",
		typeClass = ListArrayType.class
		),
}) 
public class T4 extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Type(type = "list-array")
    @Column(name = "score", nullable = true)
    private List<Integer> score;
    

}



