package com.github.theborakompanioni.openmrc.spring.web;

import com.github.theborakompanioni.openmrc.OpenMrcConfiguration;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface OpenMrcWebConfiguration extends OpenMrcConfiguration<HttpServletRequest, ResponseEntity<String>> {

}
