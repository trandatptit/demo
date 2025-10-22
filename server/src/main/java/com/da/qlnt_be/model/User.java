package com.da.qlnt_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.users SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
    private LocalDate dob;

    // Một user có thể có nhiều userRole (nhiều quyền)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "entity_type = 'users'") // chỉ lấy ảnh liên quan User
    private List<Image> images =  new ArrayList<>();


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .flatMap(ur -> ur.getRole().getRolePermissions().stream())
                .map(rp -> new SimpleGrantedAuthority(rp.getPermission().getCode()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return username;
    }
}
