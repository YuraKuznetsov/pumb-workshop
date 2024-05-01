package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParams {

    String type;
    Integer category;
    String sex;
    String orderBy;
}
