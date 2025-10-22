package com.da.qlnt_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role_permissions")
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.role_permissions SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class RolePermission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    @JsonIgnore
    private Permission permission;
}
