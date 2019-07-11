package com.perfect.bean.pojo.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PagingPojo {

    private int pageNo;
    private int pageSize;
    private int pages;

}
