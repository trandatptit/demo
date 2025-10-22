package com.da.qlnt_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "permissions")
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.permissions SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // VD: "ROOM_CREATE", "ROOM_DELETE"
    private String description;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private Set<RolePermission> rolePermissions = new HashSet<>();
}