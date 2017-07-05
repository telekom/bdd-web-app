package collectivetaxi.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private final String username;
    private final String password;
    private final String email;

}
