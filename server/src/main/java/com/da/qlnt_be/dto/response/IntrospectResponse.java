package com.da.qlnt_be.dto.response;//package com.example.btl_kttk.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectResponse {
    boolean val;
}
