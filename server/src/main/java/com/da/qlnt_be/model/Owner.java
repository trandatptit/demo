package com.da.qlnt_be.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.owner SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class Owner extends User {
    private String bankAcc;
    private String businessNo;
    private String taxCode;
    private Boolean verified;

    // Một chủ có nhiều nhà cho thuê
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<House> houses;
}
