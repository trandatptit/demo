package com.da.qlnt_be.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "houses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.houses SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class House extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // tên nhà trọ
    private String code;
    private String province;    // Tỉnh/Thành phố
    private String district;    // Quận/Huyện
    private String address;     // Địa chỉ chi tiết
    private Double area;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Nhà trọ có thể có nhiều nội quy
    @ManyToMany
    @JoinTable(
            name = "house_rules",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private List<Rule> rules;

    // một nhà trọ có nhiều phòng
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;
}
