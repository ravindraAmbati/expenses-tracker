package learn.myapps.expensestracker.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/model")
@RestController
public class ApiModelController<T> {

    private final ApiModelService<T> apiModelService;

    public ApiModelController(ApiModelService<T> apiModelService) {
        this.apiModelService = apiModelService;
    }

    @GetMapping
    public T getModel(@RequestBody ApiRequestBody apiRequestBody) throws ClassNotFoundException {
        return apiModelService.getModel(apiRequestBody);
    }
}
