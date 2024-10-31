package org.example.task.models;

import jakarta.persistence.*;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "principal_id", nullable = false)
    private Principal principal;

    @Column(name = "is_signed", nullable = false)
    private boolean isSigned;


    public Contract() {}

    public Contract(Admin admin, Principal principal) {
        this.admin = admin;
        this.principal = principal;
        this.isSigned = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean isSigned){
        this.isSigned = isSigned;
    }

    public void sign() {
        this.isSigned = true;
    }
}