package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.HouseRequest;
import com.da.qlnt_be.dto.response.HouseResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.HouseMapper;
import com.da.qlnt_be.model.House;
import com.da.qlnt_be.model.Owner;
import com.da.qlnt_be.model.Rule;
import com.da.qlnt_be.repository.HouseRepository;
import com.da.qlnt_be.repository.OwnerRepository;
import com.da.qlnt_be.repository.RuleRepository;
import com.da.qlnt_be.services.HouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    HouseRepository houseRepository;
    OwnerRepository ownerRepository;
    RuleRepository ruleRepository;

    /**
     * Tạo mới một nhà trọ và lưu vào cơ sở dữ liệu.
     * @param request đối tượng HouseRequest chứa thông tin để tạo nhà trọ mới.
     * @return HouseResponse  thông tin nhà trọ đã được lưu.
     * @throws AppException nếu không tìm thấy chủ trọ tương ứng với ownerId.
     */
    @Override
    public HouseResponse saveHouse(HouseRequest request) {
        House house = new House();
        house.setName(request.getName());
        house.setProvince(request.getProvince());
        house.setDistrict(request.getDistrict());
        house.setCode(generateHouseCode(request.getProvince()));
        house.setAddress(request.getAddress());
        house.setArea(request.getArea());

        // Tìm owner tương ứng
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new AppException(ErrorCode.OWNER_NOT_EXISTED));
        house.setOwner(owner);

        // Gán nội quy
        if (request.getRuleIds() != null && !request.getRuleIds().isEmpty()) {
            List<Rule> rules = ruleRepository.findAllById(request.getRuleIds());
            house.setRules(rules);
        }

        // lưu house vào db
        houseRepository.save(house);

        log.info("Created new house successfully: code={}, ownerId={}", house.getCode(), owner.getId());
        return HouseMapper.toReponse(house);
    }

    /**
     * Lấy danh sách tất cả các căn nhà thuộc về một chủ sở hữu cụ thể.
     * @param ownerId ID của chủ sở hữu (Owner) cần lấy danh sách nhà.
     * @param page Số trang hiện tại (bắt đầu từ 0)
     * @param size Số lượng phần tử trong mỗi trang
     * @return trang dữ liệu Page chứa danh sách HouseResponse của các căn nhà thuộc chủ sở hữu.
     * @throws AppException nếu không tìm thấy chủ sở hữu tương ứng với ownerId.
     */
    @Override
    public Page<HouseResponse> getHousesByOwner(Long ownerId, int page, int size) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new AppException(ErrorCode.OWNER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<House> responses = houseRepository.findByOwner(owner, pageable);


        log.info("Retrieved {} houses for ownerId={}", responses.getContent().size(), ownerId);
        return responses.map(HouseMapper::toReponse);
    }

    /**
     * Lấy thông tin chi tiết của một căn nhà dựa trên ID.
     * @param id id ID của căn nhà cần truy vấn.
     * @return HouseResponse chứa thông tin chi tiết của căn nhà tương ứng.
     * @throws AppException nếu không tìm thấy căn nhà với ID đã cho (mã lỗi ErrorCode#HOUSE_NOT_EXISTED).
     */
    @Override
    public HouseResponse getHouseById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HOUSE_NOT_EXISTED));

        log.info("Retrieved house successfully: id={}, code={}", house.getId(), house.getCode());
        return HouseMapper.toReponse(house);
    }

    /**
     * Cập nhật thông tin của một căn nhà dựa trên ID.
     * @param id ID của căn nhà cần cập nhật.
     * @param request đối tượng HouseRequest chứa thông tin cập nhật
     * @return HouseResponse chứa thông tin chi tiết của căn nhà sau khi cập nhật.
     * @throws AppException nếu căn nhà không tồn tại trong hệ thống.
     */
    @Override
    public HouseResponse updateHouse(Long id, HouseRequest request) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HOUSE_NOT_EXISTED));

        // Tên nhà trọ
        String newName = request.getName();
        if (newName != null && !newName.equals(house.getName())) {
            house.setName(newName);
        }

        // Tỉnh/Thành phố và Mã Code (House Code)
        String newProvince = request.getProvince();
        if (newProvince != null && !newProvince.equals(house.getProvince())) {
            house.setProvince(newProvince);

            house.setCode(generateHouseCode(newProvince));
        }

        // Quận/Huyện
        String newDistrict = request.getDistrict();
        if (newDistrict != null && !newDistrict.equals(house.getDistrict())) {
            house.setDistrict(newDistrict);
        }

        // Địa chỉ chi tiết
        String newAddress = request.getAddress();
        if (newAddress != null && !newAddress.equals(house.getAddress())) {
            house.setAddress(newAddress);
        }

        if (!Objects.equals(request.getArea(), house.getArea())) {
            house.setArea(request.getArea());
        }

        // Gán nội quy
        if (request.getRuleIds() != null) {
            if (request.getRuleIds().isEmpty()) {
                house.setRules(Collections.emptyList());
            } else {
                List<Rule> rules = ruleRepository.findAllById(request.getRuleIds());
                house.setRules(rules);
            }
        }

        houseRepository.save(house);

        log.info("Updated house successfully: id={}, newCode={}, address={}",
                house.getId(), house.getCode(), house.getAddress());
        return HouseMapper.toReponse(house);
    }

    /**
     * Đánh dấu ngôi nhà là không còn hoạt động (xóa mềm).
     * @param id ID của ngôi nhà cần xóa mềm
     * @throws AppException nếu không tìm thấy ngôi nhà với ID tương ứng
     */
    @Override
    public void softDeleteHouse(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HOUSE_NOT_EXISTED));

        houseRepository.delete(house);
        log.info("Soft deleted house successfully: id={}, code={}", house.getId(), house.getCode());
    }

    /**
     * Tạo mã định danh cho nhà trọ (House Code) dựa trên tỉnh
     * @param province tỉnh nhà trọ.
     * @return Mã nhà trọ có dạng PREFIX-XXX, ví dụ: "DN-001" hoặc "HS-001" nếu không có địa chỉ hợp lệ.
     */
    private String generateHouseCode(String province) {
        // Sinh tiền tố (prefix) dựa trên địa chỉ
        String prefix = normalizeProvincePrefix(province);

        // Lấy số thứ tự tăng dần theo tổng số bản ghi hiện có
        long count = houseRepository.count() + 1;

        return String.format("%s-%03d", prefix, count);
    }

    /**
     * Chuẩn hóa địa chỉ để sinh tiền tố mã nhà trọ (prefix).
     * @param province tỉnh của nhà trọ.
     * @return Chuỗi prefix viết hoa, ví dụ: "DN", "TPHCM", hoặc "HS" nếu không hợp lệ.
     */
    private String normalizeProvincePrefix(String province) {
        // Nếu tỉnh rỗng hoặc null -> trả về mặc định "HS"
        if(province == null || province.isBlank()) return "HS";

        // Bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(province, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", ""); // Loại bỏ dấu thanh, mũ...

        String part = normalized.trim();

        // Tách các từ và ghép chữ cái đầu tiên của từng từ
        String[] words = part.split(" ");
        StringBuilder prefix = new StringBuilder();
        for (String word : words) {
            if (!word.isBlank()) {
                prefix.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        // Nếu sau khi xử lý mà không có ký tự nào hợp lệ → dùng prefix mặc định "HS"
        if (prefix.isEmpty()) prefix.append("HS");

        return prefix.toString();
    }
}
