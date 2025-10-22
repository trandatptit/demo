package com.da.qlnt_be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.tenant SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class Tenant extends User {
    private Boolean representative;
}
