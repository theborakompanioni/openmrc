package org.tbk.openmrc.web;

import org.tbk.openmrc.mapper.OpenMrcMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by void on 20.06.15.
 */
public interface OpenMrcHttpRequestMapper extends OpenMrcMapper<HttpServletRequest, HttpServletResponse, HttpServletRequest, HttpServletResponse> {
}
