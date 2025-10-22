package com.da.qlnt_be.model;


import com.da.qlnt_be.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.rooms SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Double area;
    private Double rent;
    private Integer floor; // Ví dụ: 1 = tầng 1, 2 = tầng 2,...
    private Integer maxPeople; // số người tối đa
    @Builder.Default
    private int status = Constants.ROOM_STATUS.EMPTY;
    private String description;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    // Một phòng có nhiều tài sản
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets;

    // Một phòng có thể có nhiều ảnh
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "entity_type = 'rooms'")
    private List<Image> images = new ArrayList<>();
}
