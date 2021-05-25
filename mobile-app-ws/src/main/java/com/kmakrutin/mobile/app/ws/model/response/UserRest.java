package com.kmakrutin.mobile.app.ws.model.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
