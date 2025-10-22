package com.da.qlnt_be.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE qlnt.rules SET is_active = false WHERE id = ?")
public class Rule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // VD: "Không hút thuốc trong phòng"

    private String description; // Mô tả chi tiết (nếu có)

    @ManyToMany(mappedBy = "rules")
    private List<House> houses;
}
