package com.hujiao.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductIdsParam {
    @NotNull
    private List<Integer> categoryID;
    private int currentPage = 1;
    private int pageSize = 15;
}
