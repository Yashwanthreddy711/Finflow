    package com.finflow.account.entity;

    import com.finflow.customer.entity.Customer;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import org.hibernate.annotations.Generated;
    import org.hibernate.generator.EventType;

    import java.math.BigDecimal;

    @Entity
    @Table(name = "ff_account")
    @Getter
    @Setter
    public class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Generated(event= EventType.INSERT)
        @Column(nullable = false,unique = true,insertable = false,updatable = false)
        private String accountNumber;
        @Column(nullable = false)
        private BigDecimal balance;
        @Version
        private Long version;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AccountType type;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AccountStatus status;
            @ManyToOne(fetch = FetchType.LAZY,optional = false)
            @JoinColumn(name="customer_id",referencedColumnName = "customerNumber", nullable = false)
            private Customer customer;
    }
