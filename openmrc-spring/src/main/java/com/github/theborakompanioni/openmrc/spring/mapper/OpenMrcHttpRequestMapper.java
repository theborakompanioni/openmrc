package com.github.theborakompanioni.openmrc.spring.mapper;


import com.github.theborakompanioni.openmrc.mapper.OpenMrcMapper;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface OpenMrcHttpRequestMapper extends OpenMrcMapper<HttpServletRequest, ResponseEntity<String>, HttpServletRequest, ResponseEntity<String>> {
}
