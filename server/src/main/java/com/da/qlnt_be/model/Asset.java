package com.da.qlnt_be.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.assets SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class Asset extends BaseEntity{  //loại tài sản
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // tên của loại tài sản

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}