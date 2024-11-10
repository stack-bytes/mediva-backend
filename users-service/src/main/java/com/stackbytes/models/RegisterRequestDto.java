package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class RegisterRequestDto {
    private User user;
    private Medic medic;
    private boolean isMedic;
    public boolean isMedic() {
        return isMedic;
    }
}
