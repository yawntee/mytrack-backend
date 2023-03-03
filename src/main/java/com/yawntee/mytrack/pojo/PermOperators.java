package com.yawntee.mytrack.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PermOperators {

    private List<String> grant;

    private List<String> revoke;

}
