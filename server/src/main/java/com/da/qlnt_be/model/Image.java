package com.da.qlnt_be.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_active = true") // thêm điều kiện vào tất cả query SELECT
@SQLDelete(sql = "UPDATE qlnt.images SET is_active = false WHERE id = ?") // khi gọi delete(), chỉ update flag
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entityType; // 'room', 'user', 'contract', ...

    @Column(name = "entity_id")
    private Long entityId;
    private String url;
    private int imageType; // thumbnail / gallery / avatar
    private String fileName;
    private Long bytes;
    private String contentType;
    private String displayName;
    private String publicId;

}
