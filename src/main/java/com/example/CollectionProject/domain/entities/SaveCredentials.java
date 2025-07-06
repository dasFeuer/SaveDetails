package com.example.CollectionProject.domain.entities;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "userCredentials")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User ownerOfCredentials;

    @Column(name = "header", nullable = false)
    @Description("Name the app before saving it's credentials")
    private String header;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "remarks")
    private String remarks;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SaveCredentials that = (SaveCredentials) o;
        return Objects.equals(id, that.id)
                && Objects.equals(ownerOfCredentials, that.ownerOfCredentials)
                && Objects.equals(header, that.header)
                && Objects.equals(email, that.email)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(remarks, that.remarks)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerOfCredentials, header, email, username, password, remarks, createdAt, updatedAt);
    }
}
