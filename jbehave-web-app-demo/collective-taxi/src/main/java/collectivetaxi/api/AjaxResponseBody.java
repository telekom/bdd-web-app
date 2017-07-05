package collectivetaxi.api;

import collectivetaxi.service.User;
import lombok.Data;

import java.util.List;

@Data
public class AjaxResponseBody {

    private String msg;
    private List<User> result;

}