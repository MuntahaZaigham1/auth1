package com.fastcode.example.domain.core.authorization.user;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.fastcode.example.domain.core.authorization.tokenverification.Tokenverification;
import com.fastcode.example.domain.core.authorization.userpermission.Userpermission;
import com.fastcode.example.domain.core.authorization.userpreference.Userpreference;
import com.fastcode.example.domain.core.authorization.userrole.Userrole;
import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "userEntity")
@Table(name = "f_user")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class User extends AbstractEntity {

    @Basic
    @Column(name = "email_address", nullable = false,length =256)
    private String emailAddress;

    @Basic
    @Column(name = "first_name", nullable = false,length =32)
    private String firstName;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Basic
    @Column(name = "is_email_confirmed", nullable = true)
    private Boolean isEmailConfirmed;
    
    @Basic
    @Column(name = "last_name", nullable = false,length =32)
    private String lastName;

    @Basic
    @Column(name = "password", nullable = false,length =128)
    private String password;

    @Basic
    @Column(name = "phone_number", nullable = true,length =32)
    private String phoneNumber;

    @Basic
    @Column(name = "user_name", nullable = false,length =32)
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tokenverification> tokenverificationsSet = new HashSet<Tokenverification>();
    
    public void addTokenverifications(Tokenverification tokenverifications) {        
    	tokenverificationsSet.add(tokenverifications);

        tokenverifications.setUser(this);
    }
    public void removeTokenverifications(Tokenverification tokenverifications) {
        tokenverificationsSet.remove(tokenverifications);
        
        tokenverifications.setUser(null);

    }
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Userpermission> userpermissionsSet = new HashSet<Userpermission>();
    
    public void addUserpermissions(Userpermission userpermissions) {        
    	userpermissionsSet.add(userpermissions);

        userpermissions.setUser(this);
    }
    public void removeUserpermissions(Userpermission userpermissions) {
        userpermissionsSet.remove(userpermissions);
        
        userpermissions.setUser(null);

    }
    

    @OneToOne(mappedBy = "user", cascade=CascadeType.MERGE)
    private Userpreference userpreference;

    public void setUserpreference(Userpreference userpreference) {
    	if (userpreference == null) {
            if (this.userpreference != null) {
                this.userpreference.setUser(null);
            }
        }
        else {
            userpreference.setUser(this);
        }
        this.userpreference = userpreference;
    }
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Userrole> userrolesSet = new HashSet<Userrole>();
    
    public void addUserroles(Userrole userroles) {        
    	userrolesSet.add(userroles);

        userroles.setUser(this);
    }
    public void removeUserroles(Userrole userroles) {
        userrolesSet.remove(userroles);
        
        userroles.setUser(null);

    }
    

}



