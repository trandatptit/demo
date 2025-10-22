package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.AssetItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetItemRepository extends JpaRepository<AssetItem, Long> {
    @Query("select ai from AssetItem ai where ai.asset.id = :assetId and ai.isActive = true")
    Page<AssetItem> findByAssetId(@Param("assetId") Long assetId, Pageable pageable);

    @Query("select ai from AssetItem ai where ai.asset.id = :assetId and ai.status = :status and ai.isActive = true")
    Page<AssetItem> getAssetItemsByAssetIdAndStatus(@Param("assetId") Long assetId, @Param("status") int status, Pageable pageable);
}
