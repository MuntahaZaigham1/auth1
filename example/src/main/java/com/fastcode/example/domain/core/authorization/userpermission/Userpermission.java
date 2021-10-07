package com.fastcode.example.domain.core.authorization.userpermission;

import javax.persistence.*;
import java.time.*;
import com.fastcode.example.domain.core.authorization.permission.Permission;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "userpermissionEntity")
@Table(name = "userpermission")
@IdClass(UserpermissionId.class)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Userpermission extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;
    
    @Basic
    @Column(name = "revoked", nullable = true)
    private Boolean revoked;
    
    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable=false, updatable=false)
    private Permission permission;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;


}



