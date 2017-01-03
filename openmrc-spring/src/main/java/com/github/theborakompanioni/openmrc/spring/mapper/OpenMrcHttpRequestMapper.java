package com.github.theborakompanioni.openmrc.spring.mapper;


import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface OpenMrcHttpRequestMapper extends OpenMrcMapper<HttpServletRequest, ResponseEntity<String>, HttpServletRequest, ResponseEntity<String>> {
}
