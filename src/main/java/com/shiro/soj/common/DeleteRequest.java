package com.shiro.soj.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author Shiro
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}