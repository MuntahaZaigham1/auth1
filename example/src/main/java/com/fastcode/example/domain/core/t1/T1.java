package com.fastcode.example.domain.core.t1;

import javax.persistence.*;
import java.time.*;
import java.util.List;
import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.ByteArrayConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "t1Entity")
@Table(name = "t1")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class T1 extends AbstractEntity {

    @Basic
    @Convert(converter= ByteArrayConverter.class)
    @Column(name = "file", nullable = true)
    private byte[] file;
    
    @Basic
    @Convert(converter= ByteArrayConverter.class)
    @Column(name = "flpa", nullable = true)
    private List<Double> flpa;
    
    @Basic
    @Convert(converter= ByteArrayConverter.class)
    @Column(name = "fpa", nullable = true)
    private List<Double> fpa;
    
    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "inet", nullable = true,length =255)
    private String inet;

    @Basic
    @Column(name = "jb", nullable = true,length =255)
    private String jb;

    @Basic
    @Column(name = "jbf", nullable = true,length =255)
    private String jbf;

    @Basic
    @Convert(converter= ByteArrayConverter.class)
    @Column(name = "str", nullable = true)
    private String str;


}



