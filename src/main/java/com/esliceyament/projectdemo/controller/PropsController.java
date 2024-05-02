package com.esliceyament.projectdemo.controller;

import com.esliceyament.projectdemo.dto.PropsRequest;
import com.esliceyament.projectdemo.dto.PropsResponse;
import com.esliceyament.projectdemo.service.PropsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropsController {

    private final PropsService propsService;

    @PostMapping("/addProperty")
    public void addProp(PropsRequest propsRequest) {
        propsService.addProp(propsRequest);
    }

    @GetMapping("/getAllProperties")
    public List<PropsResponse> getAllProps() {
        return propsService.getAllProps();
    }


}
