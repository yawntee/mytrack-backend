package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Listable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.service.VersionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@AllArgsConstructor
public class VersionController implements Insertable<Version>, Modifiable<Version>, Listable<Version>, Deletable<Version> {

    @Getter
    private final VersionService service;
}
