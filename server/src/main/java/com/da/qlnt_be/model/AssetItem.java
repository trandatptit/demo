package com.da.qlnt_be.model;

import com.da.qlnt_be.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asset_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE qlnt.asset_items SET is_active = false WHERE id = ?")
public class AssetItem extends BaseEntity { // từng tài sản cụ thể
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String serialNumber;  // số seri
    private LocalDate boughtAt;   // ngày mua
    private Double price;         // giá mua

    @Column(nullable = true)
    private String description;   // mô tả chi tiết

    @Builder.Default
    private int status = Constants.ASSET_ITEM_STATUS.GOOD;   // tình trạng: 0 tốt, 1 hư, 2 đang sửa

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "entity_type = 'asset_items'")
    private List<Image> images = new ArrayList<>();
}
